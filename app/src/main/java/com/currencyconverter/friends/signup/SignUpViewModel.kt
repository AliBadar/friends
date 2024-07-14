package com.currencyconverter.friends.signup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.currencyconverter.friends.domain.CredentialsValidationResult
import com.currencyconverter.friends.domain.RegexCredentialsValidator
import com.currencyconverter.friends.domain.user.InMemoryUserCatalog
import com.currencyconverter.friends.domain.user.UserRepository
import com.currencyconverter.friends.signup.states.SignUpState

class SignUpViewModel(private val regexCredentialsValidator: RegexCredentialsValidator) {
    private var _mutableSignUpState = MutableLiveData<SignUpState>()
    var signUpState: LiveData<SignUpState> = _mutableSignUpState


    fun createAccount(email: String, passowd: String, about: String) {

        when (regexCredentialsValidator.validate(email, passowd)) {
            CredentialsValidationResult.InvalidEmail ->
                _mutableSignUpState.value = SignUpState.BadEmail

            CredentialsValidationResult.InvalidPassword ->
                _mutableSignUpState.value = SignUpState.BadPassword

            CredentialsValidationResult.Valid ->
                _mutableSignUpState.value = userRepository.signUp(email, about, passowd)
        }
    }

    private val userRepository = UserRepository(InMemoryUserCatalog())

}
