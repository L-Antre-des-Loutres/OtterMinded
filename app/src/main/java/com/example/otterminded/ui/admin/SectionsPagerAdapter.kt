package com.example.otterminded.ui.admin

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.otterminded.R
import com.example.otterminded.models.Utilisateur

private val TAB_TITLES = arrayOf(
    R.string.tab_text_1,
    R.string.tab_text_2,
    R.string.tab_text_3
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
            0 -> QuestionFragment() // Utilisation directe du nom de classe du premier fragment
            1 -> CommentaireFragment() // Utilisation directe du nom de classe du deuxième fragment
            2 -> UtilisateurFragement() // Utilisation directe du nom de classe du troisième fragment
            else -> PlaceholderFragment.newInstance(position + 1)
        }
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return context.resources.getString(TAB_TITLES[position])
    }

    override fun getCount(): Int {
        // Affiche 3 pages au total.
        return 3
    }
}
