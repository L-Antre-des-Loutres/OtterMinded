package com.example.otterminded.models

// Classe Commentaire
data class Commentaire(
    val id: Long,
    val id_question: Long,
    val id_utilisateur: Long,
    val commentaire: String
)
