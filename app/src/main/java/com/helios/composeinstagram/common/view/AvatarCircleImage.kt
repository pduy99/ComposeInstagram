package com.helios.composeinstagram.common.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage

@Composable
fun AvatarCircleImage(modifier: Modifier = Modifier, imageUrl: String?, onClick: () -> Unit) {
    Card(
        modifier = modifier
            .size(64.dp)
            .padding(8.dp), shape = CircleShape
    ) {
        AsyncImage(
            model = imageUrl,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.clickable { onClick() })
    }
}
