package com.currencyconverter.friends.signup

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import com.currencyconverter.friends.R

@Composable
@Preview(device = Devices.NEXUS_5)
fun SignUp() {
    Column(modifier = Modifier.fillMaxSize()) {
        Text(text = "Create an account")

        OutlinedTextField(value = "", onValueChange = {}, label = {
            Text(text = stringResource(id = R.string.email))
        })

        OutlinedTextField(value = "", onValueChange = {}, label = {
            Text(text = stringResource(id = R.string.password))
        })

        Button(onClick = { /*TODO*/ }) {
            Text(text = stringResource(id = R.string.signUp))
        }
    }
}