package com.example.otterminded.support

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.otterminded.R
import com.example.otterminded.models.Commentaire

class CommentaireAdapter(
    private val commentaires: MutableList<Commentaire>,
    private val onEditClickListener: (Long) -> Unit // Interface pour gérer les clics sur le bouton "Edit"
) : RecyclerView.Adapter<CommentaireAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val idQuestionTextView: TextView = itemView.findViewById(R.id.idQuestionTextView)
        val idUtilisateurTextView: TextView = itemView.findViewById(R.id.idUtilisateurTextView)
        val commentaireTextView: TextView = itemView.findViewById(R.id.commentaireTextView)
        val editButton: Button = itemView.findViewById(R.id.editButton) // Ajout du bouton "Edit"
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_commentaire, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val commentaire = commentaires[position]
        holder.idQuestionTextView.text = commentaire.id_question.toString()
        holder.idUtilisateurTextView.text = commentaire.id_utilisateur.toString()
        holder.commentaireTextView.text = commentaire.commentaire

        // Gérer le clic sur le bouton "Edit"
        holder.editButton.setOnClickListener {
            // Récupérer l'ID du commentaire à partir de la position
            val commentaireId = commentaires[position].id
            // Appeler l'interface pour gérer le clic sur le bouton "Edit"
            onEditClickListener(commentaireId)
        }
    }

    override fun getItemCount(): Int {
        return commentaires.size
    }
}
