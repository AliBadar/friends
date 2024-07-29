package com.currencyconverter.friends.signup

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.currencyconverter.friends.R
import com.currencyconverter.friends.domain.RegexCredentialsValidator
import com.currencyconverter.friends.domain.user.InMemoryUserCatalog
import com.currencyconverter.friends.domain.user.UserRepository
import com.currencyconverter.friends.signup.states.SignUpState
import com.currencyconverter.friends.ui.theme.Typography

@Composable
fun SignUpScreen(
    signUpViewModel: SignUpViewModel,
    onSignUp: () -> Unit,
) {

    val regexCredentialsValidator = RegexCredentialsValidator()
    val userCatalog = InMemoryUserCatalog()
    val userRepository = UserRepository(userCatalog)

    val signUpState by signUpViewModel.signUpState.observeAsState()

    var email by remember {
        mutableStateOf("")
    }

    var password by remember {
        mutableStateOf("")
    }

    var about by remember {
        mutableStateOf("")
    }

    var isBadEmail by remember {
        mutableStateOf(false)
    }

    var isBadPassword by remember {
        mutableStateOf(false)
    }

    if (signUpState is SignUpState.SignedUp) {
        onSignUp()
    } else if (signUpState is SignUpState.BadEmail){
        isBadEmail = true
    } else if (signUpState == SignUpState.BadPassword) {
        isBadPassword = true
    } else if (signUpState is SignUpState.DuplicateAccount){
        InfoMessage(stringResource = R.string.duplicateAccountError)
    } else if (signUpState is SignUpState.BackEndError){
        InfoMessage(stringResource = R.string.createAccountError)
    } else if (signUpState is SignUpState.Offline){
        InfoMessage(stringResource = R.string.offlineError)
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    16.dp
                )
        ) {

            ScreenTitle(R.string.createaccount)

            Spacer(modifier = Modifier.height(16.dp))

            EmailField(email, isBadEmail) { it ->
                email = it

            }

            Spacer(modifier = Modifier.height(8.dp))

            PasswordField(password, isBadPassword) { password = it }

            AboutField(
                value = about,
                onValueChange = { about = it },
                //onDoneClicked = { with(screenState) { onSignUp(email, password, about) } }
            )
            Spacer(modifier = Modifier.height(8.dp))

            Spacer(modifier = Modifier.height(8.dp))

            Button(modifier = Modifier.fillMaxWidth(), onClick = {

                signUpViewModel.createAccount(email, password, about)

            }) {
                Text(text = stringResource(id = R.string.signUp))
            }
        }


    }
}

@Composable
fun InfoMessage(@StringRes stringResource: Int) {

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.error)
    ) {
        Text(text = stringResource(id = stringResource))
    }
}

@Composable
private fun PasswordField(
    value: String,
    isError: Boolean,
    onValueChange: (String) -> Unit,
) {


    var isVisible by remember {
        mutableStateOf(false)
    }

    val visualTransformation =
        if (isVisible) VisualTransformation.None else PasswordVisualTransformation()

    OutlinedTextField(modifier = Modifier.fillMaxWidth(),
        value = value,
        isError = isError,
        onValueChange = onValueChange,
        trailingIcon = {
            VisibilityToggle(isVisible) {
                isVisible = !isVisible
            }

        },
        visualTransformation = visualTransformation,
        label = {
            val resource = if (isError) R.string.badPasswordError else R.string.password
            Text(text = stringResource(id = resource))
        })
}

@Composable
private fun VisibilityToggle(isVisible: Boolean, onToggle: () -> Unit) {

    IconButton(onClick = {
        onToggle()
    }) {

        val resource = if (isVisible) R.drawable.ic_visible else R.drawable.ic_invisible

        Icon(
            painter = painterResource(id = resource),
            contentDescription = stringResource(
                id = R.string.toggleVisibility
            )
        )
    }
}

@Composable
private fun EmailField(
    value: String,
    isError: Boolean,
    onValueChange: (String) -> Unit,
) {
    OutlinedTextField(modifier = Modifier.fillMaxWidth(),
        value = value,
        isError = isError,
        onValueChange = onValueChange,
        label = {
            val resource = if (isError) R.string.badEmailError else R.string.email
            Text(text = stringResource(id = resource))
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

@Composable
fun AboutField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
) {
    OutlinedTextField(
        modifier = modifier.fillMaxWidth(),
        value = value,
        label = {
            Text(text = stringResource(id = R.string.about))
        },
        onValueChange = onValueChange,
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Done
        ),
        //keyboardActions = KeyboardActions(onDone = { onDoneClicked() })
    )
}