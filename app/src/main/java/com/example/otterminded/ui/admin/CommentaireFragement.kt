package com.example.otterminded.ui.admin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.otterminded.R
import com.example.otterminded.models.DAOCommentaire
import com.example.otterminded.models.DAOQuestion
import com.example.otterminded.models.DAOUtilisateur
import com.example.otterminded.support.AdminCommentaireAdapter
class CommentaireFragment : Fragment() {
    private lateinit var daoQuestion: DAOQuestion
    private lateinit var daoUtilisateur: DAOUtilisateur
    private lateinit var daoCommentaire: DAOCommentaire
    private lateinit var spinner: Spinner
    private lateinit var recyclerView: RecyclerView
    private lateinit var commentAdapter: AdminCommentaireAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragement_admin_commentaire, container, false)

        spinner = view.findViewById(R.id.spinnerOptions)
        recyclerView = view.findViewById(R.id.vu_admin_commentaire)

        daoQuestion = DAOQuestion(requireContext())
        daoUtilisateur = DAOUtilisateur(requireContext())
        daoCommentaire = DAOCommentaire(requireContext())

        val questions = daoQuestion.getAllQuestions()
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, questions.map { it.question })
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selectedQuestionId = questions[position].id
                val comments = daoCommentaire.getCommentaireByQuestionId(selectedQuestionId)
                commentAdapter.updateData(comments)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Faites quelque chose si rien n'est sélectionné (si nécessaire)
            }
        }

        // Initialiser le RecyclerView et son adaptateur
        commentAdapter = AdminCommentaireAdapter(ArrayList(), daoUtilisateur) { position -> onDeleteCommentaire(position) }
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = commentAdapter

        return view
    }

    private fun onDeleteCommentaire(position: Int) {
        try {
            val commentaire = commentAdapter.commentaires[position]

            // Afficher une boîte de dialogue de confirmation
            AlertDialog.Builder(requireContext())
                .setTitle("Confirmer la suppression")
                .setMessage("Êtes-vous sûr de vouloir supprimer ce commentaire ?")
                .setPositiveButton("Oui") { dialog, which ->
                    try {
                        // Supprimer le commentaire de la base de données
                        daoCommentaire.deleteCommentaire(commentaire.id)

                        // Mettre à jour la liste des commentaires dans l'adaptateur
                        commentAdapter.commentaires.removeAt(position)
                        commentAdapter.notifyDataSetChanged() // Mettre à jour le RecyclerView
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
                .setNegativeButton("Non", null)
                .show()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


}