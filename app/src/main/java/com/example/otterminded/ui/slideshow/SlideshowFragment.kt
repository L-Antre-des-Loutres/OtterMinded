package com.example.otterminded.ui.slideshow

import QuestionAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.otterminded.R
import com.example.otterminded.databinding.FragmentSlideshowBinding
import com.example.otterminded.models.DAOInitializer

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
        val adapter = QuestionAdapter(questions)

        // Liaison de l'adaptateur avec le RecyclerView
        recyclerView.adapter = adapter

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
