package com.example.recyclerviewapidatabindingpractice.repository.characters

import com.example.recyclerviewapidatabindingpractice.model.API
import com.example.recyclerviewapidatabindingpractice.model.Character
import com.example.recyclerviewapidatabindingpractice.model.Common
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class CharactersRepository {

    //    /**
//     * suspend is must be used because there is withContext within it
//     */
    fun fetchCharacters(urlStr: String): MutableList<Character>? {

//        /**
//         * use withContext(Dispatchers.IO) so that the API task is always done on Non-Main thread
//         */
//        return withContext(Dispatchers.IO) {
//            return@withContext try {
        return try {
            val response = executeAPI(urlStr)
//                return@withContext extractCharacters(response.body()!!)
            return extractCharacters(response.body()!!)
        } catch (e: Exception) {
            println("test CharactersRepository: $e")
            null
        }
//        }
    }

    private fun extractCharacters(entity: CharactersEntity): MutableList<Character> {

        val characterList: MutableList<Character> = mutableListOf()

        for (characterEntity in entity.results) {
            val character =
                Character(characterEntity.id, characterEntity.name, characterEntity.image, null)
            characterList.add(character)
        }

        return characterList

    }

    private fun executeAPI(urlStr: String): Response<CharactersEntity> {
        val service = restClient().create(API::class.java)
        return service.fetchCharacters(urlStr).execute()
    }

    private fun restClient(): Retrofit {
        return Retrofit.Builder()
//            .baseUrl("https://rickandmortyapi.com/api/")
            .baseUrl(Common.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}