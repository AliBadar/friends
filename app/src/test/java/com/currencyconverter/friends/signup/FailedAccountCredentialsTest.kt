package com.currencyconverter.friends.signup

import com.currencyconverter.friends.domain.exeptions.BackEndException
import com.currencyconverter.friends.domain.exeptions.ConnectionUnavailableException
import com.currencyconverter.friends.domain.user.User
import com.currencyconverter.friends.domain.user.UserCatalog
import com.currencyconverter.friends.domain.user.UserRepository
import com.currencyconverter.friends.signup.states.SignUpState
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class FailedAccountCredentialsTest {

    @Test
    fun backEndError() {
        val userRepository = UserRepository(UnavailableUserCatalog())
        val result = userRepository.signUp("email", "password", "abc")
        assertEquals(SignUpState.   BackEndError, result)
    }



    @Test
    fun offlineError() {
        val userRespository = UserRepository(OfflineUserCatalog())
        val result = userRespository.signUp("email", "password", "abc")
        assertEquals(SignUpState.Offline, result)
    }

    class OfflineUserCatalog : UserCatalog {
        override fun createUser(email: String, password: String, about: String): User {
            throw ConnectionUnavailableException()
        }

    }

    class UnavailableUserCatalog : UserCatalog {
        override fun createUser(email: String, password: String, about: String): User {
          throw BackEndException()
        }

    }
}