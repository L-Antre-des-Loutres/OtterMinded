package com.example.otterminded.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.otterminded.CommentaireActivity
import com.example.otterminded.QuestionManager
import com.example.otterminded.databinding.FragmentHomeBinding
import com.example.otterminded.models.DAOCommentaire
import com.example.otterminded.models.DAOQuestion
import com.example.otterminded.support.CommentaireAdapter

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textHome
        homeViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }

        val dailyQuestion: TextView = binding.dailyQuestion

        // Initialisation de QuestionManager avec le contexte de l'activité
        val questionManager = QuestionManager(requireContext())

        // Obtenir l'ID et la question actuels
        val (questionId, question) = questionManager.getCurrentQuestion()

        // Afficher la question dans TextView
        dailyQuestion.text = question

        // Référence du bouton dans le layout
        val commentIcon: ImageView = binding.commentIcon

        // Créer une instance de DAOCommentaire
        val daoCommentaire = DAOCommentaire(requireContext())

        // Obtenir les commentaires de la question
        val commentaires = daoCommentaire.getCommentaireByQuestionId(questionId)

        // Référence au RecyclerView dans votre layout (vu_commentaire)
        val recyclerViewCommentaire: RecyclerView = binding.vuCommentaire

        // Créer un adaptateur CommentaireAdapter en passant la liste des commentaires
        val commentaireAdapter = CommentaireAdapter(commentaires)

        // Associer l'adaptateur au RecyclerView
        recyclerViewCommentaire.layoutManager = LinearLayoutManager(requireContext())
        recyclerViewCommentaire.adapter = commentaireAdapter

        // Ajout d'un OnClickListener au bouton
        commentIcon.setOnClickListener {
            val intent = Intent(requireContext(), CommentaireActivity::class.java)
            intent.putExtra("question_id", questionId) // Passage de l'ID de la question à l'activité UpdateQuestionActivity
            startActivity(intent)
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}