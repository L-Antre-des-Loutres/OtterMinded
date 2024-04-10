package com.example.otterminded.models

import android.content.ContentValues
import android.content.Context

class DAOUtilisateur(context: Context) {

    private val dbHelper: BDHelper = BDHelper(context)

    fun addUser(nom: String, email: String, motDePasse: String): Long {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put("nom", nom)
            put("email", email)
            put("mot_de_passe", motDePasse)
        }
        val newRowId = db.insert("utilisateur", null, values)
        db.close()
        return newRowId
    }

    fun getAllUsers(): ArrayList<Utilisateur> {
        val users = ArrayList<Utilisateur>()
        val db = dbHelper.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM utilisateur", null)

        // moveToNext() permet d'aller de ligne en ligne
        while (cursor.moveToNext()) {
            val id = cursor.getLong(cursor.getColumnIndex("id"))
            val nom = cursor.getString(cursor.getColumnIndex("nom"))
            val email = cursor.getString(cursor.getColumnIndex("email"))
            val motDePasse = cursor.getString(cursor.getColumnIndex("mot_de_passe"))
            val utilisateur = Utilisateur(id, nom, email, motDePasse)
            users.add(utilisateur)
        }
        cursor.close()
        db.close()
        return users
    }

    fun getUserById(id: Long): Utilisateur? {
        val db = dbHelper.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM utilisateur WHERE id = ?", arrayOf(id.toString()))
        var utilisateur: Utilisateur? = null

        // moveToNext() permet d'aller de ligne en ligne
        if (cursor.moveToFirst()) {
            val nom = cursor.getString(cursor.getColumnIndex("nom"))
            val email = cursor.getString(cursor.getColumnIndex("email"))
            val motDePasse = cursor.getString(cursor.getColumnIndex("mot_de_passe"))
            utilisateur = Utilisateur(id, nom, email, motDePasse)
        }
        cursor.close()
        db.close()
        return utilisateur
    }

    fun updateUser(id: Long, nom: String, email: String, motDePasse: String): Int {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put("nom", nom)
            put("email", email)
            put("mot_de_passe", motDePasse)
        }
        val rowsAffected = db.update("utilisateur", values, "id = ?", arrayOf(id.toString()))
        db.close()
        return rowsAffected
    }

    fun deleteUser(id: Long): Int {
        val db = dbHelper.writableDatabase
        val rowsAffected = db.delete("utilisateur", "id = ?", arrayOf(id.toString()))
        db.close()
        return rowsAffected
    }
}
