package com.example.otterminded.ui.apropos

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AproposViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Bienvenue sur OtterMinded !"
    }
    val text: LiveData<String> = _text
}


