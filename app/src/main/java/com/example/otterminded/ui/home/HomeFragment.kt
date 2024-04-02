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
import com.example.otterminded.CommentaireActivity
import com.example.otterminded.databinding.FragmentHomeBinding
import com.example.otterminded.models.DAOQuestion

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

        // ID de la question que vous souhaitez afficher dans TextView
        val questionId: Long = 1 // Mettez l'ID de la question que vous souhaitez afficher ici

        // Créez une instance de DAOQuestion
        val daoQuestion = DAOQuestion(requireContext())

        // Appeler la fonction getQuestionById sur cette instance
        val question = daoQuestion.getQuestionById(questionId)

        // Afficher la question dans TextView
        dailyQuestion.text = question?.question ?: "Question non trouvée"

        // Référence du bouton dans le layout
        val commentIcon: ImageView = binding.commentIcon

        // Ajout d'un OnClickListener au bouton
        commentIcon.setOnClickListener {
            // Intent pour démarrer l'activité de commentaire
            val intent = Intent(requireContext(), CommentaireActivity::class.java)

            // Ajouter la variable questionId en tant que données supplémentaires à l'intent
            intent.putExtra("question_id", questionId)

            startActivity(intent)
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
