package com.actividad.tarjetas

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.actividad.tarjetas.objetosPersonalizados.ListaPersonas
import com.actividad.tarjetas.objetosPersonalizados.ListaTitulacionesFiltrada
import com.actividad.tarjetas.objetosPersonalizados.Persona
import com.actividad.tarjetas.objetosPersonalizados.obtenerTitulacionesUnicas
import com.actividad.tarjetas.ui.theme.Purple40
import com.example.juegopreguntas.Rutas.Rutas

@Composable
fun PantallaInicio(navController: NavHostController?) {
    val context = LocalContext.current
    var filtroTitulacion: String by remember {
        mutableStateOf("")
    }
    var consulta: String by remember {
        mutableStateOf("")
    }
    var estadoBotones: Boolean by remember {
        mutableStateOf(false)
    }
    var confirmarBorrado by remember {
        mutableStateOf(false)
    }

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        BarraBusqueda(
            context = context,
            onQueryChange = { nuevaConsulta -> consulta = nuevaConsulta },
            onSearch = {
                filtroTitulacion = consulta
            }
        )
        Spacer(modifier = Modifier.padding(4.dp))
        PersonasACards(
            personas = ListaPersonas.lista,
            filtroTitulacion = filtroTitulacion,
            estadoBotones = estadoBotones
        )
    }

    if (confirmarBorrado && !estadoBotones) {
        DialogoConfirmacionBorrado(
            onConfirmarBorrado = {
                ListaPersonas.lista.removeAll { it.seleccionado }
                confirmarBorrado = false
            },
            onCancelar = {
                confirmarBorrado = false
            }
        )
    }

    if (navController != null) {
        botonesFunciones(navController) {
            estadoBotones = !estadoBotones
            confirmarBorrado = true
        }
    }
}


@Composable
fun botonesFunciones(navController: NavHostController, onBotonBorrarClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp),
        verticalAlignment = Alignment.Bottom,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        ExtendedFloatingActionButton(
            onClick = { navController.navigate(Rutas.PantallaFormulario.ruta) },
            modifier = Modifier
        ) {
            Text(text = "Añadir")
            Icon(
                painterResource(id = R.drawable.simbolo_add),
                contentDescription = null
            )
        }
        ExtendedFloatingActionButton(onClick = {
            onBotonBorrarClick()
        }) {
            Text(text = "Borrar")
            Icon(
                imageVector = Icons.Default.Delete,
                contentDescription = "Opcion borrado"
            )
        }
    }
}

@Composable
fun DialogoConfirmacionBorrado(
    onConfirmarBorrado: () -> Unit,
    onCancelar: () -> Unit
) {
    AlertDialog(
        onDismissRequest = { onCancelar() },
        title = {
            Text("Confirmar borrado")
        },
        text = {
            Text("¿Está seguro de que desea borrar a las personas seleccionadas?")
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onConfirmarBorrado()
                }
            ) {
                Text("Confirmar")
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    onCancelar()
                }
            ) {
                Text("Cancelar")
            }
        }
    )
}

@Composable
fun TarjetaContacto(
    colorFondo: Color,
    persona: Persona,
    estadoBotones: Boolean
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            DatosPersona(persona, estadoBotones)
        }
    }
}

@Composable
fun DatosPersona(persona: Persona, estadoBotones: Boolean) {
    Row(
        modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth()
            .wrapContentHeight(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        val imagenPainter = painterResource(id = persona.idImagen)
        Image(
            painter = imagenPainter,
            contentDescription = "Imagen de ${persona.nombre}",
            modifier = Modifier
                .size(100.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.primary)
        )

        Spacer(modifier = Modifier.width(10.dp))

        Column(
            modifier = Modifier
                .weight(1f)
                .wrapContentHeight()
        ) {

            Text(
                text = persona.nombre,
                style = MaterialTheme.typography.headlineMedium
            )
            Text(
                text = "Titulación: ${persona.titulacion}",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp)
            )
            Text(
                text = "Teléfono: ${persona.telefono}",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp)
            )
            Text(
                text = "${persona.edad} años",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp)
            )
        }

        if (estadoBotones) {
            Spacer(modifier = Modifier.width(8.dp))
            var selecionado by remember {
                mutableStateOf(persona.seleccionado)
            }
            Checkbox(
                checked = selecionado,
                onCheckedChange = { isChecked ->
                    persona.seleccionado = isChecked
                    selecionado = isChecked
                },
                modifier = Modifier.padding(8.dp)
            )
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BarraBusqueda(
    context: Context,
    onQueryChange: (String) -> Unit,
    onSearch: () -> Unit
) {
    var consulta by remember {
        mutableStateOf("")
    }
    var estadoBarra by remember {
        mutableStateOf(false)
    }
    SearchBar(
        query = consulta,
        onQueryChange = {
            consulta = it
            onQueryChange(it)
        },
        onSearch = {
            Toast.makeText(context, consulta, Toast.LENGTH_LONG).show()
            onSearch()
            estadoBarra = false
        },
        active = estadoBarra,
        onActiveChange = { estadoBarra = it },
        placeholder = { Text(text = "Buscardor") },
        trailingIcon = {
            IconButton(onClick = { estadoBarra = false }) {
                Icon(
                    painter = painterResource(id = R.drawable.search),
                    contentDescription = ""
                )
            }
        }
    ) {
        LazyColumn {
            ListaTitulacionesFiltrada.lista =
                obtenerTitulacionesUnicas().filter {
                    it.startsWith(
                        consulta,
                        true
                    )
                } as ArrayList<String>
            items(ListaTitulacionesFiltrada.lista) {
                ListItem(modifier = Modifier.clickable {
                    consulta = it
                    onQueryChange(it)
                }, headlineContent = { Text(text = it) })
            }
        }
    }
}

@Composable
fun PersonasACards(personas: List<Persona>, filtroTitulacion: String, estadoBotones: Boolean) {
    LazyColumn(
        modifier = Modifier
            .fillMaxHeight()
    ) {
        items(personas) { persona: Persona ->
            if (filtroTitulacion.isEmpty()) {
                TarjetaContacto(
                    colorFondo = Purple40,
                    persona = persona,
                    estadoBotones = estadoBotones
                )
            } else if (persona.titulacion.startsWith(filtroTitulacion, true)) {
                TarjetaContacto(
                    colorFondo = Purple40,
                    persona = persona,
                    estadoBotones = estadoBotones
                )
            }
        }
    }
}

