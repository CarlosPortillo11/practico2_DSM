package com.example.practico1_dsm

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import kotlin.math.roundToInt
import com.google.firebase.database.*
import com.example.practico1_dsm.datos.Empleados


class Ejercicio2 : AppCompatActivity() {

    var consultaOrdenada:Query = refEmpleados.orderByChild("empNombre")
    var EmpleadosLista:MutableList<Empleados>? = null
    var listaEmpleados: ListView? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ejercicio2)

        val listaEmpleados = findViewById<ListView>(R.id.ListaEmpleados)
        val btnSalario = findViewById<Button>(R.id.btnSalario)

       inicializar();

        btnSalario.setOnClickListener{

            guardar();
        }


    }


    private fun guardar() {
        var database:FirebaseDatabase = FirebaseDatabase.getInstance()
        var refEmpleados:DatabaseReference = database.getReference("Empleados")

        val nombre = findViewById<EditText>(R.id.txtNombre).text.toString()
        val salarioBase = findViewById<EditText>(R.id.txtSalarioBase).text.toString().toDouble()

        val ISSS = salarioBase - (salarioBase * 0.97)
        val AFP =  salarioBase - (salarioBase * 0.96)
        val RENTA = salarioBase - (salarioBase * 0.95)
        val salarioNeto = salarioBase - ISSS - AFP - RENTA

        val empleado = Empleados(nombre,salarioNeto)

        refEmpleados.child(nombre).get().addOnSuccessListener{
            if(it.value != null){
                val key = refEmpleados.child("epmNombre").push().key
                if(key == null){
                    Toast.makeText(applicationContext, "Llave vacía", Toast.LENGTH_SHORT).show()
                }
                val empleadosValues = empleado.toMap()
                val childUpdates = hashMapOf<String, Any>(
                    "$nombre" to empleadosValues
                )
                refEmpleados.updateChildren(childUpdates)
                Toast.makeText(applicationContext, "Registro actualizado correctamente", Toast.LENGTH_LONG).show()
            }
            else{
                refEmpleados.child(nombre).setValue(empleado).addOnSuccessListener {
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
    private fun inicializar() {
        EmpleadosLista = ArrayList<Empleados>()
        val listaEmpleados = findViewById<ListView>(R.id.ListaEmpleados);
        var EmpleadosArray = ArrayList<String>()

        consultaOrdenada.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                EmpleadosLista!!.removeAll( EmpleadosLista!!)
                for (dato in snapshot.children){
                    val empleado :Empleados? = dato.getValue(Empleados::class.java)
                    empleado?.key(dato.key)
                    if(empleado != null){
                        EmpleadosLista!!.add(empleado)
                    }
                }
                val Adapter = AdaptadorEmpleado(this@Ejercicio2,EmpleadosLista as ArrayList<Empleados>)
                listaEmpleados!!.adapter = Adapter
            }
            override fun onCancelled(error: DatabaseError) {
            }
        })
        listaEmpleados!!.onItemLongClickListener = object : AdapterView.OnItemLongClickListener {
            override fun onItemLongClick(
                adapterView: AdapterView<*>?,
                view: View,
                position: Int,
                l: Long
            ): Boolean {
                
                val ad = AlertDialog.Builder(this@Ejercicio2)
                ad.setMessage("Está seguro de eliminar registro?")
                    .setTitle("Confirmación")
                ad.setPositiveButton("Si"
                ) { dialog, id ->
                    EmpleadosLista!![position].nombre?.let {
                        refEmpleados.child(it).removeValue()
                    }
                    Toast.makeText(
                        this@Ejercicio2,
                        "Registro borrado!", Toast.LENGTH_SHORT
                    ).show()
                }
                ad.setNegativeButton("No", object : DialogInterface.OnClickListener {
                    override fun onClick(dialog: DialogInterface, id: Int) {
                        Toast.makeText(
                            this@Ejercicio2,
                            "Operación de borrado cancelada!", Toast.LENGTH_SHORT
                        ).show()
                    }
                })
                ad.show()
                return true
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
            R.id.ejercicio1 -> startActivity(Intent(this, Ejercicio1::class.java))
            R.id.ejercicio2 -> Toast.makeText(this,"Ejercicio 2", Toast.LENGTH_SHORT).show()
            R.id.ejercicio3 -> startActivity(Intent(this, Ejercicio3::class.java))

        }
        return super.onOptionsItemSelected(item)
    }
    companion object{
        var database:FirebaseDatabase = FirebaseDatabase.getInstance()
        var refEmpleados:DatabaseReference = database.getReference("Empleados")
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