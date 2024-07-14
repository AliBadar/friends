package com.currencyconverter.friends.domain.user

import com.currencyconverter.friends.domain.exeptions.DuplicateAccountException
import com.currencyconverter.friends.signup.states.SignUpState

class UserRepository(private val userCatalog: InMemoryUserCatalog) {

    fun signUp(
        email: String,
        about: String,
        passowd: String
    ) = try {
        val user = userCatalog.createUser(email, about, passowd)
        SignUpState.SignedUp(user)
    } catch (_: DuplicateAccountException) {
        SignUpState.DuplicateAccount
    }
}