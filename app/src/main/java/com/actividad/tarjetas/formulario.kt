package com.actividad.tarjetas

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.actividad.tarjetas.objetosPersonalizados.ListaPersonas
import com.actividad.tarjetas.objetosPersonalizados.Persona
import com.actividad.tarjetas.objetosPersonalizados.obtenerTitulacionesUnicas
import com.example.juegopreguntas.Rutas.Rutas

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaFormulario(navController: NavHostController?) {
    var persona by remember {
        mutableStateOf(
            Persona(
                idPersona = 0,
                idImagen = R.drawable.paco,
                nombre = "",
                edad = 0,
                telefono = "",
                titulacion = "",
                experiencia = "",
                seleccionado = false
            )
        )
    }
    var enviado by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (!enviado) {
            CamposFormulario(persona) {
                persona = it
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    ListaPersonas.agregarPersona(persona)
                    enviado = true
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Enviar")
            }
        } else {
            Column {
                Text(
                    text = "Se ha agregado la nueva persona con éxito",
                    style = MaterialTheme.typography.headlineMedium,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
                Button(
                    onClick = {
                        if (navController != null) {
                            navController.navigate(Rutas.PantallaInicio.ruta)
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = "Volver al inicio")
                }
            }
        }
    }
}

@ExperimentalMaterial3Api
@Composable
fun CamposFormulario(persona: Persona, onPersonaChange: (Persona) -> Unit) {
    OutlinedTextField(
        value = persona.nombre,
        onValueChange = { onPersonaChange(persona.copy(nombre = it)) },
        label = { Text(text = "Nombre") },
        modifier = Modifier.fillMaxWidth()
    )

    Spacer(modifier = Modifier.height(8.dp))

    OutlinedTextField(
        value = persona.edad.toString(),
        onValueChange = {
            onPersonaChange(persona.copy(edad = it.toIntOrNull() ?: 0))
        },
        label = { Text(text = "Edad") },
        modifier = Modifier.fillMaxWidth(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
    )

    Spacer(modifier = Modifier.height(8.dp))

    OutlinedTextField(
        value = persona.telefono,
        onValueChange = { onPersonaChange(persona.copy(telefono = it)) },
        label = { Text(text = "Teléfono") },
        modifier = Modifier.fillMaxWidth(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone)
    )

    Spacer(modifier = Modifier.height(8.dp))

    OutlinedTextField(
        value = persona.titulacion,
        onValueChange = { onPersonaChange(persona.copy(titulacion = it)) },
        label = { Text(text = "Titulación") },
        modifier = Modifier.fillMaxWidth(),
        readOnly = true,
        trailingIcon = {
            Icon(
                imageVector = Icons.Default.ArrowDropDown,
                contentDescription = "Desplegar opciones"
            )
        }
    )

    SliderExperiencia {
        onPersonaChange(persona.copy(experiencia = it))
    }

    Spacer(modifier = Modifier.height(8.dp))

    var expandidoDDmenu by remember {
        mutableStateOf(false)
    }

    DropdownMenu(
        expanded = expandidoDDmenu,
        onDismissRequest = { expandidoDDmenu = true },
        modifier = Modifier.fillMaxWidth()
    ) {
        obtenerTitulacionesUnicas().forEach { opcion ->
            DropdownMenuItem(text = { Text(text = opcion) },
                onClick = {
                    onPersonaChange(persona.copy(titulacion = opcion))
                }
            )
        }
    }

}


@Composable
fun SliderExperiencia(funOnValueChange: (String) -> Unit) {
    var sliderPosition by remember { mutableFloatStateOf(0f) }
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = "Años de experiencia")
        Slider(
            value = sliderPosition,
            onValueChange = {
                sliderPosition = it
                if (sliderPosition.toInt() == 0) {
                    funOnValueChange(sliderPosition.toInt().toString())
                } else if (sliderPosition.toInt() > 9) {
                    funOnValueChange("10 o mas años")
                } else if (sliderPosition.toInt() == 1) {
                    funOnValueChange("$sliderPosition año")
                } else {
                    funOnValueChange("$sliderPosition años")
                }
            },
            colors = SliderDefaults.colors(
                thumbColor = MaterialTheme.colorScheme.secondary,
                activeTrackColor = MaterialTheme.colorScheme.secondary,
                inactiveTrackColor = MaterialTheme.colorScheme.secondaryContainer,
            ),
            steps = 9,
            valueRange = 0f..10f,
            modifier = Modifier.padding(30.dp, 0.dp, 30.dp, 0.dp)
        )
        if (sliderPosition.toInt() == 0) {
            Text(text = "Ninguna")
        } else if (sliderPosition.toInt() > 9) {
            Text(text = "10 o mas años")
        } else if (sliderPosition.toInt() == 1) {
            Text(text = "1 año")
        } else {
            Text(text = "${sliderPosition.toInt()} años")
        }
    }
}