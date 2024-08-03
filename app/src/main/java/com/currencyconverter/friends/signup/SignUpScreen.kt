package com.currencyconverter.friends.signup

import androidx.annotation.StringRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
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
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.currencyconverter.friends.R
import com.currencyconverter.friends.signup.states.SignUpScreenState
import com.currencyconverter.friends.signup.states.SignUpState
import com.currencyconverter.friends.ui.theme.Typography

@Composable
fun SignUpScreen(
    signUpViewModel: SignUpViewModel,
    onSignUp: () -> Unit,
) {

    val signUpState by signUpViewModel.signUpState.observeAsState()
    val coroutineScope = rememberCoroutineScope()

    val screenState by remember {
        mutableStateOf(SignUpScreenState(coroutineScope))
    }




    when (signUpState) {
        is SignUpState.SignedUp -> onSignUp()

        is SignUpState.BadEmail -> screenState.isBadEmail = true

        SignUpState.BadPassword -> screenState.isBadPassword = true

        is SignUpState.DuplicateAccount -> screenState.toggleInfoMessage(R.string.duplicateAccountError)


        is SignUpState.BackEndError -> screenState.toggleInfoMessage(R.string.createAccountError)

        is SignUpState.Offline -> screenState.toggleInfoMessage(R.string.offlineError)
        SignUpState.Loading -> BlockingLoading()
        else -> {}
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

            EmailField(screenState.email, screenState.showBadEmail) { it ->
                screenState.email = it

            }

            Spacer(modifier = Modifier.height(8.dp))

            PasswordField(
                screenState.password,
                screenState.showBadPassword
            ) { screenState.password = it }

            AboutField(
                value = screenState.about,
                onValueChange = { screenState.about = it },
                //onDoneClicked = { with(screenState) { onSignUp(email, password, about) } }
            )
            Spacer(modifier = Modifier.height(8.dp))

            Spacer(modifier = Modifier.height(8.dp))

            Button(modifier = Modifier.fillMaxWidth(), onClick = {

                screenState.resetUiState()
                with(screenState) {
                    signUpViewModel.createAccount(email, password, about)
                }

            }) {
                Text(text = stringResource(id = R.string.signUp))
            }
        }

        InfoMessage(
            screenState.isInfoMessageShowing,
            stringResource = screenState.currentInfoMessage
        )

    }
}

@Composable
fun BlockingLoading() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .testTag(stringResource(id = R.string.loading))
            .background(MaterialTheme.colorScheme.surface.copy(alpha = 0.7f)),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(progress = 100f)
//        CircularProgressIndicator()
    }
}

@Composable
fun InfoMessage(isVisible: Boolean, @StringRes stringResource: Int) {

    AnimatedVisibility(
        visible = isVisible,
        enter = slideInVertically(
            initialOffsetY = { fullHeight -> -fullHeight },
            animationSpec = tween(durationMillis = 150, easing = FastOutLinearInEasing)
        ),
        exit = fadeOut(
            targetAlpha = 0f,
            animationSpec = tween(durationMillis = 250, easing = LinearOutSlowInEasing)
        )
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth(),
            color = MaterialTheme.colorScheme.error,
            tonalElevation = 4.dp
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {

                Text(
                    text = stringResource(id = stringResource),
                    modifier = Modifier.padding(16.dp),
                    color = MaterialTheme.colorScheme.onError
                )
            }
        }
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

    OutlinedTextField(modifier = Modifier
        .fillMaxWidth()
        .testTag(stringResource(id = R.string.password)),
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
    OutlinedTextField(modifier = Modifier
        .fillMaxWidth()
        .testTag(stringResource(id = R.string.email)),
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