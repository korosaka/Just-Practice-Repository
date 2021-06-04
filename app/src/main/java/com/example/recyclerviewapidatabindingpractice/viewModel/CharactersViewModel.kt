package com.example.recyclerviewapidatabindingpractice.viewModel

import android.text.Editable
import android.text.TextWatcher
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recyclerviewapidatabindingpractice.model.Character
import com.example.recyclerviewapidatabindingpractice.repository.characters.CharacterImageRepository
import com.example.recyclerviewapidatabindingpractice.repository.characters.CharactersRepository
import com.example.recyclerviewapidatabindingpractice.repository.characters.CharactersURLRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CharactersViewModel : ViewModel() {

    private var characters: MutableList<Character> = mutableListOf()
    var liveCharacters: MutableLiveData<MutableList<Character>> = MutableLiveData()

    private val characterRepo: CharactersRepository
    private val characterImageRepo: CharacterImageRepository
    private val charactersURLRepository: CharactersURLRepository

    var filteringWord: MutableLiveData<String> = MutableLiveData()
    var clickLister: ClickItemListener? = null

    init {
        filteringWord.value = ""
        liveCharacters = MutableLiveData(characters)
        characterRepo = CharactersRepository()
        characterImageRepo = CharacterImageRepository()
        charactersURLRepository = CharactersURLRepository()
    }

    fun fetchCharacters() {
        viewModelScope.launch(Dispatchers.IO) {

            // fetching characters API URL
            val charactersURLStr =
                charactersURLRepository.fetchCharactersURL()
                    ?: return@launch //TODO error handling
            println("test: url: $charactersURLStr")


            // fetching characters
            val fetchedData = characterRepo.fetchCharacters(charactersURLStr) ?: return@launch //TODO error handling
            characters = fetchedData
            viewModelScope.launch(Dispatchers.Main) {
                liveCharacters.value = characters
            }

            // fetching character images
            for (i in characters.indices) {
                viewModelScope.launch(Dispatchers.IO) {
                    val urlStr = characters[i].image_url
                    println("test: start $i")
                    val image = characterImageRepo.fetchImage(urlStr)
                    println("test: finish $i")
                    viewModelScope.launch(Dispatchers.Main) {
                        characters[i].image = image
                        liveCharacters.value = characters
                    }
                }
            }
        }
    }

    fun createTextChangeListener(): TextWatcher {
        return object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                liveCharacters.value = filterCharacters()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        }
    }

    private fun filterCharacters(): MutableList<Character> {
        val filter = filteringWord.value ?: return characters
        if (filter == "") return characters

        val filteredList = mutableListOf<Character>()

        for (character in characters) {
            if (character.name.toUpperCase().contains(filter.toUpperCase())) {
                filteredList.add(character)
            }
        }
        return filteredList
    }

    fun getCharacter(index: Int) = liveCharacters.value?.get(index)

    fun getCharacterCount() = liveCharacters.value?.size ?: 0

    fun onClickCharacter(character: Character) {
        clickLister?.onClickItem(character)
    }
}

interface ClickItemListener {
    fun onClickItem(item: Character)
}