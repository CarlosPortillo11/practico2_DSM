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
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import kotlin.math.roundToInt

class Ejercicio2 : AppCompatActivity() {

    private lateinit var dbRef : DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ejercicio2)

        dbRef = FirebaseDatabase.getInstance().getReference("Empleados")
        val btnSalario = findViewById<Button>(R.id.btnSalario)

        btnSalario.setOnClickListener{
            GuardarEmpleado()
        }
    }

    private fun GuardarEmpleado() {
        val nombre = findViewById<EditText>(R.id.txtNombre)
        val salarioBase = findViewById<EditText>(R.id.txtSalarioBase)

        val empNombre = nombre.text.toString()
        val empSalarioNeto = salarioBase.text.toString()

        val empId = dbRef.push().key!!
        val empleado = Eje2EmpleadosModel(empId, empNombre, empSalarioNeto)


        dbRef.child(empId).setValue(empleado)
            .addOnCompleteListener{
                Toast.makeText(this, "Los datos se insertaron", Toast.LENGTH_SHORT).show()
                nombre.setText("")
                salarioBase.setText("")
            }
            .addOnFailureListener{ error ->
                Toast.makeText(this, "Error: ${error.message}", Toast.LENGTH_SHORT).show()
            }

    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.Home -> startActivity(Intent(this, MainActivity::class.java))
            R.id.ejercicio1 -> startActivity(Intent(this, Ejercicio1::class.java))
            R.id.ejercicio2 -> Toast.makeText(this,"Ejercicio 2", Toast.LENGTH_SHORT).show()
            R.id.ejercicio3 -> startActivity(Intent(this, Ejercicio3::class.java))

        }
        return super.onOptionsItemSelected(item)
    }
}



// COPIA DE CODIGO
/** val nombre = findViewById<EditText>(R.id.txtNombre)
val salarioBase = findViewById<EditText>(R.id.txtSalarioBase)
val btnSalario = findViewById<Button>(R.id.btnSalario)
val Resultadosalario  = findViewById<TextView>(R.id.txtResultadoSalario)
val ViewISSS  = findViewById<TextView>(R.id.txtISSS)
val ViewAFP  = findViewById<TextView>(R.id.txtAFP)
val ViewRENTA  = findViewById<TextView>(R.id.txtRENTA)

btnSalario.setOnClickListener{
    val NewSalarioBase = salarioBase.text.toString().toDouble()
    val ISSS = NewSalarioBase - (NewSalarioBase * 0.97)
    val AFP =  NewSalarioBase - (NewSalarioBase * 0.96)
    val RENTA = NewSalarioBase - (NewSalarioBase * 0.95)
    val salarioNeto = NewSalarioBase - ISSS - AFP - RENTA

    Resultadosalario.setText(
        "El salario neto de " + nombre.text.toString() + " es: $" + ((salarioNeto * 100.0).roundToInt() / 100.0)
    )
    ViewISSS.setText(
        "Reducción de ISSS es: $" + ((ISSS * 100.0).roundToInt() / 100.0)
    )
    ViewAFP.setText(
        "Reducción de AFP es: $" + ((AFP * 100.0).roundToInt() / 100.0)
    )
    ViewRENTA.setText(
        "Reducción de RENTA es: $" + ((RENTA * 100.0).roundToInt() / 100.0)
    )
}**/