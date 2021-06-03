package com.example.recyclerviewapidatabindingpractice.repository.characters

data class CharactersEntity(val results: Array<CharacterEntity>)

data class CharacterEntity(val name: String, val image: String)