package com.currencyconverter.friends.signup

import com.currencyconverter.friends.InstantTaskExecutorExtension
import com.currencyconverter.friends.domain.RegexCredentialsValidator
import com.currencyconverter.friends.domain.user.InMemoryUserCatalog
import com.currencyconverter.friends.domain.user.User
import com.currencyconverter.friends.domain.user.UserRepository
import com.currencyconverter.friends.signup.states.SignUpState
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(InstantTaskExecutorExtension::class)
class CreateAnAccountTest {

    @Test
    fun accountCreated() {
        val viewModel = SignUpViewModel(
            RegexCredentialsValidator(), UserRepository(InMemoryUserCatalog())
        )

        val maya = User("mayaId", "maya@friends.com", "About maya")
        viewModel.createAccount(maya.email, "12ABcd3!^", maya.about)


        assertEquals(SignUpState.SignedUp(maya), viewModel.signUpState.value)
    }

    @Test
    fun anotherAccountCreated() {
        val bob = User("bobId", "bob@friends.com", "about Bob")
        val viewModel = SignUpViewModel(RegexCredentialsValidator(), UserRepository(InMemoryUserCatalog()))
        viewModel.createAccount(bob.email, "Ple@seSubscribe1", bob.about)

        assertEquals(SignUpState.SignedUp(bob), viewModel.signUpState.value)
    }

    @Test
    fun createDuplicateAccount() {

        val user = User("annaId", "anna@friends.com", "about Anna")

        val viewModel = SignUpViewModel(RegexCredentialsValidator(), UserRepository(InMemoryUserCatalog())).also {
            it.createAccount(user.email, "12ABcd3!^", user.about)
        }

        viewModel.createAccount(user.email, "12ABcd3!^", user.about)
        assertEquals(SignUpState.DuplicateAccount, viewModel.signUpState.value)
    }
}