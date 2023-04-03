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
import java.math.RoundingMode
import java.text.DecimalFormat

class Ejercicio3 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ejercicio3)

        val num1 = findViewById<EditText>(R.id.Number1Box);
        val num2 = findViewById<EditText>(R.id.Number2Box);
        val btnSuma = findViewById<Button>(R.id.btnSuma);
        val btnResta = findViewById<Button>(R.id.btnResta);
        val btnMultiplicacion = findViewById<Button>(R.id.btnMultiplicacion);
        val btnDivision = findViewById<Button>(R.id.btnDivision);
        val btnIgual = findViewById<Button>(R.id.btnIgual);
        val btnLimpiar = findViewById<Button>(R.id.btnLimpiar);
        val resultBox = findViewById<TextView>(R.id.resultBox);
        val dFormat = DecimalFormat("#.##");
        dFormat.roundingMode = RoundingMode.CEILING;

        num1.setOnClickListener{
            num1.hint = "";
        }
        num2.setOnClickListener{
            num2.hint = "";
        }

        btnSuma.setOnClickListener {
            if(num1.text.toString() == "" || num2.text.toString() == ""){
                Toast.makeText(applicationContext, "Ingrese ambos valores antes de querer operar", Toast.LENGTH_LONG).show();
            }
            else{
                val resultado = num1.text.toString().toDouble() + num2.text.toString().toDouble();
                val parsedResultado = dFormat.format(resultado).toDouble();
                resultBox.text = ""+parsedResultado;
            }

        }

        btnResta.setOnClickListener {
            if(num1.text.toString() == "" || num2.text.toString() == ""){
                Toast.makeText(applicationContext, "Ingrese ambos valores antes de querer operar", Toast.LENGTH_LONG).show();
            }
            else{
                val resultado = num1.text.toString().toDouble() - num2.text.toString().toDouble();
                val parsedResultado = dFormat.format(resultado).toDouble();
                resultBox.text = ""+parsedResultado;
            }
        }

        btnMultiplicacion.setOnClickListener {
            if(num1.text.toString() == "" || num2.text.toString() == ""){
                Toast.makeText(applicationContext, "Ingrese ambos valores antes de querer operar", Toast.LENGTH_LONG).show();
            }
            else{
                val resultado = num1.text.toString().toDouble() * num2.text.toString().toDouble();
                val parsedResultado = dFormat.format(resultado).toDouble();
                resultBox.text = ""+parsedResultado;
            }
        }

        btnDivision.setOnClickListener {
            if(num1.text.toString() == "" || num2.text.toString() == ""){
                Toast.makeText(applicationContext, "Ingrese ambos valores antes de querer operar", Toast.LENGTH_LONG).show();
            }
            else{
                val resultado = (num1.text.toString().toDouble())/(num2.text.toString().toDouble());
                val parsedResultado = dFormat.format(resultado).toDouble();
                resultBox.text = ""+parsedResultado;
            }
        }

        btnLimpiar.setOnClickListener {
            num1.text.clear();
            num2.text.clear();
            resultBox.text = ""+0;
        }

        btnIgual.setOnClickListener {
            val toast = Toast.makeText(applicationContext, "No hago nada, pero necesitaba rellenar", Toast.LENGTH_LONG);
            toast.show();
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
            R.id.ejercicio2 -> startActivity(Intent(this, Ejercicio2::class.java))
            R.id.ejercicio3 -> Toast.makeText(this,"Ejercicio 3", Toast.LENGTH_SHORT).show()
        }
        return super.onOptionsItemSelected(item)
    }
}