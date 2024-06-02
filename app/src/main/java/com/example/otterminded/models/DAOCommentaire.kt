package com.example.otterminded.models

import android.content.ContentValues
import android.content.Context
import android.util.Log

class DAOCommentaire(context: Context) {

    private val dbHelper: BDHelper = BDHelper(context)

    // Méthode pour créer un log
    private fun createLog(idUser: Int, type: String, statut: String, idQuestion: Long?, idCommentaire: Long?) {
        val db = dbHelper.writableDatabase
        val valuesLogs = ContentValues().apply {
            put("id_user", idUser)
            put("type", type)
            put("statut", statut)
            put("id_question", idQuestion)
            put("id_commentaire", idCommentaire)
        }
        db.insert("logs", null, valuesLogs)
        Log.i("Log", "Log créé : id_user $idUser, type : $type, statut : $statut, id_question : $idQuestion, id_commentaire : $idCommentaire")
        db.close()
    }

    // Ajouter un commentaire et ajouter dans les logs
    fun addCommentaire(idQuestion: Long, idUtilisateur: Long, commentaire: String): Long {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put("id_question", idQuestion)
            put("id_utilisateur", idUtilisateur)
            put("commentaire", commentaire)
        }
        val newRowId = db.insert("commentaire", null, values)
        db.close()
        // Créer un log pour l'ajout du commentaire
        createLog(idUtilisateur.toInt(), "Commentaire", "Ajout", idQuestion, newRowId)
        return newRowId
    }

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

    // Obtenir un commentaire par id utilisateur
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

    // Obtenir les commentaires par id question
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

    // Supprimer un commentaire par son id et ajouter dans les logs
    fun deleteCommentaire(id: Long): Int {
        val db = dbHelper.writableDatabase
        val deletedRows = db.delete("commentaire", "id = ?", arrayOf(id.toString()))
        if (deletedRows > 0) {
            // Créer un log pour la suppression du commentaire
            createLog(1, "Commentaire", "Suppression", null, id)
        }
        db.close()
        return deletedRows
    }
}