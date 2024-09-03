package com.currencyconverter.friends.timeline

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.onNodeWithText
import com.currencyconverter.friends.R
import com.currencyconverter.friends.domain.post.Post
import com.currencyconverter.friends.signup.launchSignUpScreen

fun launchTimeLineFor(
    email: String,
    password: String,
    timelineTestRule: MainActivityRule,
    block: TimeLineRobot.() -> Unit
): TimeLineRobot {

    launchSignUpScreen(timelineTestRule) {
        typeEmail(email)
        typePassword(password)
        submit()
    }

    return TimeLineRobot(timelineTestRule).apply(block)
}

class TimeLineRobot(private val rule: MainActivityRule) {
    infix fun verify(block: TimeLineVerificationRobot.() -> Unit): TimeLineVerificationRobot {
        return TimeLineVerificationRobot(rule).apply(block)
    }

    class TimeLineVerificationRobot(private val rule: MainActivityRule) {
        fun emptyTimeLineMessageIsDisplayed() {
            val emptyTimeLineMessage = rule.activity.getString(R.string.emptyTimelineMessage)
            rule.onNodeWithText(emptyTimeLineMessage)
                .assertIsDisplayed()
        }

        fun postsAreDisplayed(vararg posts: Post) {
            posts.forEach { post ->
                rule.onNodeWithText(post.postText)
                    .assertIsDisplayed()
            }
        }

    }

}