package com.helios.composeinstagram.presentation.signin

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.helios.composeinstagram.common.view.EmailTextField
import com.helios.composeinstagram.common.view.LoadingOverlayBox
import com.helios.composeinstagram.common.view.PasswordTextField
import com.helios.instagramclone.R

@Composable
fun SignInScreen(
    modifier: Modifier = Modifier,
) {

    val focusManager = LocalFocusManager.current

    LoadingOverlayBox(modifier = modifier, isLoading = false) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .verticalScroll(
                    rememberScrollState()
                )
                .padding(top = 80.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.ig_text_logo),
                contentDescription = "logo",
                modifier = Modifier
                    .width(250.dp)
                    .padding(top = 16.dp)
                    .padding(8.dp)
            )

            EmailTextField(modifier = Modifier.padding(horizontal = 16.dp), enabledShowError = false)

            PasswordTextField(modifier = Modifier.padding(horizontal = 16.dp), enableShowError = false)

            Button(
                onClick = {
                    focusManager.clearFocus(force = true)
                }, modifier = Modifier
                    .padding(16.dp)
                    .width(280.dp), shape = RectangleShape
            ) {
                Text(text = "LOGIN")
            }

            Spacer(modifier = Modifier.weight(1f))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Divider(color = Color.Gray, thickness = 1.dp)
                Row(modifier = Modifier.padding(vertical = 16.dp)) {
                    Text(
                        text = "Don't have an account?",
                        color = Color.Gray,
                        modifier = Modifier
                            .padding(end = 5.dp)
                    )

                    Text(
                        text = "Sign Up.",
                        color = Color.Blue,
                        modifier = Modifier
                            .clickable {
                            })
                }
            }
        }
    }
}
