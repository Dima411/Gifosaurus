package com.example.gifosaurus.network.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gifosaurus.config.Constants.API_KEY
import com.example.gifosaurus.config.Constants.LIMIT_GIFS
import com.example.gifosaurus.config.Constants.OFFSET_LIMIT
import com.example.gifosaurus.network.model.Gif
import com.example.gifosaurus.repository.GiphyRepository
import com.example.gifosaurus.ui.screens.chiefscreen.searchbar.InputHistory
import kotlinx.coroutines.launch

/**
 * GifViewModel is a ViewModel class that manages and stores UI-related data for the Gif application.
 * It communicates with the GiphyRepository to load and search for GIFs.
 *
 * The ViewModel contains LiveData for the GIFs, full search history, and shown search history.
 * It also maintains an offset for loading additional GIFs.
 *
 * The ViewModel provides several functions:
 * - loadTrendingGifs: Loads trending GIFs using the GiphyRepository.
 * - searchGifs: Searches for GIFs based on a query string and updates the LiveData with the results.
 * - addInSearchText: Adds a new search text to the full search history.
 * - searchHistory: Returns a list of search history entries that contain a specific text.
 * - updateShowHistory: Updates the shown search history based on a specific text.
 * - clearFilter: Clears the current filter and reloads trending GIFs.
 *
 * The ViewModel uses Kotlin Coroutines to handle long-running tasks such as network requests.
 */
class GifViewModel(private val repository: GiphyRepository) : ViewModel() {
    private val _gifs = MutableLiveData<List<Gif>>()
    val gifs: LiveData<List<Gif>> = _gifs

    val searchText = mutableStateOf("")
    val isActiveSearch = mutableStateOf(false)

    val fullHistory = mutableStateListOf<InputHistory>()
    val showHistory = mutableStateListOf<InputHistory>()

    private var offset = 0

    init {
        loadTrendingGifs()
    }

    fun loadTrendingGifs() {
        viewModelScope.launch {
            val trendingGifs = API_KEY.let { repository.getTrendingGifs(it, LIMIT_GIFS, offset) }
            _gifs.value = trendingGifs.data
            offset += OFFSET_LIMIT
        }
    }


    fun searchGifs(query: String) {
        viewModelScope.launch {
            val searchResults = API_KEY.let { repository.searchGifs(it, query, LIMIT_GIFS, offset) }
            val filteredResults = searchResults.data.filter { it.title.contains(query, ignoreCase = true) }
            _gifs.value = filteredResults
            offset += OFFSET_LIMIT
        }
    }

    /**
     * Function to add text to the search history.
     *
     * @param text The text to be added to the search history.
     */
    fun addInSearchText(text: String) {
        val isTextInSearchHistory = fullHistory.any { it.textInput == text}
        if (!isTextInSearchHistory) {
            fullHistory.add(InputHistory(text))
        }
    }

    /**
     * Function to filter the search history based on the entered text.
     *
     * @param text The text to be used for filtering the search history.
     * @return A list of filtered search history entries.
     */
    private fun searchHistory(text: String) :  List<InputHistory> {
        val newShowHistory = fullHistory.toList()
        val filterHistory = newShowHistory.filter {
            it.textInput.lowercase().contains(text, true)
        }
        return filterHistory
    }

    fun updateShowHistory(text: String) {
        showHistory.clear()
        showHistory.addAll(searchHistory(text))
    }

    fun clearFilter() {
        viewModelScope.launch {
            _gifs.value = emptyList()
            offset = 0
            loadTrendingGifs()
        }
    }
}
