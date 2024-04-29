package com.example.otterminded.ui.approuverQuestion

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ApprouverQuestionViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Question"
    }
    val text: LiveData<String> = _text
}