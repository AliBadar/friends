package com.currencyconverter.friends.signup

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
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

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
        ) {
            Text(
                text = stringResource(id = R.string.createaccount),
                style = Typography.headlineMedium
            )
        }

        Spacer(modifier = Modifier.height(16.dp))


        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = "",
            onValueChange = {},
            label = {
                Text(text = stringResource(id = R.string.email))
            })

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = "",
            onValueChange = {},
            label = {
                Text(text = stringResource(id = R.string.password))
            })

        Spacer(modifier = Modifier.height(8.dp))

        Button(modifier = Modifier.fillMaxWidth(), onClick = { /*TODO*/ }) {
            Text(text = stringResource(id = R.string.signUp))
        }
    }
}