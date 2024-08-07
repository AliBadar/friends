package com.currencyconverter.friends.signup.states

import androidx.annotation.StringRes
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.currencyconverter.friends.domain.user.Following
import com.currencyconverter.friends.domain.user.InMemoryUserCatalog
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SignUpScreenState(private val coroutineScope: CoroutineScope) {
    val userCatalog = InMemoryUserCatalog(
        followings = listOf(
            Following("saraId", "lucyId"),
            Following("annaId", "lucyId")
        )
    )


    var currentInfoMessage by mutableIntStateOf(0)

    var isInfoMessageShowing by mutableStateOf(false)

    var email by mutableStateOf("")

    var password by mutableStateOf("")

    var about by mutableStateOf("")

    var isBadEmail by mutableStateOf(false)

    var isBadPassword by mutableStateOf(false)

    var isLoading by mutableStateOf(false)

    private var lastSubmittedEmail by mutableStateOf("")
    private var lastSubmittedPassword by mutableStateOf("")

    val showBadEmail : Boolean
        get() = isBadEmail && lastSubmittedEmail == email

    val showBadPassword : Boolean
        get() = isBadPassword && lastSubmittedPassword == password

    fun toggleInfoMessage(@StringRes message: Int) = coroutineScope.launch {
        isLoading = false
        if (currentInfoMessage != message) {
            currentInfoMessage = message
            if (!isInfoMessageShowing) {
                isInfoMessageShowing = true
                delay(1500)
                isInfoMessageShowing = false
            }
        }
    }

    fun toggleLoading() {
        isLoading = true
    }

    fun showBadEmail() {
        isBadEmail = true
    }

    fun showBadPassword() {
        isBadPassword = true
    }

    fun resetUiState() {
        currentInfoMessage = 0
        lastSubmittedEmail = email
        lastSubmittedPassword = password
        isInfoMessageShowing = false
        isBadEmail = false
        isBadPassword = false
        isLoading = false
    }

}