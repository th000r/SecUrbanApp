package de.tudarmstadt.smartcitystudyapp

import android.app.*
import android.content.*
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.Menu
import androidx.activity.viewModels
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
import de.tudarmstadt.smartcitystudyapp.database.AppDatabase
import de.tudarmstadt.smartcitystudyapp.helper.ConnectionType
import de.tudarmstadt.smartcitystudyapp.helper.NetworkMonitor
import de.tudarmstadt.smartcitystudyapp.interfaces.services.UserService
import de.tudarmstadt.smartcitystudyapp.notification.PushNotificationService
import de.tudarmstadt.smartcitystudyapp.services.*
import de.tudarmstadt.smartcitystudyapp.ui.welcome.WelcomeActivity
import kotlinx.coroutines.*
import java.util.*
import javax.inject.Inject
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import de.tudarmstadt.smartcitystudyapp.featuremanager.FeatureManager
import de.tudarmstadt.smartcitystudyapp.featuremanager.Features
import de.tudarmstadt.smartcitystudyapp.utils.getJsonDataFromAsset


@AndroidEntryPoint
class MainActivity() : AppCompatActivity() {
    private val mainViewModel by viewModels<MainActivityViewModel>()

    @Inject
    lateinit var userService: UserService
    private var count = 0
    private lateinit var appBarConfiguration: AppBarConfiguration
    private val networkMonitor = NetworkMonitor(this)

    companion object {
        var networkAvailable = true
    }

    private var wasStarted = false

    // private lateinit var binding : ActivityMainBinding
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
                R.id.nav_team_activities,
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
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        setIntent(intent)
    }

    override fun onResume() {
        super.onResume()
        Log.d("MainActivity", "onResume")
        // register network monitor for broadcasts
        networkMonitor.register()
        // navigate to report fragment if notification was tapped
        CoroutineScope(Dispatchers.IO).launch{ // launches coroutine in main thread
            getUnreadNotificationCount()
        }

        val action = intent.getStringExtra("pushNotification")

        if (wasStarted && action == "display") {
            Log.d("MainActivity", "Unread Count: " + count.toString())
            if (count > 0) {
                this.findNavController(R.id.nav_host_fragment)
                    .navigate(R.id.nav_incident_submit_notification)
                CoroutineScope(Dispatchers.IO).launch {
                    deleteAllNotifications()
                }
            } else {
                this.findNavController(R.id.nav_host_fragment).navigate(R.id.nav_incidents)
            }
        }

        wasStarted = true
    }

    override fun onStop() {
        super.onStop()
        networkMonitor.unregister()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        Log.d("MainActivity", "onCreateOptionsMenu")

        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)

        CoroutineScope(Dispatchers.IO).launch{ // launches coroutine in main thread
            getUnreadNotificationCount()
        }
        Log.d("MainActivity", "Unread Count: " + count.toString())
        if (count > 0) {
            menu?.findItem(R.id.incidents_toolbar)?.isVisible = false
            menu?.findItem(R.id.incidents_new_toolbar)?.isVisible = true
        } else {
            menu?.findItem(R.id.incidents_toolbar)?.isVisible = true
            menu?.findItem(R.id.incidents_new_toolbar)?.isVisible = false
        }



        // setNotificationIconTheme(menu)

        menu.findItem(R.id.incidents_toolbar).setOnMenuItemClickListener {
            this.findNavController(R.id.nav_host_fragment)
                .navigate(R.id.nav_incidents)
            return@setOnMenuItemClickListener true
        }

        menu.findItem(R.id.incidents_new_toolbar).setOnMenuItemClickListener {
            this.findNavController(R.id.nav_host_fragment)
                .navigate(R.id.nav_incident_submit_notification)

            val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.cancelAll()

            CoroutineScope(Dispatchers.IO).launch{ // launches coroutine in main thread
               deleteAllNotifications()
            }

            return@setOnMenuItemClickListener true
        }

        super.onCreateOptionsMenu(menu)

        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        return findNavController(R.id.nav_host_fragment).navigateUp(appBarConfiguration) ||
                super.onSupportNavigateUp()
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        Log.d("MainActivity", "onPrepareOptionsMenu")

        CoroutineScope(Dispatchers.IO).launch{ // launches coroutine in main thread
            getUnreadNotificationCount()
        }
        Log.d("MainActivity", "Unread Count: " + count.toString())
        if (count > 0) {
            menu?.findItem(R.id.incidents_toolbar)?.isVisible = false
            menu?.findItem(R.id.incidents_new_toolbar)?.isVisible = true
        } else {
            menu?.findItem(R.id.incidents_toolbar)?.isVisible = true
            menu?.findItem(R.id.incidents_new_toolbar)?.isVisible = false
        }

        super.onPrepareOptionsMenu(menu)

        return true
    }

    private suspend fun getUnreadNotificationCount() {
        val value = CoroutineScope(Dispatchers.IO).async {
            withContext(Dispatchers.IO) {
                count = AppDatabase.getDatabase(applicationContext).notificationDao()
                    .loadUnreadNotificationCount()
            }
        }
        value.await()
    }

    private suspend fun deleteAllNotifications() {
        val value = CoroutineScope(Dispatchers.IO).async {
            withContext(Dispatchers.IO) {
                AppDatabase.getDatabase(applicationContext).notificationDao()
                    .deleteAll()
            }
        }
        value.await()
    }
}