package com.helios.composeinstagram.presentation.profile

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AddBox
import androidx.compose.material.icons.outlined.ExpandMore
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Gray
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.helios.composeinstagram.common.view.AvatarCircleImage
import com.helios.composeinstagram.common.view.LoadingOverlayBox
import com.helios.composeinstagram.presentation.theme.ComposeInstagramTheme
import com.helios.composeinstagram.presentation.theme.Dimension

@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier,
    onEditProfile: () -> Unit
) {
    val viewModel = hiltViewModel<ProfileViewModel>()
    val uiState by viewModel.uiState.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val user = uiState.userData

    LoadingOverlayBox(modifier = modifier, isLoading = isLoading) {
        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp, start = 8.dp, end = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = user?.username ?: "",
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    fontSize = 20.sp,
                    modifier = Modifier.padding(start = 8.dp)
                )
                Image(imageVector = Icons.Outlined.ExpandMore, contentDescription = null)
                Spacer(modifier = Modifier.weight(1f))
                IconButton(onClick = { }) {
                    Icon(imageVector = Icons.Outlined.AddBox, contentDescription = null)
                }
                IconButton(onClick = { }) {
                    Icon(imageVector = Icons.Outlined.Menu, contentDescription = null)
                }
            }
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(8.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                Row {
                    AvatarCircleImage(
                        imageUrl = user?.imageUrl ?: "",
                        modifier = Modifier.size(90.dp)
                    ) {}

                    StatItemLayout(
                        title = "posts", count = 25,
                        modifier = Modifier
                            .weight(1f)
                            .align(Alignment.CenterVertically),
                    )

                    StatItemLayout(
                        title = "followers", count = 25,
                        modifier = Modifier
                            .weight(1f)
                            .align(Alignment.CenterVertically),
                    )

                    StatItemLayout(
                        title = "following", count = 25,
                        modifier = Modifier
                            .weight(1f)
                            .align(Alignment.CenterVertically),
                    )
                }
                Column(modifier = Modifier.padding(8.dp)) {
                    user?.name?.let { displayName ->
                        Text(
                            text = displayName,
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                        )
                    }
                    user?.bio?.let { bio ->
                        Text(text = bio, style = MaterialTheme.typography.titleSmall)
                    }
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight(), horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    OutlinedButton(
                        onClick = onEditProfile,
                        modifier = Modifier
                            .padding(8.dp)
                            .height(35.dp)
                            .weight(1f),
                        shape = RoundedCornerShape(10),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.LightGray,
                            contentColor = MaterialTheme.colorScheme.onBackground,
                            disabledContainerColor = Gray.copy(alpha = 0.7f),
                            disabledContentColor = MaterialTheme.colorScheme.onBackground,
                        ),
                        border = BorderStroke(0.dp, Color.Transparent)
                    ) {
                        Text(
                            text = "Edit Profile",
                            color = Color.Black,
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                        )
                    }

                    OutlinedButton(
                        onClick = { },
                        modifier = Modifier
                            .padding(8.dp)
                            .height(35.dp)
                            .weight(1f),
                        shape = RoundedCornerShape(10),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.LightGray,
                            contentColor = MaterialTheme.colorScheme.onBackground,
                            disabledContainerColor = Gray.copy(alpha = 0.7f),
                            disabledContentColor = MaterialTheme.colorScheme.onBackground,
                        ),
                        border = BorderStroke(0.dp, Color.Transparent)
                    ) {
                        Text(
                            text = "Share Profile",
                            color = Color.Black,
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                        )
                    }
                }
                Spacer(modifier = Modifier.height(10.dp))
                ProfileTabSection(
                    profileTabs = listOf(ProfileTab.Posts, ProfileTab.Tags),
                    activeProfileTab = uiState.activeTab,
                    onTabClick = {
                        viewModel.updateActiveTab(it)
                    })

                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(8.dp)
                ) {
                    Text(text = "post list")
                }
            }
        }
    }
}

@Composable
fun StatItemLayout(
    title: String,
    count: Int,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "$count",
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodyLarge
        )
        Text(
            text = title,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.titleMedium
        )
    }
}

@Composable
fun ProfileTabSection(
    profileTabs: List<ProfileTab>,
    activeProfileTab: ProfileTab,
    onTabClick: (profileTab: ProfileTab) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        for (tab in profileTabs) {
            TabItemLayout(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 8.dp)
                    .background(Color.Transparent)
                    .clickable {
                        if (tab != activeProfileTab) {
                            onTabClick(tab)
                        }
                    },
                icon = tab.icon,
                active = tab == activeProfileTab
            )
        }
    }
}

@Composable
fun TabItemLayout(modifier: Modifier = Modifier, icon: ImageVector, active: Boolean) {
    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        Icon(
            modifier = Modifier
                .padding(vertical = Dimension.sm)
                .size(Dimension.smIcon),
            imageVector = icon,
            contentDescription = null,
            tint = if (active) MaterialTheme.colorScheme.onBackground else Gray
        )
        if (active) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(MaterialTheme.colorScheme.onBackground)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProfileScreenPreview() {
    ComposeInstagramTheme {
        ProfileScreen {

        }
    }
}
