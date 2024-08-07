package com.currencyconverter.friends.signup

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.currencyconverter.friends.MainActivity
import com.currencyconverter.friends.domain.exeptions.BackEndException
import com.currencyconverter.friends.domain.exeptions.ConnectionUnavailableException
import com.currencyconverter.friends.domain.user.Following
import com.currencyconverter.friends.domain.user.InMemoryUserCatalog
import com.currencyconverter.friends.domain.user.User
import com.currencyconverter.friends.domain.user.UserCatalog
import kotlinx.coroutines.delay
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
            InMemoryUserCatalog(
                followings = listOf(
                    Following("saraId", "lucyId"),
                    Following("annaId", "lucyId")
                )
            )
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


        replaceUserCatalogWith(InMemoryUserCatalog(
            followings = listOf(
                Following("saraId", "lucyId"),
                Following("annaId", "lucyId")
            )
        ).apply {
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

    @Test
    fun displayBlockingLoading() {

        replaceUserCatalogWith(DelayingUserCatalog())

        launchSignUpScreen(signUpTestRule) {
            typeEmail("caly@friends.com")
            typePassword("C@lyP1ss#")
            submit()
        } verify {
            blockingLoadingIsShown()
        }
    }

    class DelayingUserCatalog : UserCatalog {
        override suspend fun createUser(email: String, password: String, about: String): User {
            delay(1000)
            return User("someId", email, about)
        }

        override fun followedBy(userId: String): List<String> {
            TODO("Not yet implemented")
        }

    }

    @After
    fun tearDown() {
        replaceUserCatalogWith(InMemoryUserCatalog(
            followings = listOf(
                Following("saraId", "lucyId"),
                Following("annaId", "lucyId")
            )
        )
        )
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

        override fun followedBy(userId: String): List<String> {
            TODO("Not yet implemented")
        }

    }

    class UnavailableUserCatalog : UserCatalog {
        override suspend fun createUser(email: String, password: String, about: String): User {
            throw BackEndException()
        }

        override fun followedBy(userId: String): List<String> {
            TODO("Not yet implemented")
        }

    }
}