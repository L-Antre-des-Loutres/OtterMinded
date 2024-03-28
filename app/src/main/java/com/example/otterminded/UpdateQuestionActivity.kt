package com.example.otterminded

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.otterminded.models.DAOInitializer
import com.example.otterminded.models.Question
import com.example.otterminded.ui.slideshow.SlideshowFragment

class UpdateQuestionActivity : AppCompatActivity() {

    private lateinit var editTextTheme: EditText
    private lateinit var editTextQuestion: EditText
    private var questionId: Long = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_question)

        // Récupérer l'ID de la question de l'Intent
        questionId = intent.getLongExtra("question_id", -1)

        // Supposons que vous avez une méthode pour récupérer la question à partir de l'ID
        val daoQuestion = DAOInitializer.getDAOQuestion()
        val question = daoQuestion.getQuestionById(questionId)

        // Lier les éléments de l'interface utilisateur avec les vues XML
        editTextTheme = findViewById(R.id.editTextText)
        editTextQuestion = findViewById(R.id.editTextText2)

        // Remplir les champs EditText avec les données de la question
        editTextTheme.setText(question?.theme ?: "")
        editTextQuestion.setText(question?.question ?: "")

        // Configurez le reste de votre activité ici
    }

// Méthode appelée lors du clic sur le bouton d'update
    fun onUpdateButtonClick(view: View) {
        // Récupérer les nouvelles valeurs des champs EditText
        val newTheme = editTextTheme.text.toString()
        val newQuestion = editTextQuestion.text.toString()

        // Vérifier si les champs ne sont pas vides
        if (newTheme.isNotEmpty() && newQuestion.isNotEmpty()) {
            // Supposons que vous avez une méthode pour mettre à jour la question dans la base de données
            val daoQuestion = DAOInitializer.getDAOQuestion()
            val rowsAffected = daoQuestion.updateQuestion(questionId, newTheme, newQuestion)

            if (rowsAffected > 0) {
                // Afficher un message de succès
                Toast.makeText(this, "La loutre a mis à jour ta question.", Toast.LENGTH_SHORT).show()
                // Rediriger vers la page des questions après la mise à jour
                val intent = Intent(this, SlideshowFragment::class.java)
                startActivity(intent)
            } else {
                // Afficher un message d'erreur si la mise à jour a échoué
                Toast.makeText(this, "La loutre n'a pas réussi à modifier cette question.", Toast.LENGTH_SHORT).show()
            }
        } else {
            // Afficher un message d'erreur si les champs sont vides
            Toast.makeText(this, "La loutre n'aime pas les champs de formulaire vides.", Toast.LENGTH_SHORT).show()
        }
    }
    // Méthode appelée lors du clic sur le bouton de suppression
    // Méthode appelée lors du clic sur le bouton de suppression
    fun onDeleteButtonClick(view: View) {
        // Vérifiez d'abord si l'ID de la question est valide
        if (questionId != -1L) {
            // Affichez une boîte de dialogue de confirmation avant de supprimer la question
            AlertDialog.Builder(this)
                .setTitle("Une loutre : \"Grouuuu Grouuuuu\"")
                .setMessage("(Tu es sûr de vouloir supprimer cette question ?)")
                .setPositiveButton("Oui") { dialog, _ ->
                    // Supprimez la question en utilisant DAO
                    val daoQuestion = DAOInitializer.getDAOQuestion()
                    val rowsAffected = daoQuestion.deleteQuestion(questionId)

                    if (rowsAffected > 0) {
                        // Affichez un message de confirmation si la suppression a réussi
                        Toast.makeText(this, "La loutre a réussi à supprimer cette question.", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this, SlideshowFragment::class.java)
                        startActivity(intent)
                        // Optionnel : Redirigez vers la liste des questions ou terminez cette activité
                        finish()
                    } else {
                        // Affichez un message d'erreur si la suppression a échoué
                        Toast.makeText(this, "Échec de la suppression de la question par la loutre.", Toast.LENGTH_SHORT).show()
                    }
                }
                .setNegativeButton("Non") { dialog, _ ->
                    dialog.dismiss() // Fermer la boîte de dialogue sans rien faire
                }
                .show()
        } else {
            // Affichez un message d'erreur si l'ID de la question est invalide
            Toast.makeText(this, "L'id de la Loutre Question n'est pas valide", Toast.LENGTH_SHORT).show()
        }
    }
}


