package com.example.gifosaurus.ui.screens.navigation

sealed class Routes(val route: String) {
    object ChiefScreen : Routes("chief_screen")
    object DetailScreen : Routes("detail_screen")
}