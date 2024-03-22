package com.example.otterminded.models

import android.annotation.SuppressLint
import android.database.sqlite.SQLiteDatabase
import android.content.ContentValues
import android.content.Context
import android.util.Log
import com.example.otterminded.R
import org.json.JSONObject
import java.io.InputStream

class DAOQuestion(context: Context) {
    private lateinit var monBDHelper: BDHelper
    private lateinit var maBase: SQLiteDatabase

    init {
        monBDHelper = BDHelper(context)
    }

    // Test de la base
    fun testBase(): Int {
        open()
        val req = "SELECT COUNT(id) FROM question"
        val cursor = maBase.rawQuery(req, null)
        cursor.moveToFirst()
        val count = cursor.getInt(0)
        cursor.close()
        close()
        return count
    }

    fun deleteQuestion(id: Int) {
        open()
        val colonne = "id = ?"
        val args = arrayOf(id.toString())
        maBase.delete("question", colonne, args)
        close()
    }

    public fun getLesQuestions(): MutableList<Question> {
        val lesQuestions: MutableList<Question> = mutableListOf()

        val cursor = maBase.query(
            "question",
            arrayOf("id", "theme", "question"),
            null,
            null,
            null,
            null,
            "theme"
        )

        try {
            while (cursor.moveToNext()) {
                val id = cursor.getInt(cursor.getColumnIndexOrThrow("id"))
                val theme = cursor.getString(cursor.getColumnIndexOrThrow("theme"))
                val question = cursor.getString(cursor.getColumnIndexOrThrow("question"))
                val uneQuestion = Question(id, theme, question)
                lesQuestions.add(uneQuestion)
            }
        } catch (e: Exception) {
            Log.e("DB_ERROR", "Error while retrieving questions: ${e.message}")
        } finally {
            cursor.close()
        }

        return lesQuestions
    }
    fun updateQuestion(uneQuestion: Question): Int {
        open()
        val values = ContentValues().apply {
            put("theme", uneQuestion.theme)
            put("question", uneQuestion.question)
        }

        val colonne = "id = ?"
        val args = arrayOf(uneQuestion.id.toString())
        val result = maBase.update("question", values, colonne, args)
        close()
        return result
    }

    fun insertQuestion(uneQuestion: Question): Long {
        open()
        val values = ContentValues().apply {
            put("theme", uneQuestion.theme)
            put("question", uneQuestion.question)
        }
        val result = maBase.insert("question", null, values)
        close()
        return result
    }

    private fun open() {
        maBase = monBDHelper.writableDatabase
    }

    private fun close() {
        maBase.close()
    }
}

