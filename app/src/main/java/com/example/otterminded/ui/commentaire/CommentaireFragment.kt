package com.example.otterminded.ui.commentaire

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.otterminded.MainActivity
import com.example.otterminded.R
import com.example.otterminded.databinding.FragmentCommentaireBinding
import com.example.otterminded.models.DAOCommentaire
import com.example.otterminded.models.DAOQuestion
import com.example.otterminded.notification.NotificationScheduler

class CommentaireFragment : Fragment() {

    private lateinit var binding: FragmentCommentaireBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCommentaireBinding.inflate(inflater, container, false)
        val view = binding.root

        val daoCommentaire = DAOCommentaire(requireContext())

        // Récupérer l'ID de la question depuis les arguments du fragment
        val questionId = arguments?.getLong("question_id", -1) ?: -1

        // Vérifier si l'ID de la question est valide
        if (questionId != (-1).toLong()) {
            // Créer une instance de DAOQuestion
            val daoQuestion = DAOQuestion(requireContext())

            // Appeler la fonction getQuestionById sur cette instance
            val question = daoQuestion.getQuestionById(questionId)

            // Afficher la question dans TextView
            binding.questionComment.text = question?.question ?: "Question non trouvée"
        } else {
            // Gérer le cas où l'ID n'a pas été passé au fragment
            binding.questionComment.text = "Question non trouvée"
        }

        val editTextCommentaire = view.findViewById<EditText>(R.id.editTextCommentaire)
        val buttonEnvoyerCommentaire = view.findViewById<Button>(R.id.buttonEnvoyerCommentaire)

        buttonEnvoyerCommentaire.setOnClickListener {
            val commentaire = editTextCommentaire.text.toString()

            // Récupérer l'ID de la question et de l'utilisateur depuis votre source de données

            val idUtilisateur = 1L // Remplacez ceci par l'ID de l'utilisateur approprié

            // Ajouter le commentaire à la base de données
            val newRowId = daoCommentaire.addCommentaire(questionId, idUtilisateur, commentaire)

            // Vérifier si l'opération a réussi
            if (newRowId != -1L) {
                // Afficher un message de succès ou effectuer d'autres actions
                Toast.makeText(requireContext(), "Commentaire ajouté avec succès! La loutre te remercie pour cela.", Toast.LENGTH_SHORT).show()
                val intent = Intent(requireContext(), MainActivity::class.java) // Remplacez MainActivity par le nom de votre activité

                fun createNotification(context: Context) {
                    val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

                    // Créer le canal de notification (pour Android Oreo et versions ultérieures)
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        val channel = NotificationChannel(NotificationScheduler.CHANNEL_ID, "Channel Name", NotificationManager.IMPORTANCE_DEFAULT)
                        notificationManager.createNotificationChannel(channel)
                    }

                    // Créer la notification
                    val notificationBuilder = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        Notification.Builder(context, NotificationScheduler.CHANNEL_ID)
                            .setSmallIcon(R.drawable.notification_icon)
                            .setContentTitle("Commentaire")
                            .setContentText("Commentaire ajouté !")
                            .setGroup(context.getString(R.string.groupeNotif))
                    } else {
                        TODO("VERSION.SDK_INT < O")
                    }

                    val notification = notificationBuilder.build()

                    // Afficher la notification
                    notificationManager.notify(NotificationScheduler.NOTIFICATION_ID, notification)
                }

                startActivity(intent)
            } else {
                // Afficher un message d'erreur ou effectuer d'autres actions si nécessaire
                Toast.makeText(requireContext(), "Erreur lors de l'ajout du commentaire.", Toast.LENGTH_SHORT).show()
            }

        }

        return view
    }
}
