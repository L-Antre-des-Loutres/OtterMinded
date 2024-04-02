package com.example.otterminded.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.otterminded.models.DAOQuestion
import com.example.otterminded.databinding.FragmentCommentaireBinding

class CommentaireFragment : Fragment() {

    private lateinit var binding: FragmentCommentaireBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCommentaireBinding.inflate(inflater, container, false)
        val view = binding.root

        // Récupérer l'intent qui a démarré l'activité associée au fragment
        val intent = requireActivity().intent

        // Vérifier si l'intent est non nul et si l'intent contient l'extra "question_id"
        if (intent != null && intent.hasExtra("question_id")) {
            // Récupérer la valeur de l'ID à partir de l'intent
            val questionId = intent.getLongExtra("question_id", -1)

            // Créer une instance de DAOQuestion
            val daoQuestion = DAOQuestion(requireContext())

            // Appeler la fonction getQuestionById sur cette instance
            val question = daoQuestion.getQuestionById(questionId)

            // Afficher la question dans TextView
            binding.questionComment.text = question?.question ?: "Question non trouvée"
        } else {
            // Gérer le cas où l'ID n'a pas été passé à l'activité
            // Vous pouvez afficher un message d'erreur, retourner en arrière ou effectuer une autre action appropriée
            binding.questionComment.text = "Question non trouvée"
        }

        return view
    }
}