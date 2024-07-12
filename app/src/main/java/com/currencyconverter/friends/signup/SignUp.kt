package com.currencyconverter.friends.signup

import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.currencyconverter.friends.R
import com.currencyconverter.friends.ui.theme.Typography

@Composable
@Preview(device = Devices.NEXUS_5)
fun SignUp() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                16.dp
            )
    ) {

        ScreenTitle(R.string.createaccount)

        Spacer(modifier = Modifier.height(16.dp))

        var email by remember {
            mutableStateOf("")
        }

        var password by remember {
            mutableStateOf("")
        }

        EmailField(email) { it ->
            email = it

        }

        Spacer(modifier = Modifier.height(8.dp))

        PasswordField(password) { password = it }

        Spacer(modifier = Modifier.height(8.dp))

        Button(modifier = Modifier.fillMaxWidth(), onClick = { /*TODO*/ }) {
            Text(text = stringResource(id = R.string.signUp))
        }
    }
}

@Composable
private fun PasswordField(
    value: String,
    onValueChange: (String) -> Unit
) {

    var isVisible by remember {
        mutableStateOf(false)
    }

    OutlinedTextField(modifier = Modifier.fillMaxWidth(),
        value = value,
        onValueChange = onValueChange,
        trailingIcon = {
            IconButton(onClick = {
                isVisible = !isVisible
            }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_visible),
                    contentDescription = stringResource(
                        id = R.string.toggleVisibility
                    )
                )
            }

        },
        visualTransformation = if (isVisible)   VisualTransformation.None else PasswordVisualTransformation(),
        label = {
            Text(text = stringResource(id = R.string.password))
        })
}

@Composable
private fun EmailField(
    value: String,
    onValueChange: (String) -> Unit
) {
    OutlinedTextField(modifier = Modifier.fillMaxWidth(),
        value = value,
        onValueChange = onValueChange,
        label = {
            Text(text = stringResource(id = R.string.email))
        })
}

@Composable
private fun ScreenTitle(@StringRes resource: Int) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
    ) {
        Text(
            text = stringResource(id = resource), style = Typography.headlineMedium
        )
    }
}