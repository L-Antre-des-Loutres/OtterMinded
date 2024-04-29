package com.example.otterminded.models

import android.content.Context

object DAOInitializer {
    private var daoQuestion: DAOQuestion? = null
    private var daoUtilisateur: DAOUtilisateur? = null

    fun initialize(context: Context) {
        if (daoQuestion == null) {
            daoQuestion = DAOQuestion(context)
        }
        if (daoUtilisateur == null) {
            daoUtilisateur = DAOUtilisateur(context)
        }
    }

    fun getDAOQuestion(): DAOQuestion {
        if (daoQuestion == null) {
            throw IllegalStateException("DAOInitializer.initialize must be called before accessing DAOQuestion")
        }
        return daoQuestion!!
    }

    fun getDAOUtilisateur(): DAOUtilisateur {
        if (daoUtilisateur == null) {
            throw IllegalStateException("DAOInitializer.initialize must be called before accessing DAOUtilisateur")
        }
        return daoUtilisateur!!
    }
}
