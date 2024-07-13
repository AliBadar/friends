package com.currencyconverter.friends.signup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.currencyconverter.friends.domain.CredentialsValidationResult
import com.currencyconverter.friends.domain.RegexCredentialsValidator
import com.currencyconverter.friends.domain.user.User
import com.currencyconverter.friends.signup.states.SignUpState
import java.util.regex.Pattern

class SignUpViewModel(private val regexCredentialsValidator: RegexCredentialsValidator) {
    private var _mutableSignUpState = MutableLiveData<SignUpState>()
    var signUpState: LiveData<SignUpState> = _mutableSignUpState


    fun createAccount(email: String, passowd: String, about: String) {

        val state = when(regexCredentialsValidator.validate(email, passowd)){
            CredentialsValidationResult.InvalidEmail -> {
                SignUpState.BadEmail
            }
            CredentialsValidationResult.InvalidPassword -> {
                SignUpState.BadPassword
            }
            CredentialsValidationResult.Valid -> {
                val user = User("mayaID", "maya@friends.com", "About maya")
                SignUpState.SignedUp(user)
            }
        }

        _mutableSignUpState.value = state


    }


}
