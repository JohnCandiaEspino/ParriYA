package com.example.myapplication2

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.VideoView
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
import android.content.Intent

import androidx.compose.ui.platform.LocalContext

fun abrirVideoYouTube(context: android.content.Context, url: String) {
    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    context.startActivity(intent)
}




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

                        DetalleCarneScreen(nombre, descripcion, precio, imagenId, dificultad, urlVideo)
                    }
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

data class Carne(val nombre: String, val descripcion: String, val precio: String, val imagenId: Int,val dificultad: String, val urlVideo: String)

val listaCarnes = listOf(
    Carne("Bife de Chorizo", "Corte grueso jugoso y sabroso.", "S/ 35.00", R.drawable.bife, "FÁCIL", "https://www.youtube.com/shorts/HKYy94J8_VA" ),
    Carne("Costillas BBQ", "Costillas marinadas en salsa barbacoa.", "S/ 42.00", R.drawable.costilla, "INTERMEDIO", "https://www.youtube.com/shorts/dSJFPf0nrk4"),
    Carne("Chorizo Artesanal", "Chorizos artesanales con toque ahumado.", "S/ 18.00", R.drawable.chorizo, "FÁCIL", "https://www.youtube.com/shorts/vcyQWcLVhd0"),
    Carne("Picanha", "Corte brasileño con capa de grasa.", "S/ 38.00", R.drawable.picanha, "DIFÍCIL", "https://www.youtube.com/shorts/vNWdHTqS884"),
    Carne("Asado de Tira", "Corte tradicional para parrilla.", "S/ 33.00", R.drawable.asado, "FÁCIL", "https://www.youtube.com/shorts/qbggfnOdXyc")
)

@Composable
fun HomeScreen(navController: NavController) {
    Column(modifier = Modifier.fillMaxSize().background(Color.Black).padding(16.dp)) {
        Text("Carnes disponibles", fontSize = 24.sp, color = Color.White, fontWeight = FontWeight.Bold, modifier = Modifier.padding(bottom = 16.dp))

        LazyColumn(verticalArrangement = Arrangement.spacedBy(16.dp)) {
            items(listaCarnes) { carne ->
                Card(modifier = Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(containerColor = Color.DarkGray)) {
                    Row(modifier = Modifier.fillMaxWidth().padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
                        Image(
                            painter = painterResource(id = carne.imagenId),
                            contentDescription = carne.nombre,
                            modifier = Modifier.size(80.dp).padding(end = 12.dp)
                        )
                        Column(modifier = Modifier.weight(1f)) {
                            Text(carne.nombre, color = Color.White, fontWeight = FontWeight.Bold)
                            Text(carne.descripcion, color = Color.LightGray, fontSize = 14.sp)
                            Text(carne.precio, color = Color(0xFFFFC107), fontWeight = FontWeight.Medium)

                        }
                        Button(onClick = {
                            val nombreEncoded = Uri.encode(carne.nombre)
                            val descripcionEncoded = Uri.encode(carne.descripcion)
                            val precioEncoded = Uri.encode(carne.precio)
                            val dificultadEncoded = Uri.encode(carne.dificultad)
                            val urlVideoEncoded = Uri.encode(carne.urlVideo)
                            navController.navigate("detalle/$nombreEncoded/$descripcionEncoded/$precioEncoded/${carne.imagenId}/$dificultadEncoded/$urlVideoEncoded"   )
                        }) {
                            Text("Ver más")
                        }

                    }
                }
            }
        }
    }
}

@Composable
fun DetalleCarneScreen(
    nombre: String,
    descripcion: String,
    precio: String,
    imagenId: Int,
    dificultad: String,
    urlVideo: String    // Puedes ajustar este valor por carne
) {
    val (colorDificultad, tituloParrillero) = when (dificultad.uppercase()) {
        "FÁCIL" -> Pair(Color.Green, "Ayudante Parrillero")
        "INTERMEDIO" -> Pair(Color.Yellow, "Parrillero")
        "DIFÍCIL" -> Pair(Color.Red, "Chef parrillero")
        else -> Pair(Color.Gray, "Sin definir")
    }
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(nombre, fontSize = 30.sp, fontWeight = FontWeight.Bold, color = Color.White)
        Spacer(modifier = Modifier.height(16.dp))

        Image(
            painter = painterResource(id = imagenId),
            contentDescription = nombre,
            modifier = Modifier.size(200.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))

        Text(descripcion, color = Color.LightGray, fontSize = 16.sp)
        Spacer(modifier = Modifier.height(8.dp))
        Text(precio, color = Color(0xFFFFC107), fontSize = 20.sp, fontWeight = FontWeight.SemiBold)

        Spacer(modifier = Modifier.height(24.dp))

        // Nivel de dificultad
        Text(
            text = "Nivel: $dificultad",
            color = colorDificultad,
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp
        )
        Text(
            text = "Rango: $tituloParrillero",
            color = Color.White,
            fontSize = 16.sp
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Botones
        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(
                onClick = { Log.d("Detalle", "Comprar: $nombre") },
                modifier = Modifier.weight(1f)
            ) {
                Text("Comprar")
            }

            Button(
                onClick = {
                    // Aquí puedes poner un enlace por tipo de carne si deseas
                    val youtubeUrl = urlVideo
                    abrirVideoYouTube(context, youtubeUrl)
                },
                modifier = Modifier.weight(1f)
            ) {
                Text("Ver Tutorial")
            }

        }
    }
}


