package com.example.recyclerviewapidatabindingpractice.model

import com.example.recyclerviewapidatabindingpractice.repository.characters.CharactersEntity
import retrofit2.Call
import retrofit2.http.GET

interface API {
    // https://rickandmortyapi.com/api/character
    @GET("character")
    fun fetchCharacters(): Call<CharactersEntity>

}