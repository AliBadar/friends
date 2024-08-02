package com.currencyconverter.friends.signup

import com.currencyconverter.friends.InstantTaskExecutorExtension
import com.currencyconverter.friends.app.TestDispatchers
import com.currencyconverter.friends.domain.RegexCredentialsValidator
import com.currencyconverter.friends.domain.user.InMemoryUserCatalog
import com.currencyconverter.friends.domain.user.User
import com.currencyconverter.friends.domain.user.UserRepository
import com.currencyconverter.friends.signup.states.SignUpState
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(InstantTaskExecutorExtension::class)
class RenderingSignUpStatesTest {

    private val userRepository = UserRepository(InMemoryUserCatalog())
    private val viewModel = SignUpViewModel(RegexCredentialsValidator(), userRepository, TestDispatchers())
    private val tom = User("tomId", "tom@friends.com", "about Tom")


    @Test
    fun uiStatesAreDeliveredInParticularOrder() {

        val deliveredStates = mutableListOf<SignUpState>()

        viewModel.signUpState.observeForever {
            deliveredStates.add(it)
        }

        viewModel.createAccount(tom.email, "P@assiw223", tom.about)

        assertEquals(
            listOf(SignUpState.Loading,
                SignUpState.SignedUp(tom)),
            deliveredStates
        )
    }
}