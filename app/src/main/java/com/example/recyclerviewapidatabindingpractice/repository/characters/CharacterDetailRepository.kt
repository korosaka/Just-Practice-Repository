package com.example.recyclerviewapidatabindingpractice.repository.characters

import com.example.recyclerviewapidatabindingpractice.model.API
import com.example.recyclerviewapidatabindingpractice.model.CharacterDetail
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.Exception

class CharacterDetailRepository {

    fun fetchCharacterDetail(characterId: String) : CharacterDetail? {
        return try {
            val response = executeAPI(characterId)
            extractCharacterDetail(response.body()!!)
        } catch (e: Exception) {
            println("test: $e")
            null
        }
    }

    private fun extractCharacterDetail(entity: CharacterDetailEntity) : CharacterDetail {
        return CharacterDetail(
            entity.name,
            entity.status,
            entity.species,
            entity.type,
            entity.gender,
            entity.image,
            null)
    }


    private fun executeAPI(characterId: String): Response<CharacterDetailEntity> {
        val service = restClient().create(API::class.java)
        return service.fetchCharacterDetail(characterId).execute()
    }

    private fun restClient(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://rickandmortyapi.com/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}