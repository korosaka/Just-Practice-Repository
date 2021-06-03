package com.example.recyclerviewapidatabindingpractice.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recyclerviewapidatabindingpractice.model.Character
import com.example.recyclerviewapidatabindingpractice.model.CharacterImage
import com.example.recyclerviewapidatabindingpractice.repository.characters.CharacterImageRepository
import com.example.recyclerviewapidatabindingpractice.repository.characters.CharactersRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CharactersViewModel : ViewModel() {

    var characters: MutableList<Character> = mutableListOf()
    var liveCharacters: MutableLiveData<MutableList<Character>> = MutableLiveData()
    var images: Array<CharacterImage?> = arrayOf()
    var liveImages: MutableLiveData<Array<CharacterImage?>> = MutableLiveData()
    private val characterRepo: CharactersRepository
    private val characterImageRepo: CharacterImageRepository

    init {
//        liveCharacters.value = mutableListOf()
//        images.value = arrayOf()
        liveCharacters = MutableLiveData(characters)
        liveImages = MutableLiveData(images)
        characterRepo = CharactersRepository()
        characterImageRepo = CharacterImageRepository()
    }

    fun fetchCharacters() {
        viewModelScope.launch {
            val fetchedData = characterRepo.fetchCharacters() ?: return@launch //TODO error handling
            characters = fetchedData
            liveCharacters.value = characters

//            images = arrayOfNulls(fetchedData.size)
//            liveImages.value = images

            val indices = characters.indices

            for (i in indices) {
//                val urlStr = liveCharacters.value?.get(i)?.image_url ?: continue
                val urlStr = characters[i].image_url
                viewModelScope.launch(Dispatchers.IO) {
                    println("test: start $i")
                    val image = characterImageRepo.fetchImage(urlStr)
                    println("test: finish $i")
                    viewModelScope.launch(Dispatchers.Main) {
//                        images[i] = CharacterImage(image)
//                        liveImages.value = images
                        characters[i].image = image
                        liveCharacters.value = characters
                    }
                }
            }
        }
    }

//    fun getCharacter(index: Int) = liveCharacters.value?.get(index)
//    fun getImage(index: Int) = images[index]
        fun getCharacter(index: Int) = characters[index]
//    fun getImage(index: Int) = images[index]


//    fun getCharacterCount() = liveCharacters.value?.size
fun getCharacterCount() = characters.size
}