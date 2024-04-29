package com.example.otterminded.models

import android.content.ContentValues
import android.content.Context

class DAOUtilisateur(context: Context) {

    private val dbHelper: BDHelper = BDHelper(context)

    fun tryLogin(email: String, password: String): Utilisateur? {
        val db = dbHelper.readableDatabase
        val cursor = db.rawQuery(
            "SELECT * FROM utilisateur WHERE email = ? AND mot_de_passe = ?",
            arrayOf(email, password)
        )
        var utilisateur: Utilisateur? = null
        if (cursor.moveToFirst()) {
            val id = cursor.getLong(cursor.getColumnIndex("id"))
            val email = cursor.getString(cursor.getColumnIndex("email"))
            val admin = cursor.getInt(cursor.getColumnIndex("admin"))
            utilisateur = Utilisateur(id, email, email, password, admin)
        }
        cursor.close()
        db.close()
        return utilisateur
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
            val admin = cursor.getInt(cursor.getColumnIndex("admin"))
            val utilisateur = Utilisateur(id, nom, email, motDePasse, admin)
            users.add(utilisateur)
        }
        cursor.close()
        db.close()
        return users
    }

    fun getUserByEmail(email: String): Utilisateur? {
        val db = dbHelper.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM utilisateur WHERE email = ?", arrayOf(email))
        var utilisateur: Utilisateur? = null
        if (cursor.moveToFirst()) {
            val id = cursor.getLong(cursor.getColumnIndex("id"))
            val nom  = cursor.getString(cursor.getColumnIndex("nom"))
            val motDePasse = cursor.getString(cursor.getColumnIndex("mot_de_passe"))
            val admin = cursor.getInt(cursor.getColumnIndex("admin"))
            utilisateur = Utilisateur(id, nom, email, motDePasse, admin)
        }
        cursor.close()
        db.close()
        return utilisateur
    }

    fun getUserByUsername(username: String): Utilisateur? {
        val db = dbHelper.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM utilisateur WHERE nom = ?", arrayOf(username))
        var utilisateur: Utilisateur? = null
        if (cursor.moveToFirst()) {
            val id = cursor.getLong(cursor.getColumnIndex("id"))
            val email = cursor.getString(cursor.getColumnIndex("email"))
            val motDePasse = cursor.getString(cursor.getColumnIndex("mot_de_passe"))
            val admin = cursor.getInt(cursor.getColumnIndex("admin"))
            utilisateur = Utilisateur(id, username, email, motDePasse, admin)
        }
        cursor.close()
        db.close()
        return utilisateur
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
            val admin = cursor.getInt(cursor.getColumnIndex("admin"))
            utilisateur = Utilisateur(id, nom, email, motDePasse, admin)
        }
        cursor.close()
        db.close()
        return utilisateur
    }

    fun userIsAdmin(email: String): Boolean {
        val db = dbHelper.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM utilisateur WHERE email = ?", arrayOf(email))
        var isAdmin = false
        if (cursor.moveToFirst()) {
            val admin = cursor.getInt(cursor.getColumnIndex("admin"))
            if (admin == 1) {
                isAdmin = true
            }
        }
        cursor.close()
        db.close()
        return isAdmin
    }

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

    fun updateUsername(id: Long, nom: String): Int {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put("nom", nom)
        }
        val rowsAffected = db.update("utilisateur", values, "id = ?", arrayOf(id.toString()))
        db.close()
        return rowsAffected
    }

    fun updateEmail(id: Long, email: String): Int {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put("email", email)
        }
        val rowsAffected = db.update("utilisateur", values, "id = ?", arrayOf(id.toString()))
        db.close()
        return rowsAffected
    }

    fun updatePassword(id: Long, motDePasse: String): Int {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
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
