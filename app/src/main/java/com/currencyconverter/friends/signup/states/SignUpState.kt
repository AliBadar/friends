package com.currencyconverter.friends.signup.states

import com.currencyconverter.friends.domain.user.User

sealed class SignUpState {
    data class SignedUp(val user: User) : SignUpState()

    object BadEmail: SignUpState()
    object BadPassword : SignUpState()

}
