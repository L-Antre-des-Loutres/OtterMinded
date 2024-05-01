package com.example.otterminded.notification

import android.app.AlarmManager
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import com.example.otterminded.R
import java.util.*

class NotificationScheduler : BroadcastReceiver() {

    companion object {
        const val CHANNEL_ID = "notification_channel"
        const val NOTIFICATION_ID = 1

        // Méthode pour programmer une notification à une heure spécifique
        fun scheduleNotification(context: Context, hourOfDay: Int, minuteOfDay: Int) {
            val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            val intent = Intent(context, NotificationScheduler::class.java)
            val pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)

            // Créer un calendrier pour l'heure souhaitée
            val calendar = Calendar.getInstance()
            calendar.timeInMillis = System.currentTimeMillis()
            calendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
            calendar.set(Calendar.MINUTE, minuteOfDay)
            calendar.set(Calendar.SECOND, 0)

            // Si l'heure est déjà passée aujourd'hui, ajouter un jour
            if (calendar.timeInMillis <= System.currentTimeMillis()) {
                calendar.add(Calendar.DAY_OF_YEAR, 1)
            }

            // Définir une alarme pour l'heure spécifiée
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pendingIntent)
        }
    }

    override fun onReceive(context: Context, intent: Intent) {
        // Créer et afficher la notification
        createNotification(context)
    }


    private fun createNotification(context: Context) {
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // Créer le canal de notification (pour Android Oreo et versions ultérieures)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(CHANNEL_ID, "Channel Name", NotificationManager.IMPORTANCE_DEFAULT)
            notificationManager.createNotificationChannel(channel)
        }

        // Créer la notification
        val notificationBuilder = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Notification.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.notification_icon)
                .setContentTitle("HoraireMinded")
                .setContentText("17h40 touche ton nez, mais surtout passe un p'tit coup pour venir nous voir >:(")
                .setGroup(context.getString(R.string.groupeNotif))
        } else {
            TODO("VERSION.SDK_INT < O")
        }

        val notification = notificationBuilder.build()

        // Afficher la notification
        notificationManager.notify(NOTIFICATION_ID, notification)
    }
}
