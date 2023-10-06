package com.example.gifosaurus.ui.screens.detailscreen


import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.gifosaurus.config.Constants.BASE_URL
import com.example.gifosaurus.config.Constants.GIF_EXTENSION
import com.example.gifosaurus.config.Icons.iconArrowBack
import com.example.gifosaurus.network.model.Gif


@OptIn(
    ExperimentalMaterial3Api::class,
    ExperimentalGlideComposeApi::class
)
@Composable
fun GifDetailScreen(gif: Gif, onBack: () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(gif.title) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(iconArrowBack, contentDescription = null)
                    }
                }
            )
        },
        content = { padding ->
            Box(
                modifier = Modifier
                    .padding(padding)
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                val gifUrl = "$BASE_URL${gif.id}$GIF_EXTENSION"
                Column {
                    ShareButton(
                        gifUrl = gifUrl,
                        modifier = Modifier
                            .align(Alignment.End)
                    )
                    GlideImage(
                        model = gifUrl,
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize()
                    )
                }
                
            }
        }
    )
}