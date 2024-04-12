package com.example.otterminded.notification

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.otterminded.CHANNEL_ID
import com.example.otterminded.R
import com.example.otterminded.RC_NOTIFICATIONS

class NotificationCreate {
    // Méthode pour créer une notification
    @RequiresApi(Build.VERSION_CODES.O)
    fun createNotification(context: Context, title: String, message: String) {
        // Créer le canal de notification si nécessaire
        createNotificationChannel(context)

        // Construire la notification
        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.notification_icon)
            .setContentTitle(title)
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        // Afficher la notification
        with(NotificationManagerCompat.from(context)) {
            if (ActivityCompat.checkSelfPermission(
                    context,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                // TODO: Gérer le cas où les permissions sont manquantes
                return
            }
            notify(RC_NOTIFICATIONS, builder.build())
        }
    }

    // Méthode pour créer un canal de notification
    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel(context: Context) {
        val name = "Nom du canal"
        val descriptionText = "Description du canal"
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
            description = descriptionText
        }
        val notificationManager: NotificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }
}
