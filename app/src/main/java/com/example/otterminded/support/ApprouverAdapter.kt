package com.example.otterminded.support
import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.otterminded.R
import com.example.otterminded.models.DAOQuestion
import com.example.otterminded.models.Question

class ApprouverAdapter(
    private val questions: MutableList<Question>,
    private val daoQuestion: DAOQuestion,
    private val context: Context,
    onApprouverClickListener: (Long) -> Unit
) : RecyclerView.Adapter<ApprouverAdapter.ViewHolder>() {

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

        // Ajoutez un écouteur de clic au bouton "Approuver"
        holder.approveButton.setOnClickListener {
            // Afficher une boîte de dialogue de confirmation
            showConfirmationDialog(currentItem)
        }
    }

    private fun showConfirmationDialog(question: Question) {
        AlertDialog.Builder(context)
            .setTitle("Confirmation")
            .setMessage("Êtes-vous sûr de vouloir approuver cette question ?")
            .setPositiveButton("Oui") { _, _ ->
                // Approuver la question
                daoQuestion.approveQuestion(question.id)
                // Notifiez l'adaptateur du changement
                notifyDataSetChanged()
            }
            .setNegativeButton("Non", null)
            .show()
    }

    override fun getItemCount(): Int {
        return questions.size
    }
}