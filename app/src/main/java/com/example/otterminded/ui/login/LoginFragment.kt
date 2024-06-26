package com.example.otterminded.ui.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.otterminded.MainActivity
import com.example.otterminded.R
import com.example.otterminded.models.DAOUtilisateur

class LoginFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val intent = Intent(requireContext(), MainActivity::class.java)

        val view = inflater.inflate(R.layout.activity_login, container, false)

        val emailEditText = view.findViewById<EditText>(R.id.emailEditText)
        val passwordEditText = view.findViewById<EditText>(R.id.passwordEditText)
        val loginButton = view.findViewById<Button>(R.id.loginButton)
        val registerButton = view.findViewById<Button>(R.id.registerButton)

        loginButton.setOnClickListener {
            // Ecouteur pour le bouton de login

            // toString() s'assure que les valeurs sont des strings, et trim() que les espaces sont retiré
            val email = emailEditText.text.toString().trim()
            val password = passwordEditText.text.toString().trim()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                if (validateUser(email, password)) {
                    val daoUser = DAOUtilisateur(requireContext()) // Instanciation du DAOUtilisateur
                    val authUser = daoUser.getUserByEmail(email)

                    // L'utilisateur est authentifié
                    Toast.makeText(requireContext(), "Connexion réussie! Bienvenue "+authUser?.nom.toString()+"!", Toast.LENGTH_SHORT).show()

                    // Engistrement de la session en local dans sharedPreferences
                    val sharedPreferences = requireActivity().getSharedPreferences("user_session", Context.MODE_PRIVATE)
                    val editor = sharedPreferences.edit()
                    editor.putString("user_id", authUser?.id.toString())
                    editor.putString("user_nom", authUser?.nom.toString())
                    editor.putString("user_email", authUser?.email.toString())
                    editor.putString("user_mdp", authUser?.motDePasse.toString())

                    authUser?.admin?.let { it1 -> editor.putInt("admin", it1.toInt()) } // Jsp pq mais ok Android Studio

                    editor.apply()

                    // Redirection à faire ici
                    startActivity(intent)
                } else {
                    // Auth échoué
                    Toast.makeText(requireContext(), "Email ou MDP incorrect : Mail : "+email+" MDP : "+password, Toast.LENGTH_SHORT).show()
                }
            } else {
                // Tous les champs ne sont pas compelt
                Toast.makeText(requireContext(), "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show()
            }
        }

        registerButton.setOnClickListener {
            // Ecouteur pour le bouton d'enregistrement

            // toString() s'assure que les valeurs sont des strings, et trim() que les espaces sont retiré
            val email = emailEditText.text.toString().trim()
            val password = passwordEditText.text.toString().trim()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                if (doesAccountExist(email, password)) {
                    // Un utilisateur avec ce mail éxiste déjà, enregistrement échoué
                    Toast.makeText(requireContext(), "Un compte éxiste déjà avec cette Email : "+email, Toast.LENGTH_SHORT).show()
                } else {
                    // Pas d'utilisateur avec ce mail éxiste déjà, enregistrement ajout de l'utilisateur à la table
                    Toast.makeText(requireContext(), "Compte crée avec : "+email+". Tenter de vous connecter!", Toast.LENGTH_SHORT).show()
                }
            }
        }

        afficherUtilisateurs()

        return view
    }

    private fun validateUser(email: String, password: String): Boolean {
        // Logic de la connexion : vérification de email + mdp

        val daoUser = DAOUtilisateur(requireContext()) // Instanciation du DAOUtilisateur
        val user = daoUser.tryLogin(email, password) // Appel de la fonction tryLogin()// false : la connexion a échoué, aucun utilisateurs trouvés

        // true : utilisateur trouvé, la connexion a réussi
        return user != null
    }

    private fun doesAccountExist(email: String, password: String): Boolean {
        // Creation du compte si un utilisateur avec ce mail n'existe pas

        val daoUser = DAOUtilisateur(requireContext())
        val user = daoUser.getUserByEmail(email) // Appel de la fonction getUserByEmail()

        if (user != null) {
            // true : utilisateur trouvé
            return true
        } else {
            // false : aucun utilisateurs trouvés, céation du compte
            val newUserId = daoUser.addUser("Anomyme", email, password)
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
