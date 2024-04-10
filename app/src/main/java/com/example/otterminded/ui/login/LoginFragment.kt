package com.example.otterminded.ui.login

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.otterminded.R
import com.example.otterminded.models.DAOUtilisateur

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
                    Toast.makeText(requireContext(), "Connexion réussie! Bienvenue!", Toast.LENGTH_SHORT).show()
                    // Redirection ici
                } else {
                    // Auth échoué
                    Toast.makeText(requireContext(), "Email ou MDP incorrect : Mail : "+email+" MDP : "+password, Toast.LENGTH_SHORT).show()
                }
            } else {
                // Tous les champs ne sont pas compelt
                Toast.makeText(requireContext(), "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show()
            }
        }

        afficherUtilisateurs()

        return view
    }

    private fun validateUser(email: String, password: String): Boolean {
        // Logic de la connexion : vérification de email + mdp

        val daoUser = DAOUtilisateur(requireContext()) // Instanciation du DAOUtilisateur
        val user = daoUser.tryLogin(email, password) // Appel de la fonction tryLogin()

        if (user != null) {
            // true : utilisateur trouvé, la connexion a réussi
            return true
        } else {
            // false : la connexion a échoué, aucun utilisateurs trouvés
            return false
        }
    }

    // Méthode où vous voulez afficher les utilisateurs
    private fun afficherUtilisateurs() {
        val daoUser = DAOUtilisateur(requireContext())

        // Obtenez tous les utilisateurs
        val utilisateurs = daoUser.getAllUsers()

        // Parcourez la liste des utilisateurs et imprimez-les dans les logs
        Log.d("AffichageUtilisateur", "Affichage des utilisateurs de la base:")
        for (utilisateur in utilisateurs) {
            Log.d("Utilisateur", "ID: ${utilisateur.id}, Nom: ${utilisateur.nom}, Email: ${utilisateur.email}, Mot de passe: ${utilisateur.motDePasse}")
        }
    }
}
