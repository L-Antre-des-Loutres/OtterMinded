package com.example.otterminded

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.otterminded.ui.main.CommentaireFragment

class CommentaireActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_commentaire)
        // Récupérer l'intent qui a démarré cette activité
        val intent = intent

        // Récupérer la variable questionId de l'intent
        val questionId = intent.getLongExtra("question_id", 0)
    }
}