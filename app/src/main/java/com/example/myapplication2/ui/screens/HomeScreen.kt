package com.example.myapplication2.ui.screens

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.myapplication2.data.carrito
import com.example.myapplication2.ui.components.CarneCard
import com.example.myapplication2.data.listaCarnes


@Composable
fun HomeScreen(navController: NavController) {
    var selectedDificultad by remember { mutableStateOf("TODOS") }
    val dificultades = listOf("TODOS", "FÁCIL", "INTERMEDIO", "DIFÍCIL")

    Column(
        modifier = Modifier.fillMaxSize().background(Color.Black).padding(16.dp)
    ) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
            Text("Carnes disponibles", fontSize = 24.sp, color = Color.White, fontWeight = FontWeight.Bold)
            //carrito
            IconButton(onClick = { navController.navigate("carrito") }) {
                BadgedBox(
                    badge = {
                        if (carrito.sumOf { it.cantidad } > 0) {
                            Badge { Text("${carrito.sumOf { it.cantidad }}") }
                        }
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.ShoppingCart,
                        contentDescription = "Carrito",
                        tint = Color.White
                    )
                }
            }

        }

        Spacer(modifier = Modifier.height(16.dp))

        var expanded by remember { mutableStateOf(false) }
        Box {
            Button(onClick = { expanded = true }) {
                Text("Filtrar: $selectedDificultad")
            }
            DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                dificultades.forEach { dificultad ->
                    DropdownMenuItem(
                        text = { Text(dificultad) },
                        onClick = {
                            selectedDificultad = dificultad
                            expanded = false
                        }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        val carnesFiltradas = if (selectedDificultad == "TODOS") listaCarnes else listaCarnes.filter { it.dificultad.equals(selectedDificultad, ignoreCase = true) }

        LazyColumn(verticalArrangement = Arrangement.spacedBy(16.dp)) {
            items(carnesFiltradas) { carne ->
                CarneCard(carne = carne, navController = navController)
            }
        }
    }
}