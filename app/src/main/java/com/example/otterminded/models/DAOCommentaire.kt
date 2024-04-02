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

    fun getCommentaireByQuestionId(idQuestion: Long): Commentaire? {
        val db = dbHelper.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM commentaire WHERE id_question = ?", arrayOf(idQuestion.toString()))
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

}