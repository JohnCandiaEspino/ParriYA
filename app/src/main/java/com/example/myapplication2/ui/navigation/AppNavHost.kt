package com.example.myapplication2.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.myapplication2.ui.screens.*
import com.example.myapplication2.ui.screens.VideoBackgroundLanding

import androidx.navigation.NavBackStackEntry

@Composable
fun AppNavHost(navController: NavHostController) {
    NavHost(navController = navController, startDestination = "landing") {
        composable("landing") { VideoBackgroundLanding(navController) }
        composable("login") { LoginScreen(navController) }
        composable("home") { HomeScreen(navController) }
        composable("carrito") { CarritoScreen() }
        composable("foro") { ForoScreen() }

        composable("detalle/{nombre}/{descripcion}/{precio}/{imagenId}/{dificultad}/{urlVideo}") { backStackEntry: NavBackStackEntry ->
            val nombre = backStackEntry.arguments?.getString("nombre") ?: ""
            val descripcion = backStackEntry.arguments?.getString("descripcion") ?: ""
            val precio = backStackEntry.arguments?.getString("precio") ?: ""
            val imagenId = backStackEntry.arguments?.getString("imagenId")?.toIntOrNull() ?: 0
            val dificultad = backStackEntry.arguments?.getString("dificultad") ?: ""
            val urlVideo = backStackEntry.arguments?.getString("urlVideo") ?: ""

            DetalleCarneScreen(
                nombre,
                descripcion,
                precio,
                imagenId,
                dificultad,
                urlVideo,
                navController
            )
        }
        composable("receta/{nombre}") { backStackEntry: NavBackStackEntry ->
            val nombre = backStackEntry.arguments?.getString("nombre") ?: ""
            val ingredientes = listOf("1 KG de carne", "Sal Gruesa", "Pimienta", "Aceite de Oliva")
            val pasos = listOf(
                "Precalienta la parrilla.",
                "Sazona la carne.",
                "Colócala en la parrilla.",
                "Cocina 5 minutos por lado.",
                "Déjala reposar y sirve."
            )

            RecetaCompletaScreen(nombre, ingredientes=ingredientes, pasos=pasos, navController)
        }

    }
}
