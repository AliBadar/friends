package com.currencyconverter.friends.domain.user

import com.currencyconverter.friends.domain.exeptions.DuplicateAccountException

class InMemoryUserCatalog(private val usersForgetPassword: MutableMap<String, MutableList<User>> = mutableMapOf()){
    fun createUser(
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

}