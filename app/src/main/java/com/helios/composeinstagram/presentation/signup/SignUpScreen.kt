package com.helios.composeinstagram.presentation.signup

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.helios.composeinstagram.common.view.EmailTextField
import com.helios.composeinstagram.common.view.LoadingOverlayBox
import com.helios.composeinstagram.common.view.PasswordTextField
import com.helios.composeinstagram.common.view.UsernameTextField
import com.helios.instagramclone.R

@Composable
fun SignUpScreen(modifier: Modifier = Modifier) {
    val focusManager = LocalFocusManager.current
    val viewModel = hiltViewModel<SignUpViewModel>()
    val uiState by viewModel.uiState.collectAsState()

    LoadingOverlayBox(isLoading = false, modifier = modifier) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .verticalScroll(
                    rememberScrollState()
                )
                .padding(top = 80.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Image(
                painter = painterResource(id = R.drawable.ig_text_logo),
                contentDescription = "logo",
                modifier = Modifier
                    .width(250.dp)
                    .padding(16.dp)
            )

            Spacer(modifier = Modifier.height(25.dp))

            UsernameTextField(
                modifier = Modifier.padding(horizontal = 16.dp),
                usernameState = uiState.usernameState
            )

            EmailTextField(
                modifier = Modifier.padding(horizontal = 16.dp),
                emailState = uiState.emailState
            )

            PasswordTextField(
                modifier = Modifier.padding(horizontal = 16.dp),
                passwordState = uiState.passwordState
            )

            Button(
                onClick = {
                    focusManager.clearFocus(force = true)
                }, modifier = Modifier
                    .padding(top = 16.dp)
                    .width(280.dp),
                shape = RectangleShape
            ) {
                Text(text = "SIGNUP")
            }

            Spacer(modifier = Modifier.weight(4f))

            Column(
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Divider(color = Color.Gray, thickness = 1.dp)
                Row {
                    Text(
                        text = "Already has an account?",
                        color = Color.Gray,
                        modifier = Modifier
                            .padding(top = 16.dp, end = 5.dp)
                    )

                    Text(
                        text = "Login.",
                        color = Color.Blue,
                        modifier = Modifier
                            .padding(top = 16.dp)
                            .clickable { })
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SignUpScreenPreview() {
    SignUpScreen()
}