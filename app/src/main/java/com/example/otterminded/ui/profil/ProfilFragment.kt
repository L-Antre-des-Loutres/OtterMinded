package com.example.otterminded.ui.login

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.otterminded.R

class ProfileFragment : Fragment() {

    private lateinit var userIdTextView: TextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_profil, container, false)
        userIdTextView = view.findViewById(R.id.userIdTextView)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Récupérer l'ID de l'utilisateur depuis les préférences partagées
        val sharedPreferences = requireActivity().getSharedPreferences("user_session", Context.MODE_PRIVATE)
        val userId = sharedPreferences.getString("user_id", null)
        val userName = sharedPreferences.getString("user_nom", null)

        // Afficher l'ID de l'utilisateur dans le TextView
        userIdTextView.text = "User ID: $userId"
        userIdTextView.text = "User ID: $userName"
    }
}
