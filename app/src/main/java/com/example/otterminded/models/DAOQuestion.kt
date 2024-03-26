package com.example.otterminded.models

import android.content.ContentValues
import android.content.Context

class DAOQuestion(context: Context) {

    private val dbHelper: BDHelper = BDHelper(context)

    fun addQuestion(theme: String, question: String): Long {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put("theme", theme)
            put("question", question)
        }
        val newRowId = db.insert("question", null, values)
        db.close()
        return newRowId
    }

    fun getAllQuestions(): ArrayList<Question> {
        val questions = ArrayList<Question>()
        val db = dbHelper.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM question", null)
        while (cursor.moveToNext()) {
            val id = cursor.getLong(cursor.getColumnIndex("id"))
            val theme = cursor.getString(cursor.getColumnIndex("theme"))
            val questionText = cursor.getString(cursor.getColumnIndex("question"))
            val question = Question(id, theme, questionText)
            questions.add(question)
        }
        cursor.close()
        db.close()
        return questions
    }

    fun getQuestionById(id: Long): Question? {
        val db = dbHelper.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM question WHERE id = ?", arrayOf(id.toString()))
        var question: Question? = null
        if (cursor.moveToFirst()) {
            val theme = cursor.getString(cursor.getColumnIndex("theme"))
            val questionText = cursor.getString(cursor.getColumnIndex("question"))
            question = Question(id, theme, questionText)
        }
        cursor.close()
        db.close()
        return question
    }

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

    fun deleteQuestion(id: Long): Int {
        val db = dbHelper.writableDatabase
        val rowsAffected = db.delete("question", "id = ?", arrayOf(id.toString()))
        db.close()
        return rowsAffected
    }
}


