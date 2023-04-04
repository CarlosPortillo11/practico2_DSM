package com.example.practico1_dsm

import android.app.Application
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewParent
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import kotlin.math.roundToInt
import com.google.firebase.database.*
import com.example.practico1_dsm.datos.Estudiantes

class Ejercicio1 : AppCompatActivity() {

    var consultaOrdenada:Query = refEstudiantes.orderByChild("nombre")
    var estudiantesLista:MutableList<Estudiantes>? = null
    var listaEstudiantes:ListView? = null

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
        val btn_borrar = findViewById<Button>(R.id.eliminarBtn)
        val nota = findViewById<TextView>(R.id.txtPromedio)
        val aprobacion = findViewById<TextView>(R.id.txtAprobacion)
        val estudiantesSpinner = findViewById<Spinner>(R.id.estudiantesSpinner);

        inicializar();

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
            guardar()
        }

        btn_borrar.setOnClickListener {
            var database:FirebaseDatabase = FirebaseDatabase.getInstance()
            var refEstudiantes:DatabaseReference = database.getReference("estudiantes")

            val nombreABorrar = findViewById<EditText>(R.id.txtNombre).text.toString()
            if(nombreABorrar != null){
                refEstudiantes.child(nombreABorrar).removeValue();
                Toast.makeText(applicationContext, "Registro eliminado correctamente", Toast.LENGTH_LONG);
            }else{
                Toast.makeText(applicationContext, "Antes de eliminar, seleccione un registro", Toast.LENGTH_LONG);
            }
        }

        estudiantesSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(adapterView: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val estudianteSeleccionado = adapterView?.getItemAtPosition(position).toString();
                val nombre = findViewById<EditText>(R.id.txtNombre)
                val n1 = findViewById<EditText>(R.id.txtNota1)
                val n2 = findViewById<EditText>(R.id.txtNota2)
                val n3 = findViewById<EditText>(R.id.txtNota3)
                val n4 = findViewById<EditText>(R.id.txtNota4)
                val n5 = findViewById<EditText>(R.id.txtNota5)
                val nota = findViewById<TextView>(R.id.txtPromedio)
                val aprobacion = findViewById<TextView>(R.id.txtAprobacion)

                var database:FirebaseDatabase = FirebaseDatabase.getInstance()
                var refEstudiantes:DatabaseReference = database.getReference("estudiantes")

                refEstudiantes.child(estudianteSeleccionado).get().addOnSuccessListener {
                    var nombreTemp = it.child("nombre").value;
                    var nota1Temp = it.child("nota1").value;
                    var nota2Temp = it.child("nota2").value;
                    var nota3Temp = it.child("nota3").value;
                    var nota4Temp = it.child("nota4").value;
                    var nota5Temp = it.child("nota5").value;
                    var notaTemp = it.child("nota").value;
                    var aprobacionTemp = it.child("aprobacion").value

                    nombre.setText(nombreTemp.toString())
                    n1.setText(nota1Temp.toString())
                    n2.setText(nota2Temp.toString())
                    n3.setText(nota3Temp.toString())
                    n4.setText(nota4Temp.toString())
                    n5.setText(nota5Temp.toString())
                    nota.text = notaTemp.toString()
                    aprobacion.text = aprobacionTemp.toString();
                }.addOnFailureListener{
                    Log.e("firebase", "Error getting data", it)
                }

            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }

    }

    fun guardar(){
        var database:FirebaseDatabase = FirebaseDatabase.getInstance()
        var refEstudiantes:DatabaseReference = database.getReference("estudiantes")

        val nombre = findViewById<EditText>(R.id.txtNombre).text.toString()
        val n1 = findViewById<EditText>(R.id.txtNota1).text.toString().toDouble()
        val n2 = findViewById<EditText>(R.id.txtNota2).text.toString().toDouble()
        val n3 = findViewById<EditText>(R.id.txtNota3).text.toString().toDouble()
        val n4 = findViewById<EditText>(R.id.txtNota4).text.toString().toDouble()
        val n5 = findViewById<EditText>(R.id.txtNota5).text.toString().toDouble()
        val nota = findViewById<TextView>(R.id.txtPromedio).text.toString()
        val aprobacion = findViewById<TextView>(R.id.txtAprobacion).text.toString()

        val estudiante = Estudiantes(nombre, n1, n2, n3, n4, n5, nota, aprobacion)

        refEstudiantes.child(nombre).get().addOnSuccessListener{
            if(it.value != null){
                val key = refEstudiantes.child("nombre").push().key
                if(key == null){
                    Toast.makeText(applicationContext, "Llave vacía", Toast.LENGTH_SHORT).show()
                }
                val estudiantesValues = estudiante.toMap()
                val childUpdates = hashMapOf<String, Any>(
                    "$nombre" to estudiantesValues
                )
                refEstudiantes.updateChildren(childUpdates)
                Toast.makeText(applicationContext, "Registro actualizado correctamente", Toast.LENGTH_LONG).show()
            }
            else{
                Toast.makeText(applicationContext, "No conseguimos nada", Toast.LENGTH_LONG).show()
                refEstudiantes.child(nombre).setValue(estudiante).addOnSuccessListener {
                    Toast.makeText(this, "El dato se guardó con exito", Toast.LENGTH_LONG).show()
                }.addOnFailureListener{
                    Toast.makeText(this, "Dato no almacenado, corrija e intente de nuevo", Toast.LENGTH_LONG).show()
                }
            }
        }.addOnFailureListener{
            val temp = it
            Toast.makeText(applicationContext, "No conseguimos nada", Toast.LENGTH_LONG).show()
            Toast.makeText(applicationContext, "Es: "+it, Toast.LENGTH_LONG).show()
        }
    }

    private fun inicializar(){

        estudiantesLista = ArrayList<Estudiantes>()
        val estudiantesSpinner = findViewById<Spinner>(R.id.estudiantesSpinner);
        var estudiantesArray = ArrayList<String>()

        consultaOrdenada.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                estudiantesLista!!.removeAll(estudiantesLista!!)
                for (dato in snapshot.children){
                    val estudiante :Estudiantes? = dato.getValue(Estudiantes::class.java)
                    estudiante?.key(dato.key)
                    if(estudiante != null){
                        estudiantesLista!!.add(estudiante)
                    }
                }
                estudiantesArray!!.removeAll(estudiantesArray!!.toSet())
                for(eS in estudiantesLista as ArrayList<Estudiantes>){
                    eS.nombre?.let { estudiantesArray.add(it) };
                }
                val dataAdapter = ArrayAdapter<String>(applicationContext, android.R.layout.simple_spinner_item, estudiantesArray)
                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                estudiantesSpinner.adapter = dataAdapter;
            }
            override fun onCancelled(error: DatabaseError) {
            }
        })
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

    companion object{
        var database:FirebaseDatabase = FirebaseDatabase.getInstance()
        var refEstudiantes:DatabaseReference = database.getReference("estudiantes")
    }
}