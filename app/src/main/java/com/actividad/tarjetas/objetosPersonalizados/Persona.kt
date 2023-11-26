package com.actividad.tarjetas.objetosPersonalizados

data class Persona(
    val idPersona: Int,
    val idImagen: Int,
    val nombre: String,
    val edad: Int,
    val telefono: String,
    val titulacion: String,
    val experiencia: String,
    var seleccionado: Boolean = false
)
