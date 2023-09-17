package com.example.gifosaurus.ui.screens

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.example.gifosaurus.model.Gif
import com.example.gifosaurus.ui.theme.TopBarBackgroundColor
import com.example.gifosaurus.ui.theme.TopBarTextColor
import com.example.gifosaurus.viewmodel.GifViewModel
import kotlin.Exception


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GifListScreen(viewModel: GifViewModel, onGifSelected: (Gif) -> Unit) {
    val gifs by viewModel.gifs.observeAsState(emptyList())

    Scaffold(
        topBar = {
            TopBarGifListScreen(title = "Gifosaurus: Era Of GIF")
        }
    ) { paddingValues ->
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

@OptIn(ExperimentalCoilApi::class)
@Composable
private fun GifListItem(gif: Gif, onClick: (Gif) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(1.dp)
            .clickable { onClick(gif) },
        shape = ShapeDefaults.ExtraSmall,
        elevation = CardDefaults.cardElevation(0.dp)
    ) {
        Image(
            painter = rememberImagePainter(data = gif.url),
            contentDescription = null,
            modifier = Modifier.aspectRatio(1f)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TopBarGifListScreen(
    title: String
) {
    TopAppBar(
        title = {
            Text(
                text = title,
                color = TopBarTextColor
            )
        },
        colors = TopAppBarDefaults.smallTopAppBarColors(TopBarBackgroundColor)
    )
}
