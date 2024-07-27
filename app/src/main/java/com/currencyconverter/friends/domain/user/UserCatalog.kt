package com.currencyconverter.friends.domain.user

interface UserCatalog {
    fun createUser(
        email: String,
        about: String,
        passowd: String,
    ): User
}