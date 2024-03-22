package com.example.otterminded.models

class Question (
    var id: Int,
    var theme: String,
    var question: String
) {
    override fun toString(): String {
        return "Question (id='$id', theme='$theme', question='$question')"
    }
}
