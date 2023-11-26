package com.example.juegopreguntas.Rutas

sealed class Rutas (val ruta: String){
    object PantallaInicio: Rutas("inicio")
    object PantallaFormulario: Rutas("cuertionario")
}