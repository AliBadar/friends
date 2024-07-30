package com.currencyconverter.friends.signup

import androidx.annotation.StringRes
import androidx.compose.runtime.mutableStateOf
import com.currencyconverter.friends.domain.user.InMemoryUserCatalog
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SignUpScreenState(private val coroutineScope: CoroutineScope) {
    val userCatalog = InMemoryUserCatalog()


    var currentInfoMessage by mutableStateOf(0)

    var isInfoMessageShowing by mutableStateOf(false)

    var email by mutableStateOf("")

    var password by mutableStateOf("")

    var about by mutableStateOf("")

    var isBadEmail by mutableStateOf(false)

    var isBadPassword by mutableStateOf(false)

    fun toggleInfoMessage(@StringRes message: Int) = coroutineScope.launch {
        if (currentInfoMessage != message) {
            currentInfoMessage = message
            if (!isInfoMessageShowing) {
                isInfoMessageShowing = true
                delay(1500)
                isInfoMessageShowing = false
            }
        }
    }

    fun resetUiState() {
        currentInfoMessage = 0
        isInfoMessageShowing = false
    }

}