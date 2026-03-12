package com.example.persistencia

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class DetalleContactoActivity : AppCompatActivity() {

    private lateinit var etNom: EditText
    private lateinit var etCognoms: EditText
    private lateinit var etTelefon: EditText
    private lateinit var etEmail: EditText
    private lateinit var btnAccioPrincipal: Button

    // Estado de la vista: true = Editando, false = Viendo
    private var isEditing = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalle_contacto)

        // Inicializar referencias
        val btnClose = findViewById<ImageButton>(R.id.btnClose)
        val btnEliminar = findViewById<Button>(R.id.btnEliminar)
        btnAccioPrincipal = findViewById(R.id.btnAccioPrincipal)

        etNom = findViewById(R.id.etDetallNom)
        etCognoms = findViewById(R.id.etDetallCognoms)
        etTelefon = findViewById(R.id.etDetallTelefon)
        etEmail = findViewById(R.id.etDetallEmail)

        // 1. Inicializar la vista en modo NO EDITABLE
        setEditMode(false)

        // 2. Simular carga de datos (PUNTUAL: Aquí cargarías los datos reales del Intent)
        simularCargaDeDatos()


        // 3. Listener para cerrar
        btnClose.setOnClickListener {
            finish()
        }

        // 4. Listener para el botón principal (EDITAR/GUARDAR)
        btnAccioPrincipal.setOnClickListener {
            if (isEditing) {
                // Estamos en modo Edición -> Pulsamos GUARDAR
                guardarCambios()
                setEditMode(false) // Volver a modo Visualización
            } else {
                // Estamos en modo Visualización -> Pulsamos EDITAR
                setEditMode(true) // Cambiar a modo Edición
            }
        }

        // 5. Listener para Eliminar
        btnEliminar.setOnClickListener {
            Toast.makeText(this, "Eliminar Contacte Pendent...", Toast.LENGTH_SHORT).show()
        }
    }

    /**
     * Alterna el estado de los campos de texto.
     */
    private fun setEditMode(enable: Boolean) {
        isEditing = enable
        val textFields = listOf(etNom, etCognoms, etTelefon, etEmail)

        for (et in textFields) {
            // Habilita/Deshabilita la edición del campo
            et.isFocusable = enable
            et.isFocusableInTouchMode = enable
            et.isCursorVisible = enable

            // Si está desactivado (modo visualización), quita el foco
            if (!enable) {
                et.clearFocus()
            }
        }

        // Cambiar texto y estilo del botón principal
        if (enable) {
            btnAccioPrincipal.text = "GUARDAR CANVIS"
            Toast.makeText(this, "Mode Edició Habilitat", Toast.LENGTH_SHORT).show()
            etNom.requestFocus() // Poner el cursor en el primer campo
        } else {
            btnAccioPrincipal.text = "EDITAR"
        }
    }

    /**
     * Simula la carga inicial de datos (que vendrían del Intent)
     */
    private fun simularCargaDeDatos() {
        // En un caso real, leerías los datos del archivo usando la posición/ID del Intent.
        // Por ahora, solo precargamos datos ficticios para ver el diseño
        etNom.setText("Martí")
        etCognoms.setText("García Segarra")
        etTelefon.setText("666111222")
        etEmail.setText("marti.garcia@gmail.com")
    }

    /**
     * Lógica para guardar los cambios (pendiente de implementación real con el archivo)
     */
    private fun guardarCambios() {
        val nuevoNombre = etNom.text.toString()
        val nuevosDatos = "$nuevoNombre;${etCognoms.text};${etTelefon.text};${etEmail.text}\n"

        Toast.makeText(this, "Guardant $nuevoNombre... (Pendent reescriure fitxer)", Toast.LENGTH_LONG).show()
        // Aquí iría el código para reescribir el archivo completo, reemplazando la línea antigua por `nuevosDatos`
    }
}