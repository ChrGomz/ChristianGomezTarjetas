package com.actividad.tarjetas.objetosPersonalizados

import com.actividad.tarjetas.R

object ListaPersonas {
    var lista: ArrayList<Persona> = ArrayList()

    init {
        fillListaPersonas()
    }

    private fun fillListaPersonas() {
        agregarPersona(Persona(idPersona = 1, idImagen = R.drawable.paco, nombre = "Juan", edad = 25, telefono = "123456789", titulacion = "Ingeniero", experiencia = "Ninguna"))
        agregarPersona(Persona(idPersona = 2, idImagen = R.drawable.paco, nombre = "María", edad = 30, telefono = "987654321", titulacion = "Médico", experiencia = "6"))
        agregarPersona(Persona(idPersona = 3, idImagen = R.drawable.paco, nombre = "Carlos", edad = 28, telefono = "5551234567", titulacion = "Abogado", experiencia = "10"))
        agregarPersona(Persona(idPersona = 4, idImagen = R.drawable.paco, nombre = "Laura", edad = 22, telefono = "111222333", titulacion = "Diseñador", experiencia = "Ninguna"))
        agregarPersona(Persona(idPersona = 5, idImagen = R.drawable.paco, nombre = "Alejandro", edad = 35, telefono = "444555666", titulacion = "Arquitecto", experiencia = "1"))
        agregarPersona(Persona(idPersona = 6, idImagen = R.drawable.paco, nombre = "Sofía", edad = 27, telefono = "777888999", titulacion = "Contador", experiencia = "1"))
        agregarPersona(Persona(idPersona = 7, idImagen = R.drawable.paco, nombre = "Luis", edad = 31, telefono = "999000111", titulacion = "Chef", experiencia = "8"))
        agregarPersona(Persona(idPersona = 8, idImagen = R.drawable.paco, nombre = "Ana", edad = 29, telefono = "222333444", titulacion = "Psicólogo", experiencia = "4"))
        agregarPersona(Persona(idPersona = 9, idImagen = R.drawable.paco, nombre = "Roberto", edad = 26, telefono = "666777888", titulacion = "Profesor", experiencia = "2"))
        agregarPersona(Persona(idPersona = 10,idImagen = R.drawable.paco, nombre = "Elena", edad = 33, telefono = "333444555", titulacion = "Enfermera", experiencia = "2"))
    }


    fun agregarPersona(persona: Persona) {
        lista.add(persona)
    }


}

fun obtenerTitulacionesUnicas(): List<String> {
    return ListaPersonas.lista.map { it.titulacion }.distinct()
}

object ListaIdBorrar {
    var lista: ArrayList<Persona> = ArrayList()
}

object ListaTitulacionesFiltrada {
    var lista: ArrayList<String> = ArrayList()
}

object consultaGlobal {
    var texto = ""
}