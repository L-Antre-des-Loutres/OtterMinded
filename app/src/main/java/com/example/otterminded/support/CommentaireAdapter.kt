package com.example.otterminded.support

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.otterminded.R
import com.example.otterminded.models.Commentaire
import com.example.otterminded.models.DAOUtilisateur

class CommentaireAdapter(
    private val commentaires: List<Commentaire>,
    private val daoUtilisateur: DAOUtilisateur // Ajout du paramètre daoUtilisateur
) : RecyclerView.Adapter<CommentaireAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val commentaireTextView: TextView = itemView.findViewById(R.id.commentaireTextView)
        val idUtilisateurTextView: TextView = itemView.findViewById(R.id.pseudoTextView) // Ajout de la vue pour afficher le pseudo de l'utilisateur
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_commentaire, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return commentaires.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val commentaire = commentaires[position]
        holder.commentaireTextView.text = commentaire.commentaire

        // Récupération de l'utilisateur par son ID
        val utilisateur = daoUtilisateur.getUserById(commentaire.id_utilisateur)
        if (utilisateur != null) {
            holder.idUtilisateurTextView.text = utilisateur.nom // Affichage du nom d'utilisateur (pseudo)
        } else {
            holder.idUtilisateurTextView.text = "Utilisateur inconnu"
        }
    }
}
