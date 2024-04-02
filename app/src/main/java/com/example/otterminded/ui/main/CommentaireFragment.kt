package com.example.otterminded.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.otterminded.databinding.FragmentCommentaireBinding
import com.example.otterminded.models.DAOQuestion

class CommentaireFragment : Fragment() {

    private lateinit var binding: FragmentCommentaireBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCommentaireBinding.inflate(inflater, container, false)
        val view = binding.root

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

        return view
    }
}
