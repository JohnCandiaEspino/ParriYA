package com.example.myapplication2


data class Carne(
    val nombre: String,
    val descripcion: String,
    val precio: String,
    val imagenId: Int,
    val dificultad: String,
    val urlVideo: String
)

data class ItemCarrito(
    val carne: Carne,
    var cantidad: Int
)
