package com.example.practico1_dsm.datos

class Estudiantes {
    fun key(key: String?){
    }

    var nombre: String? = null
    var nota1: Double? = null
    var nota2: Double? = null
    var nota3: Double? = null
    var nota4: Double? = null
    var nota5: Double? = null
    var nota: String? = null
    var aprobacion: String? = null
    var key: String? = null
    var est: MutableMap<String, Boolean> = HashMap()

    constructor(){}
    constructor(nombre: String?, nota1: Double?, nota2: Double?, nota3: Double?, nota4: Double?, nota5: Double?, nota:String?, aprobacion: String?){
        this.nombre = nombre;
        this.nota1 = nota1;
        this.nota2 = nota2;
        this.nota3 = nota3;
        this.nota4 = nota4;
        this.nota5 = nota5;
        this.nota = nota
        this.aprobacion = aprobacion
    }

    fun toMap(): Map<String, Any?>{
        return mapOf(
            "nombre" to nombre,
            "nota1" to nota1,
            "nota2" to nota2,
            "nota3" to nota3,
            "nota4" to nota4,
            "nota5" to nota5,
            "nota" to nota,
            "aprobacion" to aprobacion,
            "key" to key,
            "est" to est
        )
    }
}