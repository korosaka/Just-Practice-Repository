package com.example.recyclerviewapidatabindingpractice.repository.characters

import com.example.recyclerviewapidatabindingpractice.model.API
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.Exception

class CharactersURLRepository {

    fun fetchCharactersURL(): String? {
        return try {
            val response = executeAPI()
            println("test: success: " + response.body().toString())
            response.body()!!.characters
        } catch (e: Exception) {
            println("test: $e")
            null
        }
    }

    private fun executeAPI(): Response<CharactersURLEntity> {
        val service = restClient().create(API::class.java)
        return service.fetchCharactersURL().execute()
    }

    private fun restClient(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://rickandmortyapi.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}