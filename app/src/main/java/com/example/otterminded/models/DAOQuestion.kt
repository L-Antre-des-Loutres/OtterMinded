package com.example.otterminded.models
import android.content.ContentValues
import android.content.Context
import android.util.Log
import com.example.otterminded.models.BDHelper
import com.example.otterminded.models.Question

/* Rappel de la structure de la table logs :
    Table : logs

    Id logs
    Id_user : Id de l'utilisateur qui à fait cette action
    Type : Commentaire où Question
    Statut : Ajout / Suppression
    Id_question : NULL si c'est un commentaire
    Id_commentaire : NULL si c'est une question
*/


class DAOQuestion(context: Context) {

    private val dbHelper: BDHelper = BDHelper(context)

    // Ajouter une question et ajouter dans les logs
    fun addQuestion(theme: String, question: String): Long {
        val db = dbHelper.writableDatabase
        // Ajouter une entrée dans la table question
        val values = ContentValues().apply {
            put("theme", theme)
            put("question", question)
        }
        val newRowId = db.insert("question", null, values)
        // Ajouter une entrée dans la table logs
        val valuesLogs = ContentValues().apply {
            put("id_user", 1) // Remplacez ceci par l'ID de l'utilisateur qui a ajouté la question
            put("type", "Question")
            put("statut", "Ajout")
            put("id_question", newRowId)
        }
        db.insert("logs", null, valuesLogs)
        // Ecrire dans les logcat
        Log.i("Log question","Question ajoutée : id_user 1, type : Question, statut : Ajout, id_question : $newRowId")

        db.close()
        return newRowId
    }

    // Obtenir toutes les questions
    fun getAllQuestions(): ArrayList<Question> {
        val questions = ArrayList<Question>()
        val db = dbHelper.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM question", null)
        while (cursor.moveToNext()) {
            val id = cursor.getLong(cursor.getColumnIndex("id"))
            val theme = cursor.getString(cursor.getColumnIndex("theme"))
            val questionText = cursor.getString(cursor.getColumnIndex("question"))
            val approuver = cursor.getInt(cursor.getColumnIndex("approuver"))
            val question = Question(id, theme, questionText, approuver)
            questions.add(question)
        }
        cursor.close()
        db.close()
        return questions
    }

    // Obtenir une question par son id
    fun getQuestionById(id: Long): Question? {
        val db = dbHelper.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM question WHERE id = ?", arrayOf(id.toString()))
        var question: Question? = null
        if (cursor.moveToFirst()) {
            val theme = cursor.getString(cursor.getColumnIndex("theme"))
            val questionText = cursor.getString(cursor.getColumnIndex("question"))
            val approuver = cursor.getInt(cursor.getColumnIndex("approuver"))
            question = Question(id, theme, questionText, approuver)
        }
        cursor.close()
        db.close()
        return question
    }

    // Mettre à jour une question
    fun updateQuestion(id: Long, theme: String, question: String): Int {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put("theme", theme)
            put("question", question)
        }
        val rowsAffected = db.update("question", values, "id = ?", arrayOf(id.toString()))
        db.close()
        return rowsAffected
    }

    // Supprimer une question par son id
    fun deleteQuestion(id: Long): Int {
        val db = dbHelper.writableDatabase
        val rowsAffected = db.delete("question", "id = ?", arrayOf(id.toString()))
        db.close()
        return rowsAffected
    }

    // Obtenir le nombre de questions
    fun getNbQuestion(): Int {
        val db = dbHelper.readableDatabase
        val cursor = db.rawQuery("SELECT COUNT(*) FROM question", null)
        var count = 0
        if (cursor.moveToFirst()) {
            count = cursor.getInt(0)
        }
        cursor.close()
        db.close()
        return count
    }

    // Approuver une question
    fun approveQuestion(id: Long): Int {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put("approuver", 1) // Mettez à jour la valeur de approuver à 1
        }
        val rowsAffected = db.update("question", values, "id = ?", arrayOf(id.toString()))
        db.close()
        return rowsAffected
    }
    fun getQuestionsNonApprouver(): ArrayList<Question> {
        val questions = ArrayList<Question>()
        val db = dbHelper.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM question WHERE approuver = 0", null)
        while (cursor.moveToNext()) {
            val id = cursor.getLong(cursor.getColumnIndex("id"))
            val theme = cursor.getString(cursor.getColumnIndex("theme"))
            val questionText = cursor.getString(cursor.getColumnIndex("question"))
            val approuver = cursor.getInt(cursor.getColumnIndex("approuver"))
            val question = Question(id, theme, questionText, approuver)
            questions.add(question)
        }
        cursor.close()
        db.close()
        return questions
    }
    fun getQuestionsApprouver(): ArrayList<Question> {
    // Obtenir les questions approuvées
        val questions = ArrayList<Question>()
        val db = dbHelper.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM question WHERE approuver = 1", null)
        while (cursor.moveToNext()) {
            val id = cursor.getLong(cursor.getColumnIndex("id"))
            val theme = cursor.getString(cursor.getColumnIndex("theme"))
            val questionText = cursor.getString(cursor.getColumnIndex("question"))
            val approuver = cursor.getInt(cursor.getColumnIndex("approuver"))
            val question = Question(id, theme, questionText, approuver)
            questions.add(question)
        }
        cursor.close()
        db.close()
        return questions
    }
}