package com.example.otterminded.models

data class Commentaire(
    val id: Long,
    val id_question: Long,
    val id_utilisateur: Long,
    val commentaire: String
)
