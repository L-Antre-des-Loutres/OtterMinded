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
import com.example.otterminded.databinding.FragmentHomeBinding
import com.example.otterminded.models.DAOCommentaire
import com.example.otterminded.models.DAOQuestion
import com.example.otterminded.service.DailyQuestionService
import com.example.otterminded.support.CommentaireAdapter

class HomeFragment : Fragment(), DailyQuestionService.QuestionListener {

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

        // Set up listener for receiving daily question
        val serviceIntent = Intent(requireContext(), DailyQuestionService::class.java)
        requireContext().startService(serviceIntent)

        // Initialize DAOQuestion
        val daoQuestion = DAOQuestion(requireContext())

        // Get today's question
        val questionId = getQuestionId()
        val question = daoQuestion.getQuestionById(questionId)

        // Display the question in TextView
        dailyQuestion.text = question?.question ?: "Question not found"

        // Set up RecyclerView for comments
        setUpRecyclerView(questionId)

        // Set up comment icon click listener
        setUpCommentIconClickListener(questionId)

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    // Function to get question ID for the current day
    private fun getQuestionId(): Long {
        val daoQuestion = DAOQuestion(requireContext())
        val nbQuestion: Int = daoQuestion.getNbQuestion()
        val currentTimeMillis = System.currentTimeMillis()
        val millisecondsIn24Hours = 24 * 60 * 60 * 1000
        val daysSinceEpoch = currentTimeMillis / millisecondsIn24Hours
        return daysSinceEpoch % nbQuestion
    }

    // Function to set up RecyclerView for comments
    private fun setUpRecyclerView(questionId: Long) {
        val daoCommentaire = DAOCommentaire(requireContext())
        val commentaires = daoCommentaire.getCommentaireByQuestionId(questionId)
        val recyclerViewCommentaire: RecyclerView = binding.vuCommentaire
        val commentaireAdapter = CommentaireAdapter(commentaires)
        recyclerViewCommentaire.layoutManager = LinearLayoutManager(requireContext())
        recyclerViewCommentaire.adapter = commentaireAdapter
    }

    // Function to set up click listener for comment icon
    private fun setUpCommentIconClickListener(questionId: Long) {
        val commentIcon: ImageView = binding.commentIcon
        commentIcon.setOnClickListener {
            val intent = Intent(requireContext(), CommentaireActivity::class.java)
            intent.putExtra("question_id", questionId)
            startActivity(intent)
        }
    }

    // Implementation of DailyQuestionService.QuestionListener
    override fun onQuestionReceived(question: String) {
        activity?.runOnUiThread {
            binding.dailyQuestion.text = question
        }
    }
}
