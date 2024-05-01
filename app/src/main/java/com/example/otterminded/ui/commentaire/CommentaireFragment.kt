package com.example.otterminded.ui.commentaire

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.example.otterminded.MainActivity
import com.example.otterminded.R
import com.example.otterminded.databinding.FragmentCommentaireBinding
import com.example.otterminded.models.DAOCommentaire
import com.example.otterminded.models.DAOQuestion
import com.example.otterminded.notification.NotificationCreate

class CommentaireFragment : Fragment() {

    private lateinit var binding: FragmentCommentaireBinding

    @RequiresApi(Build.VERSION_CODES.O)
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

            // Récupérer l'ID de l'utilisateur depuis les préférences partagées
            val sharedPreferences = requireContext().getSharedPreferences("user_session", Context.MODE_PRIVATE)
            val idUtilisateur =
                sharedPreferences.getString("user_id", null)?.toLong()  // Remplacez ceci par l'ID de l'utilisateur approprié

            // Ajouter le commentaire à la base de données
            val newRowId = idUtilisateur?.let { it1 ->
                daoCommentaire.addCommentaire(questionId,
                    it1, commentaire)
            }

            // Vérifier si l'opération a réussi
            if (newRowId != -1L) {
                // Afficher un message de succès ou effectuer d'autres actions
                Toast.makeText(requireContext(), "Commentaire ajouté avec succès! La loutre te remercie pour cela.", Toast.LENGTH_SHORT).show()
                val intent = Intent(requireContext(), MainActivity::class.java) // Remplacez MainActivity par le nom de votre activité

                // Créer une notification pour prévenir de l'ajout du commentaire
                val notificationCreate = NotificationCreate()

                // Utiliser la méthode createNotification pour afficher une notification
                notificationCreate.createNotification(
                    context = requireContext(),
                    title = "Ajout d'un commentaire !",
                    message = "Un nouveau commentaire a été ajouté sur la question: ${binding.questionComment.text}"
                )

                startActivity(intent)
            } else {
                // Afficher un message d'erreur
                Toast.makeText(requireContext(), "Erreur lors de l'ajout du commentaire.", Toast.LENGTH_SHORT).show()
            }
        }

        return view
    }
}
