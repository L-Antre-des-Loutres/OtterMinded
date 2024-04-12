package com.example.otterminded.ui.login

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.otterminded.R
import com.example.otterminded.models.DAOUtilisateur

class ProfileFragment : Fragment() {

    private lateinit var userIdTextView: TextView
    private lateinit var adminLabelTextView: TextView
    private lateinit var userNameEditView: EditText
    private lateinit var emailEditView: EditText
    private lateinit var passwordEditView: EditText

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_profil, container, false)
        // Zones de texte
        userIdTextView = view.findViewById(R.id.userIdLabelTextView)
        adminLabelTextView = view.findViewById(R.id.adminLabelTextView)
        // Champs de texte
        userNameEditView = view.findViewById(R.id.nameEditText)
        emailEditView = view.findViewById(R.id.emailEditText)
        passwordEditView = view.findViewById(R.id.passwordEditText)
        // Boutons
        val saveNomButton = view.findViewById<Button>(R.id.saveNomButton)
        val saveEmailButton = view.findViewById<Button>(R.id.saveEmailButton)
        val savePasswordButton = view.findViewById<Button>(R.id.savePasswordButton)
        // Dao Utilisateur
        val daoUser = DAOUtilisateur(requireContext()) // Instanciation du DAOUtilisateur

        // Ecouteurs pour les boutons
        saveNomButton.setOnClickListener {
            // Modifier le nom de l'utilisateur dans la base de données
            val userId = view.findViewById<TextView>(R.id.userIdLabelTextView).text.toString().split(":")[1].trim().toLong()
            daoUser.updateUsername(userId, userNameEditView.text.toString())

            // Sauvegarder le nom dans les sharedPreferences
            val sharedPreferences = requireActivity().getSharedPreferences("user_session", Context.MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            editor.putString("user_nom", userNameEditView.text.toString())
            editor.apply()
            userNameEditView.hint = userNameEditView.text.toString()
            userNameEditView.text.clear()
        }

        saveEmailButton.setOnClickListener {
            // Modifier l'email de l'utilisateur dans la base de données
            val userId = view.findViewById<TextView>(R.id.userIdLabelTextView).text.toString().split(":")[1].trim().toLong()
            daoUser.updateEmail(userId, emailEditView.text.toString())

            // Sauvegarder l'email dans les sharedPreferences
            val sharedPreferences = requireActivity().getSharedPreferences("user_session", Context.MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            editor.putString("user_email", emailEditView.text.toString())
            editor.apply()
            emailEditView.hint = emailEditView.text.toString()
            emailEditView.text.clear()
        }

        savePasswordButton.setOnClickListener {
            // Modifier le mot de passe de l'utilisateur dans la base de données
            val userId = view.findViewById<TextView>(R.id.userIdLabelTextView).text.toString().split(":")[1].trim().toLong()
            daoUser.updatePassword(userId, passwordEditView.text.toString())

            // Sauvegarder le mot de passe dans les sharedPreferences
            val sharedPreferences = requireActivity().getSharedPreferences("user_session", Context.MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            editor.putString("user_mdp", passwordEditView.text.toString())
            editor.apply()
            val mdpInvisible = "*".repeat(passwordEditView.text.length)
            passwordEditView.hint = mdpInvisible
            passwordEditView.text.clear()
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val daoUser = DAOUtilisateur(requireContext()) // Instanciation du DAOUtilisateur

        // Récupére l'ID de l'utilisateur depuis les préférences partagées
        val sharedPreferences = requireActivity().getSharedPreferences("user_session", Context.MODE_PRIVATE)

        // Afficher l'ID et le nom de l'utilisateur dans le TextView
        userIdTextView.text = "User ID: "+sharedPreferences.getString("user_id", null).toString()
        userNameEditView.hint = sharedPreferences.getString("user_nom", null).toString()
        emailEditView.hint = sharedPreferences.getString("user_email", null).toString()
        // Methode pour afficher et cacher le mot de passe avec des *
        val mdp = sharedPreferences.getString("user_mdp", null)?.toString() ?: ""
        val mdpInvisible = "*".repeat(mdp.length)
        passwordEditView.hint = mdpInvisible
        // Methode pour afficher le statut admin ou utilisateur
        val isConnected = sharedPreferences.getString("user_email", null)
        // Methode
        if (isConnected != null) {
            val isAdmin = daoUser.userIsAdmin(isConnected)
            if (isAdmin) {
                adminLabelTextView.text = "Admin"
            } else {
                adminLabelTextView.text = "Utilisateur"
            }
        } else {
            adminLabelTextView.text = "Non connecté"
        }
    }
}
