package com.example.persistencia

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import java.io.BufferedReader
import java.io.InputStreamReader

class MainActivity : AppCompatActivity() {

    lateinit var listView: ListView

    // Lista para mostrar en la UI (Nombre y Apellido)
    val contactesDisplayList = ArrayList<String>()

    // Lista para almacenar la línea de datos completa (Nom;Cognom;Tel;Email)
    val contactesDataList = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        listView = findViewById(R.id.lvContactes)
        val btnNou = findViewById<Button>(R.id.btnNou)

        // NOTA: La lógica del botón 'btnEditar' se ha ELIMINADO de aquí,
        // ya que el clic en la lista lo reemplaza.

        // 1. Botón NUEVO: Abre la actividad del formulario
        btnNou.setOnClickListener {
            val intent = Intent(this, FormularioActivity::class.java)
            startActivity(intent)
        }

        // 2. Detectar clic en la lista para abrir la vista de detalle/edición
        listView.setOnItemClickListener { _, _, position, _ ->
            // 1. Recuperamos la línea de datos COMPLETA del contacto
            val dadesCompletes = contactesDataList[position]

            // 2. Abrimos la Activity de Detalle/Edición
            val intent = Intent(this, DetalleContactoActivity::class.java)

            // 3. Pasamos los datos completos y el índice (necesario para saber qué línea editar/borrar)
            intent.putExtra("CONTACTE_DATA", dadesCompletes)
            intent.putExtra("CONTACTE_INDEX", position)

            startActivity(intent)
        }
    }

    // Usamos onResume para recargar la lista cada vez que aparezca la pantalla
    override fun onResume() {
        super.onResume()
        carregarLlista()
    }

    private fun carregarLlista() {
        // Limpiamos las dos listas antes de volver a cargarlas
        contactesDisplayList.clear()
        contactesDataList.clear()

        try {
            val fileInputStream = openFileInput("contactes.txt")
            val reader = BufferedReader(InputStreamReader(fileInputStream))

            var linia = reader.readLine()
            while (linia != null) {
                val cleanLinia = linia.trim()

                // 1. Guardamos la línea de datos COMPLETA
                contactesDataList.add(cleanLinia)

                // 2. Preparamos el string para la visualización
                val parts = cleanLinia.split(";")
                if(parts.size >= 2) {
                    contactesDisplayList.add("${parts[0]} ${parts[1]}")
                } else {
                    contactesDisplayList.add("Contacte Invàlid")
                }
                linia = reader.readLine()
            }
            reader.close()
        } catch (e: Exception) {
            // El archivo no existe o está vacío. La lista se queda limpia.
        }

        // Asignamos el adaptador con la lista para MOSTRAR
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, contactesDisplayList)
        listView.adapter = adapter
    }
}