package com.example.otterminded.ui.admin

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.otterminded.R
import com.example.otterminded.ui.approuverQuestion.ApprouverQuestionFragment

// Liste des fragments à afficher et des titres associés
private val TAB_TITLES = arrayOf(
    R.string.tab_text_1,
    R.string.tab_text_2,
    R.string.tab_text_3,
    R.string.tab_text_4
)

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
class SectionsPagerAdapter(private val context: Context, fm: FragmentManager) :
    FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        // Retourne un fragment différent en fonction de la position de la page
        return when (position) {
            0 -> QuestionFragment() // Lancement du fragement de question
            1 -> CommentaireFragment() // Lancement du fragement de commentaire
            2 -> UtilisateurFragement() // Lancement du fragement d'utilisateur
            3-> ApprouverQuestionFragment() // Lancement du fragement d'approuver question
        else -> ErreurFragement() // Lancement du fragement d'erreur
        }
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return context.resources.getString(TAB_TITLES[position] as Int)
    }

    override fun getCount(): Int {
        // Affiche 4 pages au total.
        return 4
    }
}
