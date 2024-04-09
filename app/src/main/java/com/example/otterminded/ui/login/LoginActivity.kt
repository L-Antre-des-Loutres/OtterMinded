import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.otterminded.R

class LoginActivity : AppCompatActivity() {

    private lateinit var dbHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        dbHelper = DatabaseHelper(this)

        val emailEditText = findViewById<EditText>(R.id.emailEditText)
        val passwordEditText = findViewById<EditText>(R.id.passwordEditText)
        val loginButton = findViewById<Button>(R.id.loginButton)

        loginButton.setOnClickListener {
            val email = emailEditText.text.toString().trim()
            val password = passwordEditText.text.toString().trim()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                if (validateUser(email, password)) {
                    // L'utilisateur est authentifié
                    Toast.makeText(this, "Connexion réussie", Toast.LENGTH_SHORT).show()
                    // Vous pouvez démarrer une nouvelle activité ou effectuer d'autres actions ici
                } else {
                    Toast.makeText(this, "Email ou mot de passe incorrect", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun validateUser(email: String, password: String): Boolean {
        val db = dbHelper.readableDatabase
        val columns = arrayOf("id")
        val selection = "email = ? AND mot_de_passe = ?"
        val selectionArgs = arrayOf(email, password)
        val cursor: Cursor = db.query("utilisateur", columns, selection, selectionArgs, null, null, null)
        val isValid = cursor.count > 0
        cursor.close()
        return isValid
    }

    // Database Helper class
    inner class DatabaseHelper(context: Context) :
        SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

        override fun onCreate(db: SQLiteDatabase) {
            val createTableQuery = ("CREATE TABLE utilisateur ("
                    + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + "nom VARCHAR(30) NOT NULL,"
                    + "email VARCHAR(50) NOT NULL,"
                    + "mot_de_passe VARCHAR(30) NOT NULL)")
            db.execSQL(createTableQuery)
        }

        override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
            db.execSQL("DROP TABLE IF EXISTS utilisateur")
            onCreate(db)
        }
    }

    companion object {
        // Constantes pour la base de données
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "UtilisateurDB"
    }
}
