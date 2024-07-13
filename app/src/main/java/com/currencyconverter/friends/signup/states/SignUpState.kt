package com.currencyconverter.friends.signup.states

sealed class SignUpState {

    object BadEmail: SignUpState()
    object BadPassword : SignUpState()

}
