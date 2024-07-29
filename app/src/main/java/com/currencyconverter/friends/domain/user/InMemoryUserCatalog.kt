package com.currencyconverter.friends.domain.user

import com.currencyconverter.friends.domain.exeptions.DuplicateAccountException

class InMemoryUserCatalog(private val usersForgetPassword: MutableMap<String, MutableList<User>> = mutableMapOf()) :
    UserCatalog {
    override fun createUser(
        email: String,
        password: String,
        about: String
    ): User {

        checkAccountExists(email)

        val userId = createUserIdFor(email)
        val user = User(userId, email, about)
        saveUser(password, user)
        return user
    }

    private fun checkAccountExists(email: String) {
        if (usersForgetPassword.values.flatten().any { it.email == email }) {
            throw DuplicateAccountException()
        }
    }

    private fun createUserIdFor(email: String): String {
        return email.takeWhile { it != '@' } + "Id"
    }

    private fun saveUser(
        passowd: String,
        user: User,
    ) {
        usersForgetPassword.getOrPut(passowd, ::mutableListOf).add(user)
    }

}