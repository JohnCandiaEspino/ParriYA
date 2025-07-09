package com.example.myapplication2.ui.screens


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun RecetaCompletaScreen(
    nombre: String,
    ingredientes: List<String>,
    pasos: List<String>,
    navController: NavController
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(16.dp)
    ) {
        Text(nombre, fontSize = 28.sp, color = Color.White)
        Spacer(modifier = Modifier.height(16.dp))

        Text("🥩 Ingredientes:", color = Color(0xFFFFC107), fontSize = 20.sp)
        ingredientes.forEach {
            Text("- $it", color = Color.White)
        }

        Spacer(modifier = Modifier.height(16.dp))
        Text("🔥 Pasos:", color = Color(0xFFFFC107), fontSize = 20.sp)
        pasos.forEachIndexed { i, paso ->
            Text("${i + 1}. $paso", color = Color.White)
        }

        Spacer(modifier = Modifier.height(24.dp))
        Button(onClick = { navController.popBackStack() },
                colors = ButtonDefaults.outlinedButtonColors(
                containerColor = Color.Transparent,
                contentColor = Color.White
            ),
             modifier = Modifier.fillMaxWidth()) {
            Text("Volver")
        }
    }
}
