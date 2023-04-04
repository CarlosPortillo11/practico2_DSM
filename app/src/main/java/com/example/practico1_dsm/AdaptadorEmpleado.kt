package com.example.practico1_dsm
import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import  com.example.practico1_dsm.datos.Empleados


    class AdaptadorEmpleado(private val context: Activity, var empleados: List<Empleados>) :
        ArrayAdapter<Empleados?>(context, R.layout.empleados_layout, empleados) {
        override fun getView(position: Int, view: View?, parent: ViewGroup): View {

            val layoutInflater = context.layoutInflater
            var rowview: View? = null

            rowview = view ?: layoutInflater.inflate(R.layout.empleados_layout, null)
            val tvSalarioNeto = rowview!!.findViewById<TextView>(R.id.tvSalarioNeto)
            val tvNombre = rowview.findViewById<TextView>(R.id.tvNombre)
            tvNombre.text = "Nombre : " + empleados[position].nombre
            tvSalarioNeto.text = "Salario Neto : " + empleados[position].SalarioNeto
            return rowview
        }
    }
