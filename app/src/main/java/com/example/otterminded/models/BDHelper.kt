package com.example.otterminded.models

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class BDHelper(context: Context) :
    SQLiteOpenHelper(context, "biblioloutre", null, 1) {

    override fun onCreate(db: SQLiteDatabase?) {
        db?.beginTransaction()
        try {
            val createTableLivre = """
            CREATE TABLE livre (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                nom VARCHAR(30) NOT NULL,
                datesortie VARCHAR(30) NOT NULL,
                emprunt BOOLEAN DEFAULT 0,
                id_emprunt INTEGER,
                FOREIGN KEY (id_emprunt) REFERENCES utilisateur(id)
            )
        """.trimIndent()
            db?.execSQL(createTableLivre)

            val createTableUtilisateur = """
            CREATE TABLE utilisateur (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                nom VARCHAR(30) NOT NULL,
                email VARCHAR(50) NOT NULL,
                mot_de_passe VARCHAR(30) NOT NULL
            )
        """.trimIndent()
            db?.execSQL(createTableUtilisateur)

            db?.setTransactionSuccessful()
        } catch (e: Exception) {
            Log.e("BDHelper", "Erreur lors de la création des tables : ${e.message}")
        } finally {
            db?.endTransaction()
        }
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS livre")
        db?.execSQL("DROP TABLE IF EXISTS utilisateur")
        onCreate(db)
    }
}