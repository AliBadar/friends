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

        when (regexCredentialsValidator.validate(email, passowd)) {
            CredentialsValidationResult.InvalidEmail ->
                _mutableSignUpState.value = SignUpState.BadEmail

            CredentialsValidationResult.InvalidPassword ->
                _mutableSignUpState.value = SignUpState.BadPassword

            CredentialsValidationResult.Valid ->
                _mutableSignUpState.value = signUp(email, about, passowd)
        }


    }

    private fun signUp(
        email: String,
        about: String,
        passowd: String
    ) = try {
        val user = createUser(email, about, passowd)
        SignUpState.SignedUp(user)
    } catch (_: DuplicateAccountException) {
        SignUpState.DuplicateAccount
    }

    private fun createUser(
        email: String,
        about: String,
        passowd: String
    ): User {

        checkAccountExists(email)

        val userId = createUserIdFor(email)
        val user = User(userId, email, about)
        saveUser(passowd, user)
        return user
    }

    private fun saveUser(
        passowd: String,
        user: User,
    ) {
        usersForgetPassword.getOrPut(passowd, ::mutableListOf).add(user)
    }

    private fun createUserIdFor(email: String): String {
        return email.takeWhile { it != '@' } + "Id"
    }

    private fun checkAccountExists(email: String) {
        if (usersForgetPassword.values.flatten().any { it.email == email }) {
            throw DuplicateAccountException()
        }
    }

    class DuplicateAccountException : Throwable()


    private val usersForgetPassword = mutableMapOf<String, MutableList<User>>()
}
