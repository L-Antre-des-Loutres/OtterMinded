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

class AdminQuestionAdaptater(

    // Ajout de la liste des questions
    private val questions: MutableList<Question>,

    // Ajout de l'interface pour gérer les clics sur le bouton "Edit"
    private val onEditClickListener: (Long) -> Unit // Interface pour gérer les clics sur le bouton "Edit"
) : RecyclerView.Adapter<AdminQuestionAdaptater.ViewHolder>() {

    // Classe ViewHolder
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val themeTextView: TextView = itemView.findViewById(R.id.themeTextView)
        val questionTextView: TextView = itemView.findViewById(R.id.questionTextView)
        val editButton: Button = itemView.findViewById(R.id.editButton) // Ajout du bouton "Edit"
    }

    // Méthode onCreateViewHolder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_admin_question, parent, false)
        return ViewHolder(view)
    }

    // Méthode onBindViewHolder
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        // Récupérer l'ID de l'utilisateur depuis les préférences partagées
        val sharedPreferences = holder.itemView.context.getSharedPreferences("user_session", Context.MODE_PRIVATE)
        val admin = sharedPreferences.getInt("admin", 0)
        val question = questions[position]
        holder.themeTextView.text = question.theme
        holder.questionTextView.text = question.question

        // Gérer le clic sur le bouton "Edit"
        holder.editButton.setOnClickListener {
            if (admin == 1) {
                // Récupérer l'ID de la question à partir de la position
                val questionId = questions[position].id
                // Appeler l'interface pour gérer le clic sur le bouton "Edit"
                onEditClickListener(questionId)
            } else {
                Toast.makeText(holder.itemView.context, "La fonction d'édition n'est disponible que pour un utilisateur administrateur", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // Méthode getItemCount
    override fun getItemCount(): Int {
        return questions.size
    }
}
