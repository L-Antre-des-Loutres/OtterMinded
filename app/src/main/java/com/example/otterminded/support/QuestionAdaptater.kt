import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.otterminded.R
import com.example.otterminded.models.Question

class QuestionAdapter(
    private val questions: MutableList<Question>,
    private val onEditClickListener: (Long) -> Unit // Interface pour gérer les clics sur le bouton "Edit"
) : RecyclerView.Adapter<QuestionAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val themeTextView: TextView = itemView.findViewById(R.id.themeTextView)
        val questionTextView: TextView = itemView.findViewById(R.id.questionTextView)
        val editButton: Button = itemView.findViewById(R.id.editButton) // Ajout du bouton "Edit"
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_question, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val question = questions[position]
        holder.themeTextView.text = question.theme
        holder.questionTextView.text = question.question

        // Gérer le clic sur le bouton "Edit"
        holder.editButton.setOnClickListener {
            // Récupérer l'ID de la question à partir de la position
            val questionId = questions[position].id
            // Appeler l'interface pour gérer le clic sur le bouton "Edit"
            onEditClickListener(questionId)
        }
    }

    override fun getItemCount(): Int {
        return questions.size
    }
}
