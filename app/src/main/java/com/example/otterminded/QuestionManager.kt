package com.example.otterminded

import android.content.Context
import android.content.SharedPreferences
import com.example.otterminded.models.DAOQuestion
import java.util.*

class QuestionManager(private val context: Context) {

    private val PREFS_NAME = "QuestionPrefs"
    private val LAST_QUESTION_UPDATE_KEY = "last_question_update"
    private val QUESTION_ID_KEY = "question_id"

    // Ajout de la référence aux préférences partagées
    private val sharedPreferences: SharedPreferences by lazy {
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    }

    // Méthode pour récupérer toutes les questions
    private fun getAllQuestions(): List<Pair<Long, String>> {
        val daoQuestion = DAOQuestion(context)
        return daoQuestion.getQuestionsApprouver().map { question ->
            Pair(question.id, question.question)
        }
    }

    // Méthode pour récupérer la question actuelle
    private fun getQuestion(): Pair<Long, String> {
        val questionId = sharedPreferences.getLong(QUESTION_ID_KEY, -1)

        // Création d'une instance de DAOQuestion
        val daoQuestion = DAOQuestion(context)

        // Appel de la fonction getQuestionById sur cette instance
        val question = daoQuestion.getQuestionById(questionId)

        // Récupération seulement de la question dans l'objet question
        val questionText = question?.question ?: "Question introuvable"

        return Pair(questionId, questionText) // Si la question n'est pas trouvée, retourne une chaîne par défaut
    }

    // Méthode pour récupérer la question actuelle et gérer la mise à jour si nécessaire
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

    // Méthode pour mettre à jour l'identifiant de la question
    private fun updateQuestionId() {
        // Récupération de toutes les questions disponibles
        val allQuestions = getAllQuestions()

        if (allQuestions.isNotEmpty()) {
            // Choisir un identifiant de question aléatoire parmi toutes les questions
            val randomQuestionIndex = (0 until allQuestions.size).random()
            val randomQuestion = allQuestions[randomQuestionIndex]

            // Enregistrement du nouvel identifiant de question dans les préférences partagées
            sharedPreferences.edit().putLong(QUESTION_ID_KEY, randomQuestion.first).apply()

            // Mettre à jour la date de dernière mise à jour
            sharedPreferences.edit().putLong(LAST_QUESTION_UPDATE_KEY, Calendar.getInstance().timeInMillis).apply()
        } else {
            // Gérer le cas où il n'y a pas de questions disponibles
        }
    }
}


