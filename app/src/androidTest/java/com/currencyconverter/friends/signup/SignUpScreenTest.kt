package com.currencyconverter.friends.signup

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.currencyconverter.friends.MainActivity
import com.currencyconverter.friends.domain.exeptions.BackEndException
import com.currencyconverter.friends.domain.exeptions.ConnectionUnavailableException
import com.currencyconverter.friends.domain.user.InMemoryUserCatalog
import com.currencyconverter.friends.domain.user.User
import com.currencyconverter.friends.domain.user.UserCatalog
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module

class SignUpScreenTest {

    @get:Rule
    val signUpTestRule = createAndroidComposeRule<MainActivity>()

    val inMemoryUserCatalog = InMemoryUserCatalog()

    private val signUpModule = module {
        factory<UserCatalog> {
            inMemoryUserCatalog
        }
    }

    @Before
    fun setUp() {
        loadKoinModules(signUpModule)
    }

    @Test
    fun performSignUp() {
        launchSignUpScreen(signUpTestRule) {
            typeEmail("email@test.com")
            typePassword("PassWO2ord!")
            submit()
        } verify {
            timeLineScreenIsPresent()
        }
    }

    @Test
    fun displayBadEmailError() {


        launchSignUpScreen(signUpTestRule) {
            typeEmail("email")
            submit()
        } verify {
            badEmailErrorIsShown()
        }
    }

    @Test
    fun displayDuplicateAccountError() {


        val signedUpUserEmail = "alice@friends.com"
        val signedUpUserPassword = "@l1cePass"

        createUserWith(signedUpUserEmail, signedUpUserPassword)

        launchSignUpScreen(signUpTestRule) {
            typeEmail(signedUpUserEmail)
            typePassword(signedUpUserPassword)
            submit()
        } verify {
            duplicateAccountErrorIsShown()
        }
    }

    @Test
    fun displayBackendError() {

        replaceCatlogWith(UnavailableUserCatalog())

        val signedUpUserEmail = "alice@friends.com"
        val signedUpUserPassword = "@l1cePass"

        launchSignUpScreen(signUpTestRule) {
            typeEmail(signedUpUserEmail)
            typePassword(signedUpUserPassword)
            submit()
        } verify {
            backEndErrorIsShown()
        }
    }

    @Test
    fun displayOfflineError() {

        replaceCatlogWith(OfflineUserCatalog())

        val signedUpUserEmail = "alice@friends.com"
        val signedUpUserPassword = "@l1cePass"

        launchSignUpScreen(signUpTestRule) {
            typeEmail(signedUpUserEmail)
            typePassword(signedUpUserPassword)
            submit()
        } verify {
            offlineErrorIsShown()
        }
    }

    private fun replaceCatlogWith(offlineUserCatalog: UserCatalog) {
        val replaceModule = module {
            factory {
                offlineUserCatalog
            }
        }
        loadKoinModules(replaceModule)
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

    class OfflineUserCatalog : UserCatalog {
        override fun createUser(email: String, about: String, passowd: String): User {
            throw ConnectionUnavailableException()
        }

    }

    class UnavailableUserCatalog : UserCatalog {
        override fun createUser(email: String, about: String, passowd: String): User {
            throw BackEndException()
        }

    }

    private fun createUserWith(
        signedUpUserEmail: String,
        signedUpUserPassword: String,
    ) {
        inMemoryUserCatalog.createUser(signedUpUserEmail, signedUpUserPassword, "")
    }
}