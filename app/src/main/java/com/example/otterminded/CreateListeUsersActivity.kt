package com.example.otterminded

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.Spinner
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import com.example.otterminded.models.DAOUtilisateur
import com.example.otterminded.notification.NotificationCreate
import com.example.otterminded.ui.slideshow.SlideshowFragment


class CreateListeUsersActivity : AppCompatActivity() {

    private lateinit var emailEditText: TextInputEditText
    private lateinit var passwordEditText: TextInputEditText
    private lateinit var registerButton: Button
    private lateinit var daoUtilisateur: DAOUtilisateur

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // Initialize views
        emailEditText = findViewById(R.id.emailEditText)
        passwordEditText = findViewById(R.id.passwordEditText)
        registerButton = findViewById(R.id.registerButton)

        // Initialize DAO
        daoUtilisateur = DAOUtilisateur(this)

        // Set click listener for add user button
        registerButton.setOnClickListener {
            val email = emailEditText.text.toString()
            val mdp = passwordEditText.text.toString()

            if (email.isNotEmpty() && mdp.isNotEmpty()) {
                val newRowId = daoUtilisateur.addUser("user", "mail", "password")
                if (newRowId != -1L) {

                    // Envoi d'une notification pour prévenir de l'ajout du user
                    // Créer une instance de NotificationAddUser
                    val notificationCreate = NotificationCreate()

                    // Utiliser la méthode createNotification pour afficher une notification
                    notificationCreate.createNotification(
                        context = this,
                        title = "Ajout d'un user !",
                        message = "Un nouvel user a été ajoutée. La voici : $email"
                    )


                    // Successfully added user
                    Toast.makeText(this, "user ajouté avec succès !", Toast.LENGTH_SHORT).show()
                    // You can add further handling here if needed
                    val intent = Intent(this, SlideshowFragment::class.java)
                    startActivity(intent)
                } else {
                    // Failed to add user
                    // You can add further handling here if needed
                }
            } else {
                // Theme or user text is empty
                // You can add further handling here if needed
            }
        }
    }
}

