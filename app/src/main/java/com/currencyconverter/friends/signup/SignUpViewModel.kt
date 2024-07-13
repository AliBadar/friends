package com.currencyconverter.friends.signup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.currencyconverter.friends.signup.states.SignUpState
import java.util.regex.Pattern

class SignUpViewModel {
    private var _mutableSignUpState = MutableLiveData<SignUpState>()
    var signUpState: LiveData<SignUpState> = _mutableSignUpState


    fun createAccount(email: String, passowd: String, about: String) {

        val emailRegex =
            """[a-zA-Z0-9+._%\-]{1,64}@[a-zA-Z0-9][a-zA-Z0-9\-]{1,64}(\.[a-zA-Z0-9][a-zA-Z0-9\-]{1,25})"""

        val emailPattern = Pattern.compile(emailRegex)
        if (!emailPattern.matcher(email).matches()){
            _mutableSignUpState.value = SignUpState.BadEmail
        }else if (passowd.isEmpty()){
            _mutableSignUpState.value = SignUpState.BadPassword
        }


    }

}
