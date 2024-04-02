import android.os.Bundle
import android.widget.Button
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import com.example.otterminded.R
import com.google.android.material.textfield.TextInputEditText
import com.example.otterminded.models.DAOQuestion


class AddQuestionActivity : AppCompatActivity() {

    private lateinit var themeSpinner: Spinner
    private lateinit var questionEditText: TextInputEditText
    private lateinit var addQuestionButton: Button
    private lateinit var daoQuestion: DAOQuestion

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_question)

        // Initialize views
        themeSpinner = findViewById(R.id.spinnerC)
        questionEditText = findViewById(R.id.questionC)
        addQuestionButton = findViewById(R.id.buttonC)

        // Initialize DAO
        daoQuestion = DAOQuestion(this)

        // Set click listener for add question button
        addQuestionButton.setOnClickListener {
            val theme = themeSpinner.selectedItem.toString()
            val questionText = questionEditText.text.toString()

            if (theme.isNotEmpty() && questionText.isNotEmpty()) {
                val newRowId = daoQuestion.addQuestion(theme, questionText)
                if (newRowId != -1L) {
                    // Successfully added question
                    // You can add further handling here if needed
                    finish() // Finish the activity after adding the question
                } else {
                    // Failed to add question
                    // You can add further handling here if needed
                }
            } else {
                // Theme or question text is empty
                // You can add further handling here if needed
            }
        }
    }
}