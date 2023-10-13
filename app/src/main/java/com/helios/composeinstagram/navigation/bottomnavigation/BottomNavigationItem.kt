package com.helios.composeinstagram.navigation.bottomnavigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.AddBox
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.AddBox
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

enum class BottomNavigationItem(val selectedIcon: ImageVector, val unselectedIcon: ImageVector) {
    FEED(
        selectedIcon = Icons.Filled.Home,
        unselectedIcon = Icons.Outlined.Home
    ),
    SEARCH(
        selectedIcon = Icons.Filled.Search,
        unselectedIcon = Icons.Outlined.Search
    ),
    POSTS(
        selectedIcon = Icons.Filled.AddBox,
        unselectedIcon = Icons.Outlined.AddBox
    ),
    NOTIFICATIONS(
        selectedIcon = Icons.Filled.Favorite,
        unselectedIcon = Icons.Outlined.FavoriteBorder
    ),
    PROFILE(
        selectedIcon = Icons.Filled.AccountCircle,
        unselectedIcon = Icons.Outlined.AccountCircle
    ),
}

@Composable
fun BottomNavigationMenu(
    modifier: Modifier = Modifier,
    selectedItem: BottomNavigationItem,
    onTabPressed: (destination: BottomNavigationItem) -> Unit
) {
    Surface(modifier = modifier, shadowElevation = 3.dp) {
        NavigationBar(containerColor = MaterialTheme.colorScheme.background, modifier = Modifier.height(50.dp)) {
            for (item in BottomNavigationItem.values()) {
                NavigationBarItem(
                    modifier = Modifier.background(Color.Transparent),
                    selected = selectedItem == item,
                    onClick = { onTabPressed(item) },
                    icon = {
                        if (selectedItem == item) {
                            Icon(
                                imageVector = item.selectedIcon,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.onBackground
                            )
                        } else {
                            Icon(
                                imageVector = item.unselectedIcon,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f)
                            )
                        }
                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = Color.Black,
                        unselectedIconColor = Color.Gray,
                        indicatorColor = MaterialTheme.colorScheme.background
                    )
                )
            }
        }
    }

}
