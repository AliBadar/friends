package com.currencyconverter.friends.infrastructure.builder

import com.currencyconverter.friends.domain.user.User
import java.util.UUID

class UserBuilder {


    private var userId: String = UUID.randomUUID().toString()
    private var userEmail = "user@friends.com"
    private var aboutUser = "About User"


    companion object {
        fun aUser(): UserBuilder {
            return UserBuilder()
        }
    }

    fun withId(id: String): UserBuilder = this.apply {
        userId = id
    }

    fun build(): User {
        return User(userId, userEmail, aboutUser)
    }
}