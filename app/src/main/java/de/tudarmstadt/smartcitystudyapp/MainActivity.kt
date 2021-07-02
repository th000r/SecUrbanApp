package de.tudarmstadt.smartcitystudyapp

import android.content.Intent
import android.os.Bundle
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
import dagger.hilt.android.AndroidEntryPoint
import de.tudarmstadt.smartcitystudyapp.helper.ConnectionType
import de.tudarmstadt.smartcitystudyapp.helper.NetworkMonitor
import de.tudarmstadt.smartcitystudyapp.services.UserService
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    @Inject
    lateinit var userService: UserService
    private lateinit var appBarConfiguration: AppBarConfiguration
    private val networkMonitor = NetworkMonitor(this)
    companion object {
        var networkAvailable = true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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
//                R.id.nav_profile,
                R.id.nav_incidents,
//                R.id.nav_activities,
//                R.id.nav_reports,
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
    }

    override fun onResume() {
        super.onResume()
        // register network monitor for broadcasts
        networkMonitor.register()
    }

    override fun onStop() {
        super.onStop()
        networkMonitor.unregister()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        return findNavController(R.id.nav_host_fragment).navigateUp(appBarConfiguration) ||
                super.onSupportNavigateUp()
    }
}