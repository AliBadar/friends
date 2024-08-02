package com.currencyconverter.friends.signup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.currencyconverter.friends.app.CoroutinesDispatchers
import com.currencyconverter.friends.domain.CredentialsValidationResult
import com.currencyconverter.friends.domain.RegexCredentialsValidator
import com.currencyconverter.friends.domain.user.UserRepository
import com.currencyconverter.friends.signup.states.SignUpState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SignUpViewModel(
    private val credentialsValidator: RegexCredentialsValidator,
    private val userRepository: UserRepository,
    private val dispatchers: CoroutinesDispatchers,
): ViewModel() {
    private var mutableSignUpState = MutableLiveData<SignUpState>()
    var signUpState: LiveData<SignUpState> = mutableSignUpState


    fun createAccount(email: String, passowd: String, about: String) {

        when (credentialsValidator.validate(email, passowd)) {
            CredentialsValidationResult.InvalidEmail ->
                mutableSignUpState.value = SignUpState.BadEmail

            CredentialsValidationResult.InvalidPassword ->
                mutableSignUpState.value = SignUpState.BadPassword

            CredentialsValidationResult.Valid ->
                proceedWithSignUp(email, about, passowd)
        }
    }


    private fun proceedWithSignUp(email: String, about: String, passowd: String) {
        viewModelScope.launch {
            mutableSignUpState.value = SignUpState.Loading
            val result = withContext(dispatchers.backGround){
                userRepository.signUp(email, about, passowd)
            }

            mutableSignUpState.value = result
        }
    }

}
