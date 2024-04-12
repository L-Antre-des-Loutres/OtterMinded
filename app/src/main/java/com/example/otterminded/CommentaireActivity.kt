package com.example.otterminded

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.otterminded.ui.commentaire.CommentaireFragment

class CommentaireActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_commentaire)

        // Récupérer les données reçues par l'intent
        val questionId = intent.getLongExtra("question_id", -1)

        // Créer une instance de votre fragment
        val fragment = CommentaireFragment()

        // Créer un Bundle pour les arguments
        val args = Bundle()
        args.putLong("question_id", questionId)

        // Ajouter les arguments au fragment
        fragment.arguments = args

        // Remplacer le fragment dans votre activité
        supportFragmentManager.beginTransaction()
            .replace(R.id.container_commentaire, fragment)
            .commit()
    }
}
