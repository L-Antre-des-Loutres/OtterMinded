package com.example.otterminded.ui.slideshow

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.otterminded.CreateQuestionActivity
import com.example.otterminded.R
import com.example.otterminded.UpdateQuestionActivity
import com.example.otterminded.databinding.FragmentSlideshowBinding
import com.example.otterminded.models.DAOInitializer
import com.example.otterminded.support.QuestionAdapter

class SlideshowFragment : Fragment() {

    private var _binding: FragmentSlideshowBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val slideshowViewModel =
            ViewModelProvider(this).get(SlideshowViewModel::class.java)

        _binding = FragmentSlideshowBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textSlideshow
        slideshowViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }

        // Configuration du RecyclerView et de l'adaptateur
        val recyclerView: RecyclerView = root.findViewById(R.id.vu_question)
        val layoutManager = LinearLayoutManager(requireContext())
        recyclerView.layoutManager = layoutManager

        // Initialisation de DAOInitializer avec le contexte actuel
        DAOInitializer.initialize(requireContext())

        // Récupération de DAOQuestion à partir de DAOInitializer
        val daoQuestion = DAOInitializer.getDAOQuestion()

        // Récupération des questions à partir du DAO
        val questions = daoQuestion.getAllQuestions()

        // Création de l'adaptateur avec les questions récupérées
        val adapter = QuestionAdapter(questions) { questionId ->
            // Gérer le clic sur le bouton "Edit"
            val intent = Intent(requireContext(), UpdateQuestionActivity::class.java)
            intent.putExtra("question_id", questionId) // Passage de l'ID de la question à l'activité UpdateQuestionActivity
            startActivity(intent)
        }

        // Liaison de l'adaptateur avec le RecyclerView
        recyclerView.adapter = adapter

        // Référence du bouton dans le layout
        val createQuestionButton: Button = root.findViewById(R.id.createQuestionButton)

        // Ajout d'un OnClickListener au bouton
        createQuestionButton.setOnClickListener {
            // Intent pour démarrer l'activité de création de question
            val intent = Intent(requireContext(), CreateQuestionActivity::class.java)
            startActivity(intent)
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}


