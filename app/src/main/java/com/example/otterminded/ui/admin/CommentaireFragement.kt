package com.example.otterminded.ui.admin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.fragment.app.Fragment
import com.example.otterminded.R
import com.example.otterminded.models.DAOQuestion

class CommentaireFragment : Fragment() {
    private lateinit var daoQuestion: DAOQuestion

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragement_admin_commentaire, container, false)

        // Récupérer une référence au Spinner
        val spinner = view.findViewById<Spinner>(R.id.spinnerOptions)

        // Créer une instance de DAOQuestion
        daoQuestion = DAOQuestion(requireContext())

        // Récupérer les questions à partir du DAO
        val questions = daoQuestion.getAllQuestions()

        // Créer un adaptateur pour le Spinner
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, questions.map { it.question })

        // Définir le layout de l'élément déroulant
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        // Associer l'adaptateur au Spinner
        spinner.adapter = adapter

        return view
    }
}


//     }