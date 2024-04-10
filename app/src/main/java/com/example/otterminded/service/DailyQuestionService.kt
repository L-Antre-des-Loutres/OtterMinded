package com.example.otterminded.service
import android.app.*
import android.content.Context
import android.content.Intent
import android.os.*
import androidx.core.app.NotificationCompat
import com.example.otterminded.R
import com.example.otterminded.models.DAOQuestion

class DailyQuestionService : Service() {

    private lateinit var notificationManager: NotificationManager
    private var questionListener: QuestionListener? = null

    private var serviceLooper: Looper? = null
    private var serviceHandler: ServiceHandler? = null

    // Handler that receives messages from the thread
    private inner class ServiceHandler(looper: Looper) : Handler(looper) {
        override fun handleMessage(msg: Message) {
            // Choose a random question
            val randomQuestion = chooseRandomQuestion()
            // Send the question to the listener
            questionListener?.onQuestionReceived(randomQuestion)
            // Show a notification
            showNotification(randomQuestion)
            // Stop the service once the task is done
            stopSelf(msg.arg1)
        }
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        createNotificationChannel()

        // Start up the thread running the service
        HandlerThread("ServiceStartArguments", Process.THREAD_PRIORITY_BACKGROUND).apply {
            start()

            // Get the HandlerThread's Looper and use it for our Handler
            serviceLooper = looper
            serviceHandler = ServiceHandler(looper)
        }
    }

    private fun createNotificationChannel() {
        val channelId = "DailyQuestion"
        val channelName = "La question du jour"
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(channelId, channelName, importance)
        notificationManager.createNotificationChannel(channel)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        // For each start request, send a message to start a job and deliver the
        // start ID so we know which request we're stopping when we finish the job
        serviceHandler?.obtainMessage()?.also { msg ->
            msg.arg1 = startId
            serviceHandler?.sendMessage(msg)
        }

        // If we get killed, after returning from here, restart
        return START_STICKY
    }

    private fun chooseRandomQuestion(): String {
        val daoQuestion = DAOQuestion(applicationContext)
        val nbQuestion: Int = daoQuestion.getNbQuestion()

        // Calcul de l'ID de la question
        val currentTimeMillis = System.currentTimeMillis()
        val millisecondsIn24Hours = 24 * 60 * 60 * 1000
        val daysSinceEpoch = currentTimeMillis / millisecondsIn24Hours
        val questionId: Long = daysSinceEpoch % nbQuestion

        // Si aucune question n'est définie, renvoie l'ID 1 par défaut
        if (nbQuestion == 0) {
            return daoQuestion.getQuestionById(1)?.question ?: "Question par défaut"
        }

        // Sinon, récupère la question aléatoire
        val question = daoQuestion.getQuestionById(questionId)

        return question?.question ?: "Question non trouvée"
    }

    private fun showNotification(question: String) {
        val channelId = "DailyQuestion"
        val notification = NotificationCompat.Builder(this, channelId)
            .setContentTitle("Question du Jour")
            .setContentText(question)
            .setSmallIcon(R.drawable.notification_icon)
            .build()

        notificationManager.notify(1, notification)
    }

    // Envoie de la question aléatoire directement sur le fragment Home
    interface QuestionListener {
        fun onQuestionReceived(question: String)
    }

    fun setQuestionListener(listener: QuestionListener) {
        questionListener = listener
    }

    override fun onDestroy() {
        super.onDestroy()
        // Cleanup code, if any
    }
}
