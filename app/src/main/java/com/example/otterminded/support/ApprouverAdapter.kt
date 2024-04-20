package com.example.otterminded.support
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ExpandableListView.OnChildClickListener
import android.widget.TextView
import android.widget.TextView.OnEditorActionListener
import androidx.recyclerview.widget.RecyclerView
import com.example.otterminded.R
import com.example.otterminded.models.Question

class ApprouverAdapter(
    private val questions: MutableList<Question>,
    onApprouverClickListener: (Long) -> Unit)
    : RecyclerView.Adapter<ApprouverAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val themeTextView: TextView = itemView.findViewById(R.id.themeTextView)
        val questionTextView: TextView = itemView.findViewById(R.id.questionTextView)
        val approveButton: Button = itemView.findViewById(R.id.approuverButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_approuver, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = questions[position]
        holder.themeTextView.text = currentItem.theme
        holder.questionTextView.text = currentItem.question

        // Utilisez "holder.approveButton" au lieu de "approveButton"
        holder.approveButton.setOnClickListener {
            // Action à effectuer lors de l'appui sur le bouton "Approuver"
        }
    }

    override fun getItemCount(): Int {
        return questions.size
    }
}