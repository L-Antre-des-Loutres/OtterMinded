package com.example.otterminded.models

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class BDHelper(context: Context) :
    SQLiteOpenHelper(context, "LocaLoutre", null, 1) {

    private val daoQuestion: DAOQuestion = DAOQuestion(context)

    override fun onCreate(db: SQLiteDatabase) {
        createTableQuestion(db)
        insertDefaultData(db)
        createTableUtilisateur(db)
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

    private fun createTableUtilisateur(db: SQLiteDatabase) {
        val createTableUtilisateur = """
            CREATE TABLE utilisateur (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                nom VARCHAR(30) NOT NULL,
                email VARCHAR(50) NOT NULL,
                mot_de_passe VARCHAR(30) NOT NULL
            )
        """.trimIndent()
        db.execSQL(createTableUtilisateur)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS question")
        db.execSQL("DROP TABLE IF EXISTS utilisateur")
        onCreate(db)
    }
}
