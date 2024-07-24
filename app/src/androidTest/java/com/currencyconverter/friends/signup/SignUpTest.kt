package com.currencyconverter.friends.signup

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.currencyconverter.friends.MainActivity
import com.currencyconverter.friends.domain.user.InMemoryUserCatalog
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module

class SignUpTest {

    @get:Rule
    val signUpTestRule = createAndroidComposeRule<MainActivity>()

    val inMemoryUserCatalog = InMemoryUserCatalog()

    private val signUpModule = module {
        factory<InMemoryUserCatalog> {
            inMemoryUserCatalog
        }
    }

    @Before
    fun setUp() {
        loadKoinModules(signUpModule)
    }

    @Test
    fun performSignUp(){
        launchSignUpScreen(signUpTestRule) {
            typeEmail("email@test.com")
            typePassword("PassWO2ord!")
            submit()
        } verify {
            timeLineScreenIsPresent()
        }
    }

    @Test
    fun displayDuplicateAccountError() {
        val signedUpUserEmail = "alice@friends.com"
        val signedUpUserPassword = "@l1cePass"

        createUserWith(signedUpUserEmail, signedUpUserPassword)

        launchSignUpScreen(signUpTestRule){
            typeEmail(signedUpUserEmail)
            typePassword(signedUpUserPassword)
            submit()
        } verify {
            duplicateAccountErrorIsShown()
        }
    }

    private fun createUserWith(
        signedUpUserEmail: String,
        signedUpUserPassword: String
    ) {
        inMemoryUserCatalog.createUser(signedUpUserEmail, signedUpUserPassword, "")
    }

    @After
    fun tearDown() {
        val resetModule = module {
            single<InMemoryUserCatalog> {
                InMemoryUserCatalog()
            }
        }

        loadKoinModules(resetModule)
    }
}