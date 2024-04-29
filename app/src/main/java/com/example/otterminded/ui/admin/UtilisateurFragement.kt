package com.example.otterminded.ui.admin

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.otterminded.R
import com.example.otterminded.UpdateUserActivity
import com.example.otterminded.databinding.FragmentListeUsersBinding
import com.example.otterminded.models.DAOInitializer
import com.example.otterminded.support.AdminUserAdaptater
import com.example.otterminded.ui.slideshow.SlideshowViewModel

class UtilisateurFragement : Fragment() {

    private var _binding: FragmentListeUsersBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val slideshowViewModel =
            ViewModelProvider(this).get(SlideshowViewModel::class.java)

        _binding = FragmentListeUsersBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textListeUsers

        // Configuration du RecyclerView et de l'adaptateur
        val recyclerView: RecyclerView = binding.vuUser
        val layoutManager = LinearLayoutManager(requireContext())
        recyclerView.layoutManager = layoutManager

        // Initialisation de DAOInitializer avec le contexte actuel
        DAOInitializer.initialize(requireContext())

        // Récupération du DAO à partir de DAOInitializer
        val daoUtilisateur = DAOInitializer.getDAOUtilisateur()

        // Récupération des utilisateurs à partir du DAO
        val users = daoUtilisateur.getAllUsers()

        // Création de l'adaptateur avec les utilisateurs récupérées
        val adapter = AdminUserAdaptater(users) { userId ->
            // Gérer le clic sur le bouton "Modifier"
            val intent = Intent(requireContext(), UpdateUserActivity::class.java)
            intent.putExtra("user_id", userId) // Passage de l'ID de l'utilisateur à l'activité UpdateUserActivity
            startActivity(intent)
        }

        // Liaison de l'adaptateur avec le RecyclerView
        recyclerView.adapter = adapter

        // Récupérer l'ID de l'utilisateur depuis les préférences partagées
        val sharedPreferences = requireContext().getSharedPreferences("user_session", Context.MODE_PRIVATE)
        val admin = sharedPreferences.getInt("admin", -1)
        return root
    }
}