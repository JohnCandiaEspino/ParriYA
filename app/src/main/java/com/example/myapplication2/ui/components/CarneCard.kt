package com.example.myapplication2.ui.components

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.myapplication2.Carne


@Composable
fun CarneCard(carne: Carne, navController: NavController) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.DarkGray)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = carne.imagenId),
                contentDescription = carne.nombre,
                modifier = Modifier
                    .size(80.dp)
                    .padding(end = 12.dp)
            )
            Column(modifier = Modifier.weight(1f)) {
                Text(carne.nombre, color = Color.White, fontWeight = FontWeight.Bold)
                Text(carne.descripcion, color = Color.LightGray, fontSize = 14.sp)
                Text(carne.precio, color = Color(0xFFFFC107), fontWeight = FontWeight.Medium)
            }
            Column(modifier = Modifier.weight(1f)) {
                Button(onClick = {
                    val nombreEncoded = Uri.encode(carne.nombre)
                    val descripcionEncoded = Uri.encode(carne.descripcion)
                    val precioEncoded = Uri.encode(carne.precio)
                    val dificultadEncoded = Uri.encode(carne.dificultad)
                    val videoEncoded = Uri.encode(carne.urlVideo)
                    navController.navigate("detalle/$nombreEncoded/$descripcionEncoded/$precioEncoded/${carne.imagenId}/$dificultadEncoded/$videoEncoded")
                },

                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Transparent, // fondo transparente
                            contentColor = Color.White),          // texto blanco (ajústalo si usas otro fondo)
                        elevation = ButtonDefaults.buttonElevation(0.dp),
                        modifier = Modifier.padding(top = 8.dp)
                ) {
                    Text("Ver más")
                }
                Text(carne.dificultad, color = Color(0xFFFFC107), fontSize = 12.sp)
            }
        }
    }
}
