package com.example.otterminded.ui.home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.otterminded.CommentaireActivity
import com.example.otterminded.databinding.FragmentHomeBinding
import com.example.otterminded.models.DAOCommentaire
import com.example.otterminded.QuestionManager
import com.example.otterminded.models.Commentaire
import com.example.otterminded.models.DAOUtilisateur
import com.example.otterminded.support.CommentaireAdapter

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        val textView: TextView = binding.textHome
        homeViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }

        // Créer une instance de DAOUtilisateur
        val daoUtilisateur = DAOUtilisateur(requireContext())

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
        val commentaires: MutableList<Commentaire> = daoCommentaire.getCommentaireByQuestionId(questionId).toMutableList()

        // Référence au RecyclerView dans votre layout (vu_commentaire)
        val recyclerViewCommentaire: RecyclerView = binding.vuCommentaire

        val commentaireAdapter = CommentaireAdapter(commentaires, daoUtilisateur)

        // Associer l'adaptateur au RecyclerView
        recyclerViewCommentaire.layoutManager = LinearLayoutManager(requireContext())
        recyclerViewCommentaire.adapter = commentaireAdapter

        // Récupération du statut de l'utilisateur
        val sharedPreferences = requireContext().getSharedPreferences("user_session", Context.MODE_PRIVATE)
        val admin = sharedPreferences.getInt("admin", -1)

        // Ajout d'un OnClickListener au bouton
        commentIcon.setOnClickListener {
            if (admin == 0 || admin == 1) {
                // Intent pour démarrer l'activité de création de question
                val intent = Intent(requireContext(), CommentaireActivity::class.java)
                intent.putExtra("question_id", questionId) // Passage de l'ID de la question à l'activité UpdateQuestionActivity
                startActivity(intent)
            } else {
                Toast.makeText(requireContext(), "Merci de vous connecter pour écrire un commentaire.", Toast.LENGTH_SHORT).show()
            }
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
