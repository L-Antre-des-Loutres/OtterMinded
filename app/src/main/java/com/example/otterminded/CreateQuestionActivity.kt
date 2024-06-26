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
import com.example.otterminded.models.DAOQuestion
import com.example.otterminded.notification.NotificationCreate
import com.example.otterminded.ui.slideshow.SlideshowFragment


class CreateQuestionActivity : AppCompatActivity() {

    private lateinit var themeSpinner: Spinner
    private lateinit var questionEditText: TextInputEditText
    private lateinit var addQuestionButton: Button
    private lateinit var daoQuestion: DAOQuestion

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_question)

        // Initialize views
        themeSpinner = findViewById(R.id.spinnerC)
        questionEditText = findViewById(R.id.questionC)
        addQuestionButton = findViewById(R.id.buttonC)

        // Initialize DAO
        daoQuestion = DAOQuestion(this)

        // Set click listener for add question button
        addQuestionButton.setOnClickListener {
            val theme = themeSpinner.selectedItem.toString()
            val questionText = questionEditText.text.toString()

            if (theme.isNotEmpty() && questionText.isNotEmpty()) {
                val newRowId = daoQuestion.addQuestion(theme, questionText)
                if (newRowId != -1L) {

                    // Envoi d'une notification pour prévenir de l'ajout de la question
                    // Créer une instance de NotificationAddQuestion
                    val notificationCreate = NotificationCreate()

                    // Utiliser la méthode createNotification pour afficher une notification
                    notificationCreate.createNotification(
                        context = this,
                        title = "Ajout d'une question !",
                        message = "Une nouvelle question a été ajoutée. La voici : $questionText"
                    )


                    // Successfully added question
                    Toast.makeText(this, "Question ajouté avec succès !", Toast.LENGTH_SHORT).show()
                    // You can add further handling here if needed
                    val intent = Intent(this, SlideshowFragment::class.java)
                    startActivity(intent)
                } else {
                    // Failed to add question
                    // You can add further handling here if needed
                }
            } else {
                // Theme or question text is empty
                // You can add further handling here if needed
            }
        }
    }
}

