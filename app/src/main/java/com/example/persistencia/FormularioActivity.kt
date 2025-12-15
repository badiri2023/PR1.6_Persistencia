package com.example.persistencia

import android.content.Context
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.io.OutputStreamWriter

class FormularioActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_formulario) // Asegúrate que el XML se llame así

        val etNom = findViewById<EditText>(R.id.etNom)
        val etCognoms = findViewById<EditText>(R.id.etCognoms)
        val etTelefon = findViewById<EditText>(R.id.etTelefon)
        val etEmail = findViewById<EditText>(R.id.etEmail)
        val btnGuardar = findViewById<Button>(R.id.btnGuardar)

        btnGuardar.setOnClickListener {
            val nom = etNom.text.toString()
            val cognoms = etCognoms.text.toString()
            val telefon = etTelefon.text.toString()
            val email = etEmail.text.toString()

            if (nom.isNotEmpty() && cognoms.isNotEmpty()) {
                val dades = "$nom;$cognoms;$telefon;$email\n"

                try {
                    val out = openFileOutput("contactes.txt", Context.MODE_APPEND)
                    val writer = OutputStreamWriter(out)
                    writer.write(dades)
                    writer.close()

                    Toast.makeText(this, "Guardat!", Toast.LENGTH_SHORT).show()

                    // IMPORTANTE: Cerramos esta pantalla para volver a la Lista
                    finish()

                } catch (e: Exception) {
                    Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Falten dades", Toast.LENGTH_SHORT).show()
            }
        }
    }
}