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
import com.example.otterminded.models.Utilisateur

class AdminUserAdaptater(
    private val users: MutableList<Utilisateur>,
    private val onEditClickListener: (Long) -> Unit // Interface pour gérer les clics sur le bouton "Edit"
) : RecyclerView.Adapter<AdminUserAdaptater.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val userEmailTextView: TextView = itemView.findViewById(R.id.userEmailTextView)
        val userPasswordTextView: TextView = itemView.findViewById(R.id.userPasswordTextView)
        val editButton: Button = itemView.findViewById(R.id.editButton) // Ajout du bouton "Edit"
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_admin_user, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        // Récupérer l'ID de l'utilisateur depuis les préférences partagées
        val sharedPreferences = holder.itemView.context.getSharedPreferences("user_session", Context.MODE_PRIVATE)
        val admin = sharedPreferences.getInt("admin", 0)
        val user = users[position]
        holder.userEmailTextView.text = user.email
        holder.userPasswordTextView.text = user.motDePasse

        // Gérer le clic sur le bouton "Edit"
        holder.editButton.setOnClickListener {
            if (admin == 1) {
                // Récupérer l'ID de l'utilisateur à partir de la position
                val userID = users[position].id
                // Appeler l'interface pour gérer le clic sur le bouton "Modifier"
                onEditClickListener(userID)
            } else {
                Toast.makeText(holder.itemView.context, "La fonction d'édition n'est disponible que pour un utilisateur administrateur", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun getItemCount(): Int {
        return users.size
    }
}
