package com.example.otterminded.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.otterminded.R

class LoginFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.activity_login, container, false)

        val emailEditText = view.findViewById<EditText>(R.id.emailEditText)
        val passwordEditText = view.findViewById<EditText>(R.id.passwordEditText)
        val loginButton = view.findViewById<Button>(R.id.loginButton)

        loginButton.setOnClickListener {
            // toString() s'assure que les valeurs sont des strings, et trim() que les espaces sont retiré
            val email = emailEditText.text.toString().trim()
            val password = passwordEditText.text.toString().trim()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                if (validateUser(email, password)) {
                    // L'utilisateur est authentifié
                    Toast.makeText(requireContext(), "Connexion réussie", Toast.LENGTH_SHORT).show()
                    // Redirection ici
                } else {
                    // Auth échoué
                    Toast.makeText(requireContext(), "Email ou mot de passe incorrect", Toast.LENGTH_SHORT).show()
                }
            } else {
                // Tous les champs ne sont pas compelt
                Toast.makeText(requireContext(), "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show()
            }
        }

        return view
    }

    private fun validateUser(email: String, password: String): Boolean {
        // Logic de la connexion : vérification de mdp

        return false
    }
}
