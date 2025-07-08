package com.example.myapplication2.data

import android.widget.Toast
import androidx.compose.runtime.mutableStateListOf
import com.example.myapplication2.Carne
import com.example.myapplication2.ItemCarrito

val carrito = mutableStateListOf<ItemCarrito>()
fun calcularTotalCarrito(): Double {
    return carrito.sumOf { it.carne.precio.replace("S/", "").trim().toDouble() * it.cantidad }
}

fun agregarAlCarrito(carne: Carne, context: android.content.Context) {
    val existente = carrito.find { it.carne.nombre == carne.nombre }
    if (existente != null) {
        existente.cantidad += 1
    } else {
        carrito.add(ItemCarrito(carne, 1))
    }
    Toast.makeText(context, "Producto agregado al carrito", Toast.LENGTH_SHORT).show()
}
