package com.currencyconverter.friends.signup

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.currencyconverter.friends.MainActivity
import com.currencyconverter.friends.domain.exeptions.BackEndException
import com.currencyconverter.friends.domain.exeptions.ConnectionUnavailableException
import com.currencyconverter.friends.domain.user.InMemoryUserCatalog
import com.currencyconverter.friends.domain.user.User
import com.currencyconverter.friends.domain.user.UserCatalog
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module

 class SignUpScreenTest {

    @get:Rule
    val signUpTestRule = createAndroidComposeRule<MainActivity>()

    private val signUpModule = module {
        factory<UserCatalog> {
            InMemoryUserCatalog()
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
    fun displayBadPasswordError() {
        launchSignUpScreen(signUpTestRule) {
            typeEmail("email@friends.com")
            typePassword("ads")
            submit()
            typePassword("newTry")
        } verify {
            badPasswordErrorIsNotShown()
        }
    }

    @Test
    fun resetBadEmailError() {
        launchSignUpScreen(signUpTestRule) {
            typeEmail("email")
            submit()
            typeEmail("email@")
        } verify {
            badEmailErrorIsNotShown()
        }
    }

    @Test
    fun displayBadPassword() {
        launchSignUpScreen(signUpTestRule) {
            typeEmail("alice@friends.com")
            typePassword("abc")
            submit()
        } verify {
            badPasswordErrorIsShown()
        }
    }

    @Test
    fun displayDuplicateAccountError() = runBlocking<Unit> {


        val signedUpUserEmail = "alice@friends.com"
        val signedUpUserPassword = "@l1cePass"


        replaceUserCatalogWith(InMemoryUserCatalog().apply {
            createUser(signedUpUserEmail, "", signedUpUserPassword)
        })

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

        replaceUserCatalogWith(UnavailableUserCatalog())

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

        replaceUserCatalogWith(OfflineUserCatalog())

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

    @After
    fun tearDown() {
        replaceUserCatalogWith(InMemoryUserCatalog())
    }

    private fun replaceUserCatalogWith(userCatalog: UserCatalog) {
        val replaceModule = module {
            factory {
                userCatalog
            }
        }
        loadKoinModules(replaceModule)
    }

    class OfflineUserCatalog : UserCatalog {
        override suspend fun createUser(email: String, password: String, about: String): User {
            throw ConnectionUnavailableException()
        }

    }

    class UnavailableUserCatalog : UserCatalog {
        override suspend fun createUser(email: String, password: String, about: String): User {
            throw BackEndException()
        }

    }
}