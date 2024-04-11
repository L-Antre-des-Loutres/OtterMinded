package com.example.otterminded

import android.content.Context
import android.content.SharedPreferences
import com.example.otterminded.models.DAOQuestion
import java.util.*

class QuestionManager(private val context: Context) {

    private val PREFS_NAME = "QuestionPrefs"
    private val LAST_QUESTION_UPDATE_KEY = "last_question_update"
    private val QUESTION_ID_KEY = "question_id"

    private val sharedPreferences: SharedPreferences by lazy {
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    }

    fun getCurrentQuestion(): Pair<Long, String> {
        val currentDate = Calendar.getInstance().time
        val lastUpdateMillis = sharedPreferences.getLong(LAST_QUESTION_UPDATE_KEY, 0)
        val lastUpdateDate = Date(lastUpdateMillis)

        // Comparaison pour vérifier si 24 heures se sont écoulées depuis la dernière mise à jour de la question
        if (currentDate.time - lastUpdateDate.time >= 24 * 60 * 60 * 1000) {
            updateQuestionId()
        }

        // Récupération de la question actuelle
        return getQuestion()
    }


        private fun getQuestion(): Pair<Long, String> {
            val questionId = sharedPreferences.getLong(QUESTION_ID_KEY, -1)

            // Créez une instance de DAOQuestion
            val daoQuestion = DAOQuestion(context)

            // Appeler la fonction getQuestionById sur cette instance
            val question = daoQuestion.getQuestionById(questionId)

            // Récupérer seulement la question dans l'objet question
            val questionText = question?.question ?: "Question introuvable"

            return Pair(questionId, questionText ?: "Question introuvable") // Si la question n'est pas trouvée, retourne une chaîne par défaut
        }



    private fun updateQuestionId() {
        // Créez une instance de DAOQuestion
        val daoQuestion = DAOQuestion(context)

        // Génération d'un nouvel identifiant de question aléatoire

        // Nombre de question dans la table Question
        val nbQuestions = daoQuestion.getNbQuestion()

        // Génération d'un nouvel identifiant de question aléatoire
        val newQuestionId = (1..nbQuestions).random()


        // Enregistrement du nouvel identifiant de question dans les préférences partagées
        sharedPreferences.edit().putLong(QUESTION_ID_KEY, newQuestionId.toLong()).apply()

        // Mettre à jour la date de dernière mise à jour
        sharedPreferences.edit().putLong(LAST_QUESTION_UPDATE_KEY, Calendar.getInstance().timeInMillis).apply()
    }
}
