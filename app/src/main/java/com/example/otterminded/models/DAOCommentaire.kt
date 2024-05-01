package com.example.otterminded.models

import android.annotation.SuppressLint
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

    @SuppressLint("Range")
    // Obtenir tous les commentaires
    fun getAllCommentaires(): ArrayList<Commentaire> {
        val commentaires = ArrayList<Commentaire>()
        val db = dbHelper.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM commentaire", null)
        while (cursor.moveToNext()) {
            val id = cursor.getLong(cursor.getColumnIndex("id"))
            val idQuestion = cursor.getLong(cursor.getColumnIndex("id_question"))
            val idUtilisateur = cursor.getLong(cursor.getColumnIndex("id_utilisateur"))
            val commentaireText = cursor.getString(cursor.getColumnIndex("commentaire"))
            val commentaire = Commentaire(id, idQuestion, idUtilisateur, commentaireText)
            commentaires.add(commentaire)
        }
        cursor.close()
        db.close()
        return commentaires
    }

    @SuppressLint("Range")
    // Obtenir un commentaire par son id
    fun getCommentaireById(id: Long): Commentaire? {
        val db = dbHelper.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM commentaire WHERE id = ?", arrayOf(id.toString()))
        var commentaire: Commentaire? = null
        if (cursor.moveToFirst()) {
            val idQuestion = cursor.getLong(cursor.getColumnIndex("id_question"))
            val idUtilisateur = cursor.getLong(cursor.getColumnIndex("id_utilisateur"))
            val commentaireText = cursor.getString(cursor.getColumnIndex("commentaire"))
            commentaire = Commentaire(id, idQuestion, idUtilisateur, commentaireText)
        }
        cursor.close()
        db.close()
        return commentaire
    }

    @SuppressLint("Range")
    // Obtenir un commentaire par l'id de l'utilisateur
    fun getCommentaireByUserId(id_utilisateur: Long): Commentaire? {
        val db = dbHelper.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM commentaire WHERE id_utilisateur = ?", arrayOf(id_utilisateur.toString()))
        var commentaire: Commentaire? = null
        if (cursor.moveToFirst()) {
            val id = cursor.getLong(cursor.getColumnIndex("id"))
            val idQuestion = cursor.getLong(cursor.getColumnIndex("id_question"))
            val idUtilisateur = cursor.getLong(cursor.getColumnIndex("id_utilisateur"))
            val commentaireText = cursor.getString(cursor.getColumnIndex("commentaire"))
            commentaire = Commentaire(id, idQuestion, idUtilisateur, commentaireText)
        }
        cursor.close()
        db.close()
        return commentaire
    }

    @SuppressLint("Range")
    // Obtenir un commentaire par l'id de la question
    fun getCommentaireByQuestionId(idQuestion: Long): List<Commentaire> {
        val db = dbHelper.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM commentaire WHERE id_question = ?", arrayOf(idQuestion.toString()))
        val commentaires = mutableListOf<Commentaire>()

        while (cursor.moveToNext()) {
            val id = cursor.getLong(cursor.getColumnIndex("id"))
            val idQuestion = cursor.getLong(cursor.getColumnIndex("id_question"))
            val idUtilisateur = cursor.getLong(cursor.getColumnIndex("id_utilisateur"))
            val commentaireText = cursor.getString(cursor.getColumnIndex("commentaire"))
            val commentaire = Commentaire(id, idQuestion, idUtilisateur, commentaireText)
            commentaires.add(commentaire)
        }

        cursor.close()
        db.close()
        return commentaires
    }

    // Supprimer un commentaire par son id
    fun deleteCommentaire(id: Long): Int {
        val db = dbHelper.writableDatabase
        val deletedRows = db.delete("commentaire", "id = ?", arrayOf(id.toString()))
        db.close()
        return deletedRows
    }
}