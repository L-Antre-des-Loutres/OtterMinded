package com.example.otterminded.models

import android.content.ContentValues
import android.content.Context

class DAOCommentaire(context: Context) {

    private val dbHelper: BDHelper = BDHelper(context)
    fun addCommentaire(idQuestion: Long, idUtilisateur: Long, commentaire: String): Long {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put("id_question", idQuestion)
            put("id_utilisateur", idUtilisateur)
            put("commentaire", commentaire)
        }
        val newRowId = db.insert("commentaire", null, values)
        db.close()
        return newRowId
    }
}