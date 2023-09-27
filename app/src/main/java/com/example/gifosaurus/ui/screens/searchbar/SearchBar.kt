package com.example.gifosaurus.ui.screens.searchbar

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gifosaurus.ui.theme.SearchTextColor
import com.example.gifosaurus.viewmodel.GifViewModel

/**
 * The function shows the search panel on the chief screen and looking for right gif
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(viewModel: GifViewModel) {
    val searchText = remember { mutableStateOf("") }
    val iconSearch = Icons.Filled.Search
    val isActive = remember {
        mutableStateOf(false)
    }

    fun actionsForClickableInSearch(text: String) {
        isActive.value = false
        searchText.value = text
        viewModel.addInSearchText(searchText.value)
        if(searchText.value != "") {
            viewModel.searchGifs(searchText.value)
        } else {
            viewModel.clearFilter()
        }
    }

    androidx.compose.material3.SearchBar(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        query = searchText.value,
        onQueryChange = { text ->
            searchText.value = text
            viewModel.showHistory.clear()
            viewModel.updateShowHistory(text)
        },
        onSearch = { text ->
            actionsForClickableInSearch(text)
        },
        placeholder = {
            Text(
                modifier = Modifier
                    .padding(start = 10.dp),
                text = "Search",
                fontSize = 20.sp,
                color = SearchTextColor
            )
        },
        active = isActive.value,
        onActiveChange = {
            isActive.value = it
        },
        shape = ShapeDefaults.ExtraLarge,
        trailingIcon = {
            Icon(
                modifier = Modifier
                    .clickable {
                        actionsForClickableInSearch(searchText.value)
                    }
                    .padding(end = 10.dp),
                imageVector = iconSearch,
                contentDescription = null,
                tint = SearchTextColor
            )
        }
    ) {
        LazyColumn {
            items(viewModel.showHistory) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp)
                        .clickable {
                            actionsForClickableInSearch(it.textInput)
                        }
                ) {
                    Text(text = it.textInput)
                }
            }
        }
    }
}