package com.example.gifosaurus.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gifosaurus.model.Gif
import com.example.gifosaurus.repository.GiphyRepository
import com.example.gifosaurus.ui.screens.searchbar.InputHistory
import kotlinx.coroutines.launch

const val API_KEY = "mOj6r0IttEZCtg9D3sdYjn3bJbMcRhKv"
const val LIMIT = 1000
const val OFFSET_LIMIT = 25

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

    val fullHistory = mutableStateListOf<InputHistory>()
    val showHistory = mutableStateListOf<InputHistory>()

    private var offset = 0

    init {
        loadTrendingGifs()
    }

    fun loadTrendingGifs() {
        viewModelScope.launch {
            val trendingGifs = repository.getTrendingGifs(API_KEY, LIMIT, offset)
            _gifs.value = trendingGifs.data
            offset += OFFSET_LIMIT
        }
    }

    fun searchGifs(query: String) {
        viewModelScope.launch {
            val searchResults = repository.searchGifs(API_KEY, query, LIMIT, offset)
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
    fun searchHistory(text: String) :  List<InputHistory> {
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
