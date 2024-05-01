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

// Adaptateur pour les commentaires
class CommentaireAdapter(

    // Liste des commentaires
    private var commentaires: MutableList<Commentaire>,

    // Utilisation de DAOUtilisateur
    private val daoUtilisateur: DAOUtilisateur,
) : RecyclerView.Adapter<CommentaireAdapter.ViewHolder>() {

    // Classe ViewHolder
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val commentaireTextView: TextView = itemView.findViewById(R.id.commentaireTextView)
        val idUtilisateurTextView: TextView = itemView.findViewById(R.id.pseudoTextView)
    }

    // Méthode onCreateViewHolder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_commentaire, parent, false)
        return ViewHolder(view)
    }

    // Méthode getItemCount
    override fun getItemCount(): Int {
        return commentaires.size
    }

    // Méthode onBindViewHolder
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
        commentaires.clear()
        commentaires.addAll(newCommentaires)
        notifyDataSetChanged()
    }
}