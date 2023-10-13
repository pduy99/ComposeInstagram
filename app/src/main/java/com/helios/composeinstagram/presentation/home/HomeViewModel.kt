package com.helios.composeinstagram.presentation.home

import com.helios.composeinstagram.common.view.BaseViewModel
import com.helios.composeinstagram.navigation.bottomnavigation.BottomNavigationItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor() : BaseViewModel() {

    private val _uiState: MutableStateFlow<HomeViewState> = MutableStateFlow(HomeViewState())
    val uiState: StateFlow<HomeViewState> = _uiState.asStateFlow()

    fun onTabPressed(selectedItem: BottomNavigationItem) {
        _uiState.value = _uiState.value.copy(selectedBottomNavigationItem = selectedItem)
    }
}

data class HomeViewState(
    var selectedBottomNavigationItem: BottomNavigationItem = BottomNavigationItem.FEED
)
