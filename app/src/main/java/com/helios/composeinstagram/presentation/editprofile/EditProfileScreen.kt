package com.helios.composeinstagram.presentation.editprofile

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.Done
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.helios.composeinstagram.common.view.AvatarCircleImage
import com.helios.composeinstagram.common.view.DropDownMenu
import com.helios.composeinstagram.common.view.LoadingOverlayBox
import com.helios.composeinstagram.presentation.theme.SkyBlue

@Composable
fun EditProfileScreen(modifier: Modifier = Modifier) {

    val viewModel = hiltViewModel<EditProfileViewModel>()
    val uiState by viewModel.uiState.collectAsState()
    val errorEvent by viewModel.errorEvent.collectAsState()
    val user = uiState.userData
    val focuser = LocalFocusManager.current

    LoadingOverlayBox(isLoading = false, modifier = modifier) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = {}) {
                    Icon(
                        imageVector = Icons.Outlined.Close,
                        contentDescription = null,
                        modifier = Modifier.size(30.dp)
                    )
                }
                Text(
                    text = "Edit Profile",
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                )
                Spacer(modifier = Modifier.weight(1f))
                IconButton(onClick = {
                    focuser.clearFocus(true)
                }) {
                    Icon(
                        imageVector = Icons.Outlined.Done,
                        contentDescription = null,
                        modifier = Modifier.size(30.dp),
                        tint = SkyBlue
                    )
                }
            }
            AvatarCircleImage(
                imageUrl = user?.imageUrl ?: "", modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .size(90.dp)
            ) {

            }
            Text(
                text = "Edit avatar",
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .clickable {

                    },
                color = SkyBlue,
                style = MaterialTheme.typography.labelMedium
            )
            Spacer(modifier = Modifier.padding(vertical = 8.dp))

            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                value = user?.name ?: "",
                textStyle = MaterialTheme.typography.bodySmall,
                onValueChange = { viewModel.onNameChanged(it) },
                maxLines = 1,
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent
                ),
                label = {
                    Text(text = "Name", style = MaterialTheme.typography.bodySmall)
                })

            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                value = user?.username ?: "",
                textStyle = MaterialTheme.typography.bodySmall,
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent
                ),
                onValueChange = { viewModel.onUsernameChanged(it) },
                maxLines = 1,
                label = {
                    Text(text = "Username", style = MaterialTheme.typography.bodySmall)
                })
            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent
                ),
                value = user?.bio ?: "",
                textStyle = MaterialTheme.typography.bodySmall,
                onValueChange = { viewModel.onBioChanged(it) },
                label = {
                    Text(text = "Bio", style = MaterialTheme.typography.bodySmall)
                })
            DropDownMenu(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                items = uiState.dropDownItems,
                title = user?.gender ?: "Gender",
                onValueChange = {
                    viewModel.onGenderChanged(it)
                })
        }
    }

    errorEvent.getContentOrNull()?.let {
        Toast.makeText(LocalContext.current, it, Toast.LENGTH_SHORT).show()
    }
}
