package com.example.recyclerviewapidatabindingpractice.repository.characters

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.Image
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.HttpURLConnection
import java.net.URL
import kotlin.concurrent.thread

class CharacterImageRepository {

    fun fetchImage(urlStr: String): Bitmap? {
        var image: Bitmap? = null

        val url = URL(urlStr)
        val urlConnection = url.openConnection() as HttpURLConnection

        urlConnection.readTimeout = 10000
        urlConnection.connectTimeout = 20000

        urlConnection.requestMethod = "GET"
        urlConnection.instanceFollowRedirects = false

        urlConnection.setRequestProperty("Accept-Language", "en")


//        val resp = urlConnection.responseCode

//        return withContext(Dispatchers.Default) {
        try {

            urlConnection.connect()
            val inst = urlConnection.inputStream
            val bitmap = BitmapFactory.decodeStream(inst)
            image = bitmap
            inst.close()
        } catch (e: Exception) {
            println("test: $e")
        }
        urlConnection.disconnect()
        return image
//            return@withContext image
//        }

    }
}