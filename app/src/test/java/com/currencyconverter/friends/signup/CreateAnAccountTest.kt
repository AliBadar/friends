package com.currencyconverter.friends.signup

import com.currencyconverter.friends.InstantTaskExecutorExtension
import com.currencyconverter.friends.domain.RegexCredentialsValidator
import com.currencyconverter.friends.domain.user.User
import com.currencyconverter.friends.signup.states.SignUpState
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(InstantTaskExecutorExtension::class)
class CreateAnAccountTest {

    @Test
    fun accountCreated() {
        val viewModel = SignUpViewModel(RegexCredentialsValidator())

        viewModel.createAccount("maya@friends.com", "12ABcd3!^", "")

        val maya = User("mayaID", "maya@friends.com", "About maya")

        assertEquals(SignUpState.SignedUp(maya),viewModel.signUpState.value)
    }
}