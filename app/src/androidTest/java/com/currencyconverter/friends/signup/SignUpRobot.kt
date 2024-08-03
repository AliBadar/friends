package com.currencyconverter.friends.signup

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.currencyconverter.friends.MainActivity
import com.currencyconverter.friends.R
import com.currencyconverter.friends.domain.exeptions.BackEndException
import com.currencyconverter.friends.domain.user.User
import com.currencyconverter.friends.domain.user.UserCatalog

fun launchSignUpScreen(
    rule: AndroidComposeTestRule<ActivityScenarioRule<MainActivity>, MainActivity>,
    block: SignUpRobot.() -> Unit
): SignUpRobot {
    return SignUpRobot(rule).apply(block)
}

class SignUpRobot(private val rule: AndroidComposeTestRule<ActivityScenarioRule<MainActivity>, MainActivity>) {
    fun typeEmail(email: String) {
        val emailHint = rule.activity.getString(R.string.email)
        rule.onNodeWithTag(emailHint)
            .performTextInput(email)
    }

    fun typePassword(password: String) {
        val passwordHint = rule.activity.getString(R.string.password)
        rule.onNodeWithTag(passwordHint)
            .performTextInput(password)
    }

    fun submit() {
        val signUp = rule.activity.getString(R.string.signUp)
        rule.onNodeWithText(signUp)
            .performClick()
    }

    infix fun verify(block: SignUpVerification.() -> Unit)
            : SignUpVerification {
        return SignUpVerification(rule).apply(block)
    }

}

class SignUpVerification(private val rule: AndroidComposeTestRule<ActivityScenarioRule<MainActivity>, MainActivity>) {
    fun timeLineScreenIsPresent() {
        val timeLine = rule.activity.getString(R.string.timeline)
        rule.onNodeWithText(timeLine)
            .assertIsDisplayed()

    }

    fun duplicateAccountErrorIsShown() {

        val duplicateAccountError = rule.activity.getString(R.string.duplicateAccountError)
        rule.onNodeWithText(duplicateAccountError)
            .assertIsDisplayed()
    }



    fun backEndErrorIsShown() {
        val backendError = rule.activity.getString(R.string.createAccountError)
        rule.onNodeWithText(backendError)
            .assertIsDisplayed()
    }

    fun offlineErrorIsShown() {
        val offlineError = rule.activity.getString(R.string.offlineError)
        rule.onNodeWithText(offlineError)
            .assertIsDisplayed()
    }

    fun badEmailErrorIsShown() {
        val badEmailError = rule.activity.getString(R.string.badEmailError)
        rule.onNodeWithText(badEmailError)
            .assertIsDisplayed()
    }

    fun badPasswordErrorIsShown() {
        val badPasswordError = rule.activity.getString(R.string.badPasswordError)
        rule.onNodeWithText(badPasswordError)
            .assertIsDisplayed()
    }

    fun badEmailErrorIsNotShown() {
        val badEmail = rule.activity.getString(R.string.badEmailError)
        rule.onNodeWithText(badEmail)
            .assertDoesNotExist()
    }

    fun badPasswordErrorIsNotShown() {
        val badPassword = rule.activity.getString(R.string.badPasswordError)
        rule.onNodeWithText(badPassword)
            .assertDoesNotExist()
    }

    fun blockingLoadingIsShown() {
        val loading = rule.activity.getString(R.string.loading)
        rule.onNodeWithTag(loading)
            .assertIsDisplayed()
    }

}
