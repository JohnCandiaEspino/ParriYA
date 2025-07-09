package com.example.myapplication2.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.myapplication2.Carne
import com.example.myapplication2.data.abrirVideoYouTube
import com.example.myapplication2.data.agregarAlCarrito

@Composable
fun DetalleCarneScreen(nombre: String, descripcion: String, precio: String, imagenId: Int, dificultad: String, urlVideo: String, navController: NavController) {
    val context = LocalContext.current
    Column(
        modifier = Modifier.fillMaxSize().background(Color.Black).padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(nombre, fontSize = 30.sp, fontWeight = FontWeight.Bold, color = Color.White)
        Spacer(modifier = Modifier.height(16.dp))
        Image(painter = painterResource(id = imagenId), contentDescription = nombre, modifier = Modifier.size(200.dp))
        Spacer(modifier = Modifier.height(16.dp))
        Text(descripcion, color = Color.LightGray, fontSize = 16.sp)
        Spacer(modifier = Modifier.height(8.dp))
        Text(precio, color = Color(0xFFFFC107), fontSize = 20.sp, fontWeight = FontWeight.SemiBold)
        Spacer(modifier = Modifier.height(24.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(16.dp), modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            Button(onClick = {
                agregarAlCarrito(Carne(nombre, descripcion, precio, imagenId, dificultad, urlVideo), context)
            }, modifier = Modifier.weight(1f)) {
                Text("Agregar al Carrito")
            }
            Button(onClick = { navController.navigate("foro") }, modifier = Modifier.weight(1f)) {
                Text("Foro Parrilleros", textAlign = TextAlign.Center)
            }
        }
        Row(horizontalArrangement = Arrangement.spacedBy(16.dp), modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            Button(onClick = { abrirVideoYouTube(context, urlVideo) }, modifier = Modifier.weight(1f)) {
                Text("TutoParrillas  ")
            }
            Button(onClick = { navController.navigate("receta/${nombre}") },
                modifier = Modifier.weight(1f)) {
                Text("RecetasYA ")
            }



        }

    }
}
