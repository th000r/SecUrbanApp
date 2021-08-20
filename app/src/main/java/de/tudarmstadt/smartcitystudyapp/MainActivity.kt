package de.tudarmstadt.smartcitystudyapp

import android.content.*
import android.content.SharedPreferences.OnSharedPreferenceChangeListener
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.ContextThemeWrapper
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.lifecycleScope
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.navigation.NavigationView
import com.jakewharton.threetenabp.AndroidThreeTen
import dagger.hilt.android.AndroidEntryPoint
import de.tudarmstadt.smartcitystudyapp.helper.ConnectionType
import de.tudarmstadt.smartcitystudyapp.helper.NetworkMonitor
import de.tudarmstadt.smartcitystudyapp.helper.SharedPref
import de.tudarmstadt.smartcitystudyapp.services.PushNotificationService
import de.tudarmstadt.smartcitystudyapp.services.UserService
import kotlinx.coroutines.launch
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity() : AppCompatActivity(), SharedPreferences.OnSharedPreferenceChangeListener {
    @Inject
    lateinit var userService: UserService
    private lateinit var appBarConfiguration: AppBarConfiguration
    private val networkMonitor = NetworkMonitor(this)
    companion object {
        var networkAvailable = true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        AndroidThreeTen.init(this)

        val intent = Intent(this, WelcomeActivity::class.java)
        this.lifecycleScope.launch {
            userService.getUserId() ?: run {
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                startActivity(intent)
            }
        }

        SharedPref.registerSharedPrefChangeListener(applicationContext, this)

        setContentView(R.layout.activity_main)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_main,
                R.id.nav_profile,
                R.id.nav_incidents,
                R.id.nav_activities,
                R.id.nav_reports,
                R.id.nav_help,
                R.id.nav_site_notice
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
        drawerLayout.openDrawer(GravityCompat.START)

        // listen for wifi/mobile connectivity changes
        networkMonitor.result = { isAvailable, type ->
            runOnUiThread {
                when (isAvailable) {
                    true -> {
                        when (type) {
                            ConnectionType.Wifi, ConnectionType.Cellular -> {
                                networkAvailable = true
                                Intent().also { intent ->
                                    intent.setAction(getString(R.string.broadcast_network_status))
                                    intent.putExtra("status", true)
                                    LocalBroadcastManager.getInstance(this).sendBroadcast(intent)
                                }
                            }
                            else -> { }
                        }
                    }
                    false -> {
                        networkAvailable = false
                        Intent().also { intent ->
                            intent.setAction(getString(R.string.broadcast_network_status))
                            intent.putExtra("status", false)
                            LocalBroadcastManager.getInstance(this).sendBroadcast(intent)
                        }
                    }
                }
            }
        }

        // workaround: start service to keep push notifications alive, because android os will
        // shut down alarm manager to reduce energy consumption
        val fgsIntent = Intent(this, PushNotificationService::class.java)
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForegroundService(fgsIntent)
        } else {
            startService(fgsIntent)
        }

//        val handler = Handler(Looper.getMainLooper())
//        handler.postDelayed(object : Runnable {
//            override fun run() {
//                invalidateOptionsMenu()
//                Log.d("invalidate shared pref", SharedPref.getNotificationStatus(this@MainActivity.applicationContext).toString())
//                handler.postDelayed(this, 10000)
//            }
//        }, 10000)
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        setIntent(intent)
    }

    override fun onResume() {
        super.onResume()
        // register network monitor for broadcasts
        networkMonitor.register()
        // navigate to report fragment if notification was tapped
        val fromNotification = getIntent().getStringExtra("fragment")
        if (fromNotification != null) {
            // ToDo: shared pref value does not change after edit (it's only updated after app restart)
            val notificationStatus = SharedPref.getNotificationStatus(this)
            if (notificationStatus > 0) {
                this.findNavController(R.id.nav_host_fragment).navigate(R.id.nav_incident_submit_notification)
            } else {
                this.findNavController(R.id.nav_host_fragment).navigate(R.id.nav_reports)
            }
        }
    }

    override fun onStop() {
        super.onStop()
        networkMonitor.unregister()
        SharedPref.unregisterSharedPrefChangeListener(applicationContext, this)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)

        setNotificationIconTheme(menu)

        menu.getItem(0)?.setOnMenuItemClickListener {
            //get shared preferences to remember if a notification was already scheduled and executed
            val notificationStatus = SharedPref.getNotificationStatus(this)
            Log.d("Shared Pref", notificationStatus.toString())
            if (notificationStatus > 0) {
                this.findNavController(R.id.nav_host_fragment).navigate(R.id.nav_incident_submit_notification)
            } else {
                this.findNavController(R.id.nav_host_fragment).navigate(R.id.nav_reports)
            }
            return@setOnMenuItemClickListener true
        }
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        return findNavController(R.id.nav_host_fragment).navigateUp(appBarConfiguration) ||
                super.onSupportNavigateUp()
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        setNotificationIconTheme(menu)
        return super.onPrepareOptionsMenu(menu)
    }

    fun setNotificationIconTheme(menu: Menu?) {
        //get shared preferences to remember if a notification was already scheduled and executed
        //val notificationStatus = sharedPref!!.getInt(NOTIFICATION_KEY_STATUS, 0)
        val notificationStatus = SharedPref.getNotificationStatus(this)
        //Log.d("Notification Status", notificationStatus.toString())

        val wrapper: ContextThemeWrapper
        if (notificationStatus > 0) {
            wrapper = ContextThemeWrapper(this, R.style.IconNewNotifications)
        } else{
            wrapper = ContextThemeWrapper(this, R.style.IconNoNotifications)
        }
        menu?.getItem(0)?.icon?.mutate()?.applyTheme(wrapper.theme)
    }

    override fun onSharedPreferenceChanged(p0: SharedPreferences?, p1: String?) {
        Log.d("Shared Preferences", "Change")
    }
}