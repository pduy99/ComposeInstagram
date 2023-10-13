package com.helios.composeinstagram.presentation.profile

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountBox
import androidx.compose.material.icons.outlined.GridOn
import androidx.compose.ui.graphics.vector.ImageVector
import com.helios.instagramclone.R

sealed class ProfileTab(@StringRes val title: Int, val icon: ImageVector) {

    object Posts : ProfileTab(title = R.string.posts, Icons.Outlined.GridOn)

    object Tags : ProfileTab(title = R.string.tags, Icons.Outlined.AccountBox)
}
