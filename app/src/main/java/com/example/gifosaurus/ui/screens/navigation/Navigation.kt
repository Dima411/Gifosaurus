package com.example.gifosaurus.ui.screens.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.gifosaurus.api.GiphyApiService
import com.example.gifosaurus.model.Gif
import com.example.gifosaurus.repository.GiphyRepository
import com.example.gifosaurus.ui.screens.GifDetailScreen
import com.example.gifosaurus.ui.screens.GifListScreen
import com.example.gifosaurus.viewmodel.GifViewModel
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 *  Create a navigation
 *
 *  Навігація між екранами
 */
@Composable
fun NavigationScreens() {
    val navControler = rememberNavController()

    // Getting date from the Giphy API
    val apiService = remember { // HTTP requests to the API
        Retrofit.Builder()
            .baseUrl("https://api.giphy.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(GiphyApiService::class.java)
    }
    val repository = remember { // getting data from the Giphy API
        GiphyRepository(apiService)
    }
    val viewModel = remember { // receiving data and storing this data in the form of LiveData
        GifViewModel(repository)
    }


    NavHost(navController = navControler, startDestination = Routes.ChiefScreen.route) {
        composable(Routes.ChiefScreen.route) {
            GifListScreen(viewModel = viewModel, onGifSelected = { gif ->
                if (gif is Gif) {
                    navControler.navigate("${Routes.DetailScreen.route}/${gif.id}")
                }

            })
        }
        composable("${Routes.DetailScreen.route}/{gifId}") { backStackEntry ->
            val gifId = backStackEntry.arguments?.getString("gifId")
            val gif = viewModel.gifs.value?.find { it.id == gifId }
            if (gif != null) {
                GifDetailScreen(gif = gif, onBack = { navControler.popBackStack() })
            }
        }
    }

}