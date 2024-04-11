package com.example.otterminded.models

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class BDHelper(context: Context) :
    SQLiteOpenHelper(context, "LocaLoutre", null, 1) {

    override fun onCreate(db: SQLiteDatabase) {
        createTableQuestion(db)
        insertDefaultData(db)
        createTableUtilisateur(db)
        createTableCommentaire(db)
        insertDefaultUtilisateur(db)
    }


    private fun createTableQuestion(db: SQLiteDatabase) {
        val createTableQuestion = """
            CREATE TABLE question (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                theme VARCHAR(50) NOT NULL,
                question VARCHAR(50) NOT NULL
            )
        """.trimIndent()
        db.execSQL(createTableQuestion)
    }

    private fun insertDefaultData(db: SQLiteDatabase) {
        val defaultData = arrayOf(
            arrayOf("Histoire", "Quand a eu lieu la Révolution française ?"),
            arrayOf("Géographie", "Quel est le plus grand fleuve du monde ?"),
            arrayOf("Sciences", "Quelle est la formule chimique de l'eau ?")
        )
        for (data in defaultData) {
            val values = ContentValues()
            values.put("theme", data[0])
            values.put("question", data[1])
            db.insert("question", null, values)
        }
    }

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

    private fun insertDefaultUtilisateur(db: SQLiteDatabase) {
        db.execSQL("INSERT INTO utilisateur (nom, email, mot_de_passe) VALUES ('Coco', 'coco@coco.com', 'coco', 1)")
        db.execSQL("INSERT INTO utilisateur (nom, email, mot_de_passe) VALUES ('a', 'a', 'a', 0)")
        db.execSQL("INSERT INTO utilisateur (nom, email, mot_de_passe) VALUES ('User', 'user@user.com', 'user')")
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS question")
        db.execSQL("DROP TABLE IF EXISTS utilisateur")
        db.execSQL("DROP TABLE IF EXISTS commentaire")
        onCreate(db)
    }
}
