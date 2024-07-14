package com.currencyconverter.friends.signup

import com.currencyconverter.friends.InstantTaskExecutorExtension
import com.currencyconverter.friends.domain.CredentialsValidationResult
import com.currencyconverter.friends.domain.RegexCredentialsValidator
import com.currencyconverter.friends.domain.user.InMemoryUserCatalog
import com.currencyconverter.friends.domain.user.UserRepository
import com.currencyconverter.friends.signup.states.SignUpState
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

        val viewModel = SignUpViewModel(RegexCredentialsValidator(), UserRepository(
            InMemoryUserCatalog()
        )
        )

        viewModel.createAccount("email", ":password", ":About:")

        assertEquals(SignUpState.BadEmail, viewModel.signUpState.value)

    }


    @ParameterizedTest
    @CsvSource(
        "''",
        "'           '",
        "'12345678'",
        "'abcd5678'",
        "'abcDEF78'",
        "'abcdef78#$'",
        "'ABCDEF78#$'",
    )
    fun invalidPassword(){

        val viewModel = SignUpViewModel(RegexCredentialsValidator(), UserRepository(InMemoryUserCatalog()))


        viewModel.createAccount(
            "anna@gmail.com", "", ""
        )

        assertEquals(SignUpState.BadPassword, viewModel.signUpState.value)
    }

    @Test
    fun validCredentials(){

        val validator = RegexCredentialsValidator()

        val result = validator.validate("john@gmail.com", "12ABcd3!^")

        assertEquals(CredentialsValidationResult.Valid, result)
    }

}