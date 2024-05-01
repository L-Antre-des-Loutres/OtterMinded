package com.example.otterminded.models

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import org.json.JSONObject

class BDHelper(context: Context) :
    SQLiteOpenHelper(context, "BDDOtterMinded", null, 1) {

    private val context = context

        // Action à réaliser lors de la création de l'application
    override fun onCreate(db: SQLiteDatabase) {
        createTableQuestion(db)
        createTableUtilisateur(db)
        createTableCommentaire(db)

        // Insertion de données par défaut depuis les fichiers JSON
        insertDefaultUtilisateur(db)
        insertDefaultQuestion(db)
        insertDefaultCommentaires(db)
    }

    // Création de la table question
    private fun createTableQuestion(db: SQLiteDatabase) {
        val createTableQuestion = """
            CREATE TABLE question (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                theme VARCHAR(50) NOT NULL,
                question VARCHAR(50) NOT NULL,
                approuver INTEGER DEFAULT 0
            )
        """.trimIndent()
        db.execSQL(createTableQuestion)
    }
    
    // Création de la table commentaire
    private fun createTableCommentaire(db: SQLiteDatabase) {
        val createTableCommentaire = """
        CREATE TABLE commentaire (
            id INTEGER PRIMARY KEY AUTOINCREMENT,
            id_question INTEGER NOT NULL,
            id_utilisateur INTEGER NOT NULL,
            commentaire TEXT NOT NULL,
            FOREIGN KEY(id_question) REFERENCES question(id),
            FOREIGN KEY(id_utilisateur) REFERENCES utilisateur(id)
        )
    """.trimIndent()
        db.execSQL(createTableCommentaire)
    }


    // Création de la table utilisateur
    private fun createTableUtilisateur(db: SQLiteDatabase) {
        val createTableUtilisateur = """
            CREATE TABLE utilisateur (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                nom VARCHAR(30) NOT NULL,
                email VARCHAR(50) NOT NULL,
                mot_de_passe VARCHAR(30) NOT NULL,
                admin TINYINT NOT NULL DEFAULT 0
            )
        """.trimIndent()
        db.execSQL(createTableUtilisateur)
    }

    // Insertion de données par défaut
    private fun insertDefaultUtilisateur(db: SQLiteDatabase) {
        // Lecture du fichier JSON
        val jsonString = context.assets.open("initialUsers.json").bufferedReader().use {
            it.readText()
        }

        // Conversion du JSON en objet JSON
        val jsonObject = JSONObject(jsonString)

        // Récupération du tableau d'utilisateurs
        val utilisateursArray = jsonObject.getJSONArray("utilisateurs")

        for (i in 0 until utilisateursArray.length()) {
            val utilisateurObj = utilisateursArray.getJSONObject(i)

            // Récupération des données de chaque utilisateur
            val nom = utilisateurObj.getString("nom")
            val email = utilisateurObj.getString("email")
            val motDePasse = utilisateurObj.getString("mot_de_passe")
            val isAdmin = utilisateurObj.getInt("admin") == 1

            val values = ContentValues().apply {
                put("nom", nom)
                put("email", email)
                put("mot_de_passe", motDePasse)
                put("admin",
                    if (isAdmin) 1 else 0
                )
            }
            db.insert("utilisateur", null, values)
        }
    }

    private fun insertDefaultQuestion(db: SQLiteDatabase) {
        // Lecture du fichier JSON
        val jsonString = context.assets.open("initialQuestions.json").bufferedReader().use {
            it.readText()
        }

        // Conversion du JSON en objet JSON
        val jsonObject = JSONObject(jsonString)

        // Récupération du tableau de questions
        val questionsArray = jsonObject.getJSONArray("questions")

        // Insertion des questions dans la base de données
        for (i in 0 until questionsArray.length()) {
            val questionObj = questionsArray.getJSONObject(i)

            // Récupération des données de chaque question
            val theme = questionObj.getString("theme")
            val question = questionObj.getString("titre")

            val values = ContentValues().apply {
                put("theme", theme)
                put("question", question)
            }
            db.insert("question", null, values)
        }
    }

    private fun insertDefaultCommentaires(db: SQLiteDatabase) {
        // Lecture du fichier JSON
        val jsonString = context.assets.open("initialCommentaires.json").bufferedReader().use {
            it.readText()
        }

        // Conversion du JSON en objet JSON
        val jsonObject = JSONObject(jsonString)

        // Récupération du tableau de commentaires
        val commentairesArray = jsonObject.getJSONArray("commentaires")

        // Insertion des commentaires dans la base de données
        for (i in 0 until commentairesArray.length()) {
            val commentaireObj = commentairesArray.getJSONObject(i)

            // Récupération des données de chaque commentaire
            val idQuestion = commentaireObj.getInt("id_question")
            val idUtilisateur = commentaireObj.getInt("id_utilisateur")
            val commentaireText = commentaireObj.getString("commentaire")

            val values = ContentValues().apply {
                put("id_question", idQuestion)
                put("id_utilisateur", idUtilisateur)
                put("commentaire", commentaireText)
            }
            db.insert("commentaire", null, values)
        }
    }


    // Action à réaliser lors de la mise à jour de l'application
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS question")
        db.execSQL("DROP TABLE IF EXISTS utilisateur")
        db.execSQL("DROP TABLE IF EXISTS commentaire")
        onCreate(db)
    }
}
