package com.example.myapplication2

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.VideoView
import android.content.Intent
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.myapplication2.ui.theme.MyApplication2Theme

fun abrirVideoYouTube(context: android.content.Context, url: String) {
    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    context.startActivity(intent)
}

val carrito = mutableStateListOf<Carne>()

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplication2Theme {
                val navController = rememberNavController()

                NavHost(navController = navController, startDestination = "landing") {
                    composable("landing") { VideoBackgroundLanding(navController) }
                    composable("login") { LoginScreen(navController) }
                    composable("home") { HomeScreen(navController) }
                    composable("detalle/{nombre}/{descripcion}/{precio}/{imagenId}/{dificultad}/{urlVideo}") { backStackEntry ->
                        val nombre = backStackEntry.arguments?.getString("nombre") ?: ""
                        val descripcion = backStackEntry.arguments?.getString("descripcion") ?: ""
                        val precio = backStackEntry.arguments?.getString("precio") ?: ""
                        val imagenId = backStackEntry.arguments?.getString("imagenId")?.toIntOrNull() ?: 0
                        val dificultad = backStackEntry.arguments?.getString("dificultad") ?: ""
                        val urlVideo = backStackEntry.arguments?.getString("urlVideo") ?: ""

                        DetalleCarneScreen(nombre, descripcion, precio, imagenId, dificultad, urlVideo, navController)
                    }
                    composable("carrito") { CarritoScreen() }
                }
            }
        }
    }
}

@Composable
fun VideoBackgroundLanding(navController: NavController) {
    val context = LocalContext.current

    Box(modifier = Modifier.fillMaxSize().background(Color.Black)) {
        AndroidView(
            factory = {
                VideoView(it).apply {
                    val uri = Uri.parse("android.resource://${context.packageName}/raw/fondo")
                    setVideoURI(uri)
                    setOnPreparedListener { mp ->
                        mp.isLooping = true
                        mp.setVolume(0f, 0f)
                        start()
                    }
                }
            },
            modifier = Modifier.fillMaxSize()
        )

        Box(modifier = Modifier.fillMaxSize().background(Color.Black.copy(alpha = 0.4f)))

        Box(
            modifier = Modifier.fillMaxSize().padding(24.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text("\uD83D\uDD25 ParriYA", fontSize = 40.sp, color = Color.White)
                Spacer(modifier = Modifier.height(16.dp))
                Text("Domina el fuego. Vive la parrilla.", fontSize = 18.sp, color = Color.White)
                Spacer(modifier = Modifier.height(32.dp))
                Button(onClick = { navController.navigate("login") }) {
                    Text("Empezar ahora")
                }
            }
        }
    }
}

@Composable
fun LoginScreen(navController: NavController) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Box(modifier = Modifier.fillMaxSize().background(Color.Black), contentAlignment = Alignment.Center) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text("Iniciar Sesión", color = Color.White, fontSize = 28.sp)
            Spacer(modifier = Modifier.height(20.dp))

            OutlinedTextField(
                value = username,
                onValueChange = { username = it },
                label = { Text("Usuario") }
            )

            Spacer(modifier = Modifier.height(10.dp))

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Contraseña") },
                visualTransformation = PasswordVisualTransformation()
            )

            Spacer(modifier = Modifier.height(20.dp))

            Button(onClick = { navController.navigate("home") }) {
                Text("Ingresar")
            }
        }
    }
}

@Composable
fun CarritoScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(16.dp)
    ) {
        Text("Carrito de Compras", fontSize = 24.sp, color = Color.White, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(16.dp))

        if (carrito.isEmpty()) {
            Text("Tu carrito está vacío", color = Color.LightGray)
        } else {
            LazyColumn(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                items(carrito) { carne ->
                    Card(colors = CardDefaults.cardColors(containerColor = Color.DarkGray)) {
                        Row(modifier = Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
                            Image(painter = painterResource(id = carne.imagenId), contentDescription = carne.nombre, modifier = Modifier.size(80.dp).padding(end = 12.dp))
                            Column {
                                Text(carne.nombre, color = Color.White)
                                Text(carne.precio, color = Color(0xFFFFC107))
                            }
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = { carrito.clear() }, modifier = Modifier.fillMaxWidth()) {
                Text("Vaciar Carrito")
            }
        }
    }
}

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
                carrito.add(Carne(nombre, descripcion, precio, imagenId, dificultad, urlVideo))
                navController.navigate("carrito")
            }, modifier = Modifier.weight(1f)) {
                Text("Agregar al Carrito")
            }
            Button(onClick = { abrirVideoYouTube(context, urlVideo) }, modifier = Modifier.weight(1f)) {
                Text("Ver Tutorial")
            }
        }
    }
}

data class Carne(val nombre: String, val descripcion: String, val precio: String, val imagenId: Int, val dificultad: String, val urlVideo: String)

val listaCarnes = listOf(
    Carne("Bife de Chorizo", "Corte grueso jugoso y sabroso.", "S/ 35.00", R.drawable.bife, "FÁCIL", "https://www.youtube.com/shorts/HKYy94J8_VA"),
    Carne("Costillas BBQ", "Costillas marinadas en salsa barbacoa.", "S/ 42.00", R.drawable.costilla, "INTERMEDIO", "https://www.youtube.com/shorts/dSJFPf0nrk4"),
    Carne("Chorizo Artesanal", "Chorizos artesanales con toque ahumado.", "S/ 18.00", R.drawable.chorizo, "FÁCIL", "https://www.youtube.com/shorts/vcyQWcLVhd0"),
    Carne("Picanha", "Corte brasileño con capa de grasa.", "S/ 38.00", R.drawable.picanha, "DIFÍCIL", "https://www.youtube.com/shorts/vNWdHTqS884"),
    Carne("Asado de Tira", "Corte tradicional para parrilla.", "S/ 33.00", R.drawable.asado, "FÁCIL", "https://www.youtube.com/shorts/qbggfnOdXyc")
)

@Composable
fun HomeScreen(navController: NavController) {
    var selectedDificultad by remember { mutableStateOf("TODOS") }
    val dificultades = listOf("TODOS", "FÁCIL", "INTERMEDIO", "DIFÍCIL")

    Column(
        modifier = Modifier.fillMaxSize().background(Color.Black).padding(16.dp)
    ) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text("Carnes disponibles", fontSize = 24.sp, color = Color.White, fontWeight = FontWeight.Bold)
            Button(onClick = { navController.navigate("carrito") }) {
                Text("Carrito (${carrito.size})")
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
                Card(modifier = Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(containerColor = Color.DarkGray)) {
                    Row(modifier = Modifier.fillMaxWidth().padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
                        Image(painter = painterResource(id = carne.imagenId), contentDescription = carne.nombre, modifier = Modifier.size(80.dp).padding(end = 12.dp))
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
                            }) {
                                Text("Ver más")
                            }
                            Text(carne.dificultad, color = Color(0xFFFFC107), fontSize = 12.sp)
                        }
                    }
                }
            }
        }
    }
}
