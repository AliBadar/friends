package com.currencyconverter.friends.signup

import com.currencyconverter.friends.InstantTaskExecutorExtension
import com.currencyconverter.friends.signup.states.SignUpState
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(InstantTaskExecutorExtension::class)
class CredentialsValidationTest {
    @Test
    fun inValidEmail() {

        val viewModel = SignUpViewModel()

        viewModel.createAccount("email", ":password", ":About:")

        Assertions.assertEquals(SignUpState.BadEmail, viewModel.signUpState.value)

    }




}