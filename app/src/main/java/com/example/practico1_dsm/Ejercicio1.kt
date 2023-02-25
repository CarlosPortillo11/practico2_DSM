package com.example.practico1_dsm

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import kotlin.math.roundToInt

class Ejercicio1 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ejercicio1)

        val nombre = findViewById<EditText>(R.id.txtNombre)
        val n1 = findViewById<EditText>(R.id.txtNota1)
        val n2 = findViewById<EditText>(R.id.txtNota2)
        val n3 = findViewById<EditText>(R.id.txtNota3)
        val n4 = findViewById<EditText>(R.id.txtNota4)
        val n5 = findViewById<EditText>(R.id.txtNota5)
        val btn_promedio = findViewById<Button>(R.id.btnPromedio)
        val nota = findViewById<TextView>(R.id.txtPromedio)
        val aprobacion = findViewById<TextView>(R.id.txtAprobacion)

        btn_promedio.setOnClickListener{
            val promedio = (n1.text.toString().toDouble() + n2.text.toString().toDouble() + n3.text.toString().toDouble() + n4.text.toString().toDouble() + n5.text.toString().toDouble())/5
            nota.setText(
                "El promedio de " + nombre.text.toString() + " es: " + ((promedio * 100.0).roundToInt() / 100.0)
            )
            if (promedio.roundToInt() >= 6.0){
                aprobacion.setText("Aprobó")
            } else{
                aprobacion.setText("Reprobó")
            }

        }
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.Home -> startActivity(Intent(this, MainActivity::class.java))
            R.id.ejercicio1 -> Toast.makeText(this,"Ejercicio 1", Toast.LENGTH_SHORT).show()
            R.id.ejercicio2 -> startActivity(Intent(this, Ejercicio2::class.java))
            R.id.ejercicio3 -> startActivity(Intent(this, Ejercicio3::class.java))
        }
        return super.onOptionsItemSelected(item)
    }
}