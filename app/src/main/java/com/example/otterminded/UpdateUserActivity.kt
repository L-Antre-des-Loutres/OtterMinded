package com.example.otterminded

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.otterminded.models.DAOInitializer
import com.example.otterminded.notification.NotificationCreate
import com.example.otterminded.ui.slideshow.SlideshowFragment

class UpdateUserActivity : AppCompatActivity() {

    private lateinit var nameEditText: EditText
    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private var userId: Long = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_user)

        // Récupérer l'ID de luser
        userId = intent.getLongExtra("user_id", -1)

        // Initialiser DAOInitializer avec le contexte actuel
        val daoUtilisateur = DAOInitializer.getDAOUtilisateur()
        val user = daoUtilisateur.getUserById(userId)

        // Lier les éléments de l'interface utilisateur avec les vues XML
        nameEditText = findViewById(R.id.editNameText)
        emailEditText = findViewById(R.id.editEmailText)
        passwordEditText = findViewById(R.id.editPasswordText)

        // Remplir les champs EditText avec les données de l'utilisateur
        nameEditText.setText(user?.nom ?: "")
        emailEditText.setText(user?.email ?: "")
        passwordEditText.setText(user?.motDePasse ?: "")

        // Configurez le reste de votre activité ici
    }

// Méthode appelée lors du clic sur le bouton d'update
@RequiresApi(Build.VERSION_CODES.O)
fun onUpdateButtonClick(view: View) {
        // Récupérer les nouvelles valeurs des champs EditText
        val newName = nameEditText.text.toString()
        val newEmail = emailEditText.text.toString()
        val newMdp = passwordEditText.text.toString()

        // Vérifier si les champs ne sont pas vides
        if (newName.isNotEmpty() && newEmail.isNotEmpty() && newMdp.isNotEmpty()) {

            val daoUtilisateur = DAOInitializer.getDAOUtilisateur()
            val rowsAffected = daoUtilisateur.updateUser(userId, newName, newEmail, newMdp)

            if (rowsAffected > 0) {
                val notificationCreate = NotificationCreate()

                // Utiliser la méthode createNotification pour afficher une notification
                notificationCreate.createNotification(
                    context = this,
                    title = "Modification d'un utilisateur !",
                    message = "L'utilisateur $newEmail a été mise à jour."
                )

                // Afficher un message de succès
                Toast.makeText(this, "La loutre a mis à jour cet utilisateur.", Toast.LENGTH_SHORT).show()

                val intent = Intent(this, MainActivity::class.java)

                startActivity(intent)
            } else {
                // Afficher un message d'erreur si la mise à jour a échoué
                Toast.makeText(this, "La loutre n'a pas réussi à modifier cet utilisateur.", Toast.LENGTH_SHORT).show()
            }
        } else {
            // Afficher un message d'erreur si les champs sont vides
            Toast.makeText(this, "La loutre n'aime pas les champs de formulaire vides.", Toast.LENGTH_SHORT).show()
        }
    }
    // Méthode appelée lors du clic sur le bouton de suppression
    fun onDeleteButtonClick(view: View) {
        // Vérifiez d'abord si l'ID de l'utilisateur est valide
        if (userId != -1L) {
            // Affichez une boîte de dialogue de confirmation avant de supprimer l'utilisateur
            AlertDialog.Builder(this)
                .setTitle("Arisoutre : \"Grouuuu Grouuuuu\"")
                .setMessage("(Traduction : Tu es sûr de vouloir supprimer cet utilisateur ?)")
                .setPositiveButton("Oui") { dialog, _ ->

                    // Supprimez l' utilisateur
                    val daoUtilisateur = DAOInitializer.getDAOUtilisateur()
                    val rowsAffected = daoUtilisateur.deleteUser(userId)

                    if (rowsAffected > 0) {
                        // Affichez un message de confirmation si la suppression a réussi
                        Toast.makeText(this, "La loutre a réussi à supprimer cet utilisateur.", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this, SlideshowFragment::class.java)
                        startActivity(intent)

                        finish()
                    } else {
                        // Affichez un message d'erreur si la suppression a échoué
                        Toast.makeText(this, "Échec de la suppression de l'utilisateur par la loutre.", Toast.LENGTH_SHORT).show()
                    }
                }
                .setNegativeButton("Non") { dialog, _ ->
                    dialog.dismiss() // Fermer la boîte de dialogue sans rien faire
                }
                .show()
        } else {
            // Affichez un message d'erreur si l'ID de l'utilisateur est invalide
            Toast.makeText(this, "L'id d'utilisateur cette Loutre n'est pas valide", Toast.LENGTH_SHORT).show()
        }
    }
}


