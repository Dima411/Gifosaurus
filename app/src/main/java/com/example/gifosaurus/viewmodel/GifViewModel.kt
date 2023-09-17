package com.example.gifosaurus.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gifosaurus.model.Gif
import com.example.gifosaurus.repository.GiphyRepository
import kotlinx.coroutines.launch

const val API_KEY = "mOj6r0IttEZCtg9D3sdYjn3bJbMcRhKv"

/**
* GifViewModel, який використовує GiphyRepository для завантаження GIF-файлів при ініціалізації
* та пошуку GIF-файлів за запитом
*/
class GifViewModel(private val repository: GiphyRepository) : ViewModel() {
    private val _gifs = MutableLiveData<List<Gif>>()
    val gifs: LiveData<List<Gif>> = _gifs

    private var offset = 0

    init {
        loadTrendingGifs()
    }

    fun loadTrendingGifs() {
        viewModelScope.launch {
            val trendingGifs = repository.getTrendingGifs(API_KEY, 4999, offset)
            _gifs.value = trendingGifs.data
            offset += 25
        }
    }

    fun searchGifs(query: String) {
        viewModelScope.launch {
            val searchResults = repository.searchGifs(API_KEY, query, 25, offset)
            _gifs.value = searchResults.data
            offset += 25
        }
    }
}
