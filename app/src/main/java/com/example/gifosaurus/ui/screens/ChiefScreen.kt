package com.example.gifosaurus.ui.screens

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.gifosaurus.model.Gif
import com.example.gifosaurus.ui.screens.searchbar.SearchBar
import com.example.gifosaurus.ui.theme.SerchBarBackgroundColor
import com.example.gifosaurus.ui.theme.TopBarTextColor
import com.example.gifosaurus.viewmodel.GifViewModel

const val TITLE_PROGRAMME = "Gifosaurus: Era of gifs"

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun GifListScreen(viewModel: GifViewModel, onGifSelected: (Gif) -> Unit) {
    val gifs by viewModel.gifs.observeAsState(emptyList())

    Column {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(SerchBarBackgroundColor)
        ) {
            Column {
                Text(
                    text = TITLE_PROGRAMME,
                    modifier = Modifier
                        .align(alignment = Alignment.CenterHorizontally),
                    color = TopBarTextColor,
                    fontSize = 30.sp,
                    textAlign = TextAlign.Center
                )
                SearchBar(viewModel)
            }
        }
        Scaffold { paddingValues ->
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                contentPadding = paddingValues,
                content = {
                    itemsIndexed(gifs.chunked(1)) { index, gifChunk ->
                        gifChunk.forEach { gif ->
                            GifListItem(gif) { selectedGif ->
                                onGifSelected(selectedGif)
                            }
                            if (index == gifs.size - 1) {
                                LaunchedEffect(Unit) {
                                    viewModel.loadTrendingGifs()
                                }
                            }
                        }
                    }
                }
            )

        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
private fun GifListItem(gif: Gif, onClick: (Gif) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(1.dp)
            .aspectRatio(1f)
            .background(Color.Transparent)
            .clickable { onClick(gif) },
        shape = ShapeDefaults.ExtraSmall,
        elevation = CardDefaults.cardElevation(0.dp)
    ) {
        val gifUrl = "https://media1.giphy.com/media/${gif.id}/200.gif"

        GlideImage(
            model = gifUrl,
            contentDescription = null,
            modifier = Modifier
                .aspectRatio(1f)
                .size(200.dp)
                .fillMaxSize()
        )
    }
}


