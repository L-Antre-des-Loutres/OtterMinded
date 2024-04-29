package com.example.otterminded.support

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.otterminded.R
import com.example.otterminded.models.Question

class QuestionAdapter(
    private val questions: MutableList<Question>,
    private val onEditClickListener: (Long) -> Unit // Interface pour gérer les clics sur le bouton "Edit"
) : RecyclerView.Adapter<QuestionAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val themeTextView: TextView = itemView.findViewById(R.id.themeTextView)
        val questionTextView: TextView = itemView.findViewById(R.id.questionTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_question, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        // Récupérer l'ID de l'utilisateur depuis les préférences partagées
        val sharedPreferences = holder.itemView.context.getSharedPreferences("user_session", Context.MODE_PRIVATE)
        val admin = sharedPreferences.getInt("admin", 0)
        val question = questions[position]
        holder.themeTextView.text = question.theme
        holder.questionTextView.text = question.question
    }

    override fun getItemCount(): Int {
        return questions.size
    }
}
