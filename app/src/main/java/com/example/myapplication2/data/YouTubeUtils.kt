package com.example.myapplication2.data

import android.content.Intent
import android.net.Uri

fun abrirVideoYouTube(context: android.content.Context, url: String) {
    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    context.startActivity(intent)
}