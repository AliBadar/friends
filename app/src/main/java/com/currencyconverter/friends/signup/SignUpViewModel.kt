package com.currencyconverter.friends.signup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.currencyconverter.friends.signup.states.SignUpState

class SignUpViewModel {
    private var _mutableSignUpState = MutableLiveData<SignUpState>()
    var signUpState: LiveData<SignUpState> = _mutableSignUpState


    fun createAccount(email: String, passowd: String, about: String) {
        _mutableSignUpState.value = SignUpState.BadEmail
    }

}
