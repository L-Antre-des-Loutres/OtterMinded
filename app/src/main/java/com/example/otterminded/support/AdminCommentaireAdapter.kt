package com.example.otterminded.support

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.otterminded.R
import com.example.otterminded.models.Commentaire
import com.example.otterminded.models.DAOUtilisateur

class AdminCommentaireAdapter(
    var commentaires: MutableList<Commentaire>, // Définir commentaires comme MutableList
    private val daoUtilisateur: DAOUtilisateur,
    private val onDeleteClickListener: (Int) -> Unit
) : RecyclerView.Adapter<AdminCommentaireAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val commentaireTextView: TextView = itemView.findViewById(R.id.commentaireTextView)
        val idUtilisateurTextView: TextView = itemView.findViewById(R.id.pseudoTextView)
        val deleteButton: Button = itemView.findViewById(R.id.deleteButton) // Bouton de suppression

        init {
            deleteButton.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    onDeleteClickListener(position) // Appel du gestionnaire de clic sur le bouton de suppression
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_admin_commentaire, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return commentaires.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val commentaire = commentaires[position]
        holder.commentaireTextView.text = commentaire.commentaire

        val utilisateur = daoUtilisateur.getUserById(commentaire.id_utilisateur)
        if (utilisateur != null) {
            holder.idUtilisateurTextView.text = utilisateur.nom
        } else {
            holder.idUtilisateurTextView.text = "Utilisateur inconnu"
        }
    }

    // Méthode pour mettre à jour les données de l'adaptateur avec de nouveaux commentaires
    fun updateData(newCommentaires: List<Commentaire>) {
        commentaires = newCommentaires.toMutableList()
        notifyDataSetChanged()
    }
}

