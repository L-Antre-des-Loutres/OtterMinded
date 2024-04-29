package com.example.otterminded

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.otterminded.databinding.ActivityMainBinding
import com.example.otterminded.notification.NotificationScheduler
import com.example.otterminded.ui.slideshow.SlideshowFragment
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar

val CHANNEL_ID = "1"
val RC_NOTIFICATIONS = 99

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarMain.toolbar)

        binding.appBarMain.fab.setOnClickListener { view ->
            Snackbar.make(
                view,
                "Messagerie pour l'instant non disponible en local.",
                Snackbar.LENGTH_LONG
            )
                .setAction("Action", null)
                .setAnchorView(R.id.fab).show()
        }
        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_main)

        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_login, R.id.nav_slideshow
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        // Gestion des permissions de notifications
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            requestPermissions(arrayOf(Manifest.permission.POST_NOTIFICATIONS), RC_NOTIFICATIONS)
        }

        // Notification à 17h via NotificationScheduler
        val hourOfDay = 17 // Heure souhaitée en heures du jour
        val minuteOfDay = 40
        NotificationScheduler.scheduleNotification(this, hourOfDay, minuteOfDay)

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Vérifiez quel élément de menu a été cliqué et affichez un message approprié
        return when (item.itemId) {
            R.id.action_settings -> {
                showToast("Action Settings cliquée")
                true
            }
            R.id.action_profil -> {
                showToast("Action Profil cliquée")
                true
            }
            R.id.action_questions -> {
                showToast("Action Questions cliquée")
                true
            }
            R.id.action_admin -> {
                val sharedPreferences = getSharedPreferences("user_session", Context.MODE_PRIVATE)
                val admin = sharedPreferences.getInt("admin", -1)

                if (admin == 1) {
                    val intent = Intent(this, AdminActivity::class.java)
                    startActivity(intent)
                    true
                } else {
                    // Afficher un message indiquant que l'accès est réservé aux administrateurs
                    Toast.makeText(this, "Accès réservé aux administrateurs", Toast.LENGTH_SHORT).show()
                    true
                }
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun showToast(message: String) {
        // Afficher un toast avec le message spécifié
        Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()
    }

}
