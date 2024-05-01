package com.example.otterminded.ui.approuverQuestion

import android.content.Intent
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
import com.example.otterminded.UpdateQuestionActivity
import com.example.otterminded.databinding.FragmentApprouverQuestionBinding
import com.example.otterminded.databinding.FragmentSlideshowBinding
import com.example.otterminded.models.DAOInitializer
import com.example.otterminded.models.Question
import com.example.otterminded.support.ApprouverAdapter
import com.example.otterminded.support.QuestionAdapter
import com.example.otterminded.ui.slideshow.SlideshowViewModel

class ApprouverQuestionFragment : Fragment() {
    private var _binding: FragmentApprouverQuestionBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val slideshowViewModel =
            ViewModelProvider(this).get(SlideshowViewModel::class.java)

        _binding = FragmentApprouverQuestionBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textApprouverQuestion
        slideshowViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        val recyclerView: RecyclerView = binding.vuApprouverQuestion
        val layoutManager = LinearLayoutManager(requireContext())
        recyclerView.layoutManager = layoutManager

        // Initialisation de DAOInitializer avec le contexte actuel
        DAOInitializer.initialize(requireContext())

        // Récupération de DAOQuestion à partir de DAOInitializer
        val daoQuestion = DAOInitializer.getDAOQuestion()

        // Récupération des questions à partir du DAO
        val questions = daoQuestion.getQuestionsNonApprouver()

        val adapter = ApprouverAdapter(questions, daoQuestion, requireContext()) { questionId ->
            // Gérer le clic sur le bouton "Edit"
            val intent = Intent(requireContext(), UpdateQuestionActivity::class.java)
            intent.putExtra(
                "question_id",
                questionId
            ) // Passage de l'ID de la question à l'activité UpdateQuestionActivity
            startActivity(intent)
        }
        // Liaison de l'adaptateur avec le RecyclerView
        recyclerView.adapter = adapter
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}