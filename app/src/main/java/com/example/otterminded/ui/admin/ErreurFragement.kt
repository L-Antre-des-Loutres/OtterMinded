package com.example.otterminded.ui.admin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.otterminded.R

class ErreurFragement : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Afficher le fragment par défaut
        return inflater.inflate(R.layout.fragement_admin_utilisateur, container, false)
    }
}