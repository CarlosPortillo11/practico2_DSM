package com.example.practico1_dsm

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast

class Ejercicio3 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ejercicio3)
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