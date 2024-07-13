package com.currencyconverter.friends.signup

import com.currencyconverter.friends.InstantTaskExecutorExtension
import com.currencyconverter.friends.signup.states.SignUpState
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

@ExtendWith(InstantTaskExecutorExtension::class)
class CredentialsValidationTest {


    @ParameterizedTest
    @CsvSource(
        "'email'",
        "'a@b.c'",
        "'ab@b.c'",
        "'ab@bc.c'",
        "''",
        "'     '",
    )
    fun inValidEmail(email: String) {

        val viewModel = SignUpViewModel()

        viewModel.createAccount("email", ":password", ":About:")

        assertEquals(SignUpState.BadEmail, viewModel.signUpState.value)

    }


    @Test
    fun invalidPassword(){

        val viewModel = SignUpViewModel()


        viewModel.createAccount(
            "anna@gmail.com", "", ""
        )

        assertEquals(SignUpState.BadPassword, viewModel.signUpState.value)
    }

}