package com.actividad.tarjetas.metodos

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.actividad.tarjetas.PantallaFormulario
import com.actividad.tarjetas.PantallaInicio
import com.example.juegopreguntas.Rutas.Rutas

class MetodosUtiles {
    companion object {

        @Composable
        fun GraphNavegacion() {
            val navController = rememberNavController()

            NavHost(navController = navController, startDestination = Rutas.PantallaInicio.ruta) {
                composable(Rutas.PantallaInicio.ruta) {
                    PantallaInicio(navController = navController)
                }

                composable(Rutas.PantallaFormulario.ruta) {
                    PantallaFormulario(navController = navController)
                }
            }
        }
    }
}