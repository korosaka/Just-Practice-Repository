package com.example.recyclerviewapidatabindingpractice.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recyclerviewapidatabindingpractice.model.CharacterDetail
import com.example.recyclerviewapidatabindingpractice.repository.characters.CharacterDetailRepository
import com.example.recyclerviewapidatabindingpractice.repository.characters.CharacterImageRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CharacterDetailViewModel : ViewModel() {
    var characterId: String? = null
    private var characterDetail: CharacterDetail? = null
    private var liveCharacterDetail: MutableLiveData<CharacterDetail?> = MutableLiveData()

    private val characterDetailRepo: CharacterDetailRepository
    private val characterImageRepo: CharacterImageRepository

    init {
        liveCharacterDetail = MutableLiveData(characterDetail)
        characterDetailRepo = CharacterDetailRepository()
        characterImageRepo = CharacterImageRepository()
    }

    fun fetchCharacterDetail() {
        val id = characterId ?: return

        viewModelScope.launch(Dispatchers.IO) {
            // fetching data except image
            val fetchedData = characterDetailRepo.fetchCharacterDetail(id)
            characterDetail = fetchedData ?: return@launch // TODO error display
            viewModelScope.launch(Dispatchers.Main) {
                liveCharacterDetail.value = characterDetail
            }

            // fetching image
            val imageUrl = characterDetail?.imageUrl ?: return@launch // TODO error display
            characterDetail?.image = characterImageRepo.fetchImage(imageUrl)
            viewModelScope.launch(Dispatchers.Main) {
                liveCharacterDetail.value = characterDetail
            }
        }
    }
}