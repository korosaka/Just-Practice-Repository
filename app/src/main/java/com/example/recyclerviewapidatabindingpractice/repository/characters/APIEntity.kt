package com.example.recyclerviewapidatabindingpractice.repository.characters

import android.graphics.Bitmap

data class CharactersEntity(val results: Array<CharacterEntity>)

data class CharacterEntity(val id: String, val name: String, val image: String)

data class CharacterDetailEntity(val name: String, val status: String, val species: String, val type: String, val gender: String, val image: String)