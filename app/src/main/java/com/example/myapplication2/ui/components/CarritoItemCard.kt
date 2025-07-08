package com.example.myapplication2.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.myapplication2.ItemCarrito

@Composable
fun CarritoItemCard(item: ItemCarrito){
    Card(colors = CardDefaults.cardColors(containerColor = Color.DarkGray)) {
        Row(modifier = Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
            Image(painter = painterResource(id = item.carne.imagenId), contentDescription = item.carne.nombre, modifier = Modifier.size(80.dp).padding(end = 12.dp))
            Column {
                Text(item.carne.nombre, color = Color.White)
                Text(item.carne.precio, color = Color(0xFFFFC107))
                Text("Cantidad: ${item.cantidad}", color = Color.LightGray)
            }
        }
    }
}