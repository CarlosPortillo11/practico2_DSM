package com.example.practico1_dsm.datos

class Empleados {
    fun key(key: String?){
    }

    var nombre: String? = null
    var SalarioNeto: Double? = null
    var key: String? = null
    var est: MutableMap<String, Boolean> = HashMap()

    constructor(){}
    constructor(nombre: String?,salarioneto: Double?){
        this.nombre = nombre;
        this.SalarioNeto = salarioneto;
    }

    fun toMap(): Map<String, Any?>{
        return mapOf(
            "nombre" to nombre,
            "salarioneto" to SalarioNeto,
            "key" to key,
            "est" to est
        )
    }
}