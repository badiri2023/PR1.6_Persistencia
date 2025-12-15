package com.example.persistencia

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.io.BufferedReader
import java.io.InputStreamReader

class MainActivity : AppCompatActivity() {

    lateinit var listView: ListView
    lateinit var btnEditar: Button
    // Guardamos la lista en memoria para saber qué ha pulsado el usuario
    val contactesList = ArrayList<String>()
    var itemSeleccionado: Int = -1 // -1 significa que no hay nada seleccionado

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        listView = findViewById(R.id.lvContactes)
        val btnNou = findViewById<Button>(R.id.btnNou)
        btnEditar = findViewById(R.id.btnEditar)

        // 1. Botón NUEVO: Abre la actividad del formulario
        btnNou.setOnClickListener {
            val intent = Intent(this, FormularioActivity::class.java)
            startActivity(intent)
        }

        // 2. Botón EDITAR: (Por ahora solo avisa)
        btnEditar.setOnClickListener {
            if (itemSeleccionado != -1) {
                Toast.makeText(this, "Funcionalitat d'Editar pendent", Toast.LENGTH_SHORT).show()
                // Aquí iría la lógica para abrir el formulario pasando los datos
            }
        }

        // 3. Detectar clic en la lista para habilitar el botón Editar
        listView.setOnItemClickListener { _, _, position, _ ->
            itemSeleccionado = position
            btnEditar.isEnabled = true
            btnEditar.text = "EDITAR SELECCIÓ"
        }
    }

    // Usamos onResume para recargar la lista cada vez que aparezca la pantalla
    override fun onResume() {
        super.onResume()
        carregarLlista()
    }

    private fun carregarLlista() {
        contactesList.clear() // Limpiamos la lista anterior
        try {
            val fileInputStream = openFileInput("contactes.txt")
            val reader = BufferedReader(InputStreamReader(fileInputStream))

            var linia = reader.readLine()
            while (linia != null) {
                // La línea es: Nom;Cognom;Tel;Email
                // Lo mostramos bonito en la lista reemplazando ; por espacios
                val parts = linia.split(";")
                if(parts.size >= 2) {
                    contactesList.add("${parts[0]} ${parts[1]}") // Solo mostramos nombre y apellido
                }
                linia = reader.readLine()
            }
            reader.close()
        } catch (e: Exception) {
            // Si es la primera vez y no existe el archivo, no pasa nada
        }

        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, contactesList)
        listView.adapter = adapter
    }
}