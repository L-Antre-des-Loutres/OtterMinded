package com.example.otterminded.ui.gallery

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class GalleryViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Bienvenue sur OtterMinded !"
    }
    val text: LiveData<String> = _text

    private val _recyclerview = MutableLiveData<String>().apply {
        value = "Coucou"
    }
    val recyclerView: LiveData<String> = _recyclerview
}