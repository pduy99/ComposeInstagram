package com.helios.composeinstagram.presentation.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.helios.composeinstagram.navigation.bottomnavigation.BottomNavigationItem
import com.helios.composeinstagram.navigation.bottomnavigation.BottomNavigationMenu
import com.helios.composeinstagram.presentation.profile.ProfileScreen

@Composable
fun HomeScreen(modifier: Modifier = Modifier) {

    val viewModel = hiltViewModel<HomeViewModel>()
    val uiState by viewModel.uiState.collectAsState()

    Column(modifier = modifier.fillMaxSize()) {
        Box(modifier = Modifier.weight(1f)) {
            when (uiState.selectedBottomNavigationItem) {
                BottomNavigationItem.FEED -> {

                }

                BottomNavigationItem.PROFILE -> {
                    ProfileScreen {

                    }
                }

                BottomNavigationItem.SEARCH -> {

                }

                else -> {}
            }
        }

        BottomNavigationMenu(
            selectedItem = uiState.selectedBottomNavigationItem,
            onTabPressed = {
                viewModel.onTabPressed(it)
            })
    }
}
