package com.example.otterminded.ui.login

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.otterminded.R

class ProfileFragment : Fragment() {

    private lateinit var userIdTextView: TextView
    private lateinit var userNameEditView: EditText
    private lateinit var emailEditView: EditText
    private lateinit var passwordEditView: EditText

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_profil, container, false)
        userIdTextView = view.findViewById(R.id.userIdLabelTextView)
        userNameEditView = view.findViewById(R.id.nameEditText)
        emailEditView = view.findViewById(R.id.emailEditText)
        passwordEditView = view.findViewById(R.id.passwordEditText)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Récupére l'ID de l'utilisateur depuis les préférences partagées
        val sharedPreferences = requireActivity().getSharedPreferences("user_session", Context.MODE_PRIVATE)

        // Afficher l'ID de l'utilisateur dans le TextView
        userIdTextView.text = "User ID: "+sharedPreferences.getString("user_id", null).toString()
        userNameEditView.hint = sharedPreferences.getString("user_nom", null).toString()
        emailEditView.hint = sharedPreferences.getString("user_email", null).toString()
        // Methode pour cacher le mot de passe avec des *
        val mdp = sharedPreferences.getString("user_mdp", null)?.toString() ?: ""
        val mdpInvisible = "*".repeat(mdp.length)
        passwordEditView.hint = mdpInvisible
    }
}
