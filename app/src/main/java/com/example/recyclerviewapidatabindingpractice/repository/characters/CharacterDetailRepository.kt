package com.example.recyclerviewapidatabindingpractice.repository.characters

import com.example.recyclerviewapidatabindingpractice.model.API
import com.example.recyclerviewapidatabindingpractice.model.CharacterDetail
import com.example.recyclerviewapidatabindingpractice.model.Common
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
        val apiUrlStr = Common.charactersAPIUrlStr + "/" + characterId
        return service.fetchCharacterDetail(apiUrlStr).execute()
    }

    private fun restClient(): Retrofit {
        /**
         * even when a whole URL is passed to the api function, baseUrl must be set
         */
        return Retrofit.Builder()
            .baseUrl(Common.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}