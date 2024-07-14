package com.currencyconverter.friends.signup

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.currencyconverter.friends.MainActivity
import org.junit.Rule
import org.junit.Test

class SignUpTest {

    @get:Rule
    val signUpTestRule = createAndroidComposeRule<MainActivity>()

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

}