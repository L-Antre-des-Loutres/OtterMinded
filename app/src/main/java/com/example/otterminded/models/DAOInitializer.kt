package com.example.otterminded.models

import android.content.Context

object DAOInitializer {
    private var daoQuestion: DAOQuestion? = null

    fun initialize(context: Context) {
        if (daoQuestion == null) {
            daoQuestion = DAOQuestion(context)
        }
    }

    fun getDAOQuestion(): DAOQuestion {
        if (daoQuestion == null) {
            throw IllegalStateException("DAOInitializer.initialize must be called before accessing DAOQuestion")
        }
        return daoQuestion!!
    }
}
