package com.example.recyclerviewapidatabindingpractice.model

import com.example.recyclerviewapidatabindingpractice.repository.characters.CharacterDetailEntity
import com.example.recyclerviewapidatabindingpractice.repository.characters.CharactersEntity
import com.example.recyclerviewapidatabindingpractice.repository.characters.CharactersURLEntity
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface API {
    // https://rickandmortyapi.com/api/character
    @GET("character")
    fun fetchCharacters(): Call<CharactersEntity>

    // https://rickandmortyapi.com/api/character/15
    @GET("character/{id}")
    fun fetchCharacterDetail(@Path("id") characterId: String): Call<CharacterDetailEntity>

    // https://rickandmortyapi.com/api
    @GET("api")
    fun fetchCharactersURL(): Call<CharactersURLEntity>
}