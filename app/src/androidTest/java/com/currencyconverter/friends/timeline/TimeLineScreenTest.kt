package com.currencyconverter.friends.timeline

import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.currencyconverter.friends.MainActivity
import com.currencyconverter.friends.domain.post.InMemoryPostCatalog
import com.currencyconverter.friends.domain.post.Post
import com.currencyconverter.friends.domain.post.PostCatalog
import com.currencyconverter.friends.domain.user.UserCatalog
import org.junit.After
import org.junit.Rule
import org.junit.Test
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module

typealias MainActivityRule = AndroidComposeTestRule<ActivityScenarioRule<MainActivity>, MainActivity>

class TimeLineScreenTest {

    @get:Rule
    val timelineTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun showsEmptyTimeLineMessage() {
        val email = "lucy@friends.com"
        val password = "passPASS123#Q"
        launchTimeLineFor(email, password, timelineTestRule) {

        } verify {
            emptyTimeLineMessageIsDisplayed()
        }
        //no operation
    }

    @Test
    fun showsAvailablePosts() {
        val email = "bob@friends.com"
        val password = "b0bPaS#2021"
        val post1 = Post("post1", "bobId", "This is Bob's first post", 1L)
        val post2 = Post("post2", "bobId", "Bob's second post is here!", 2L)

        val postCatalog = InMemoryPostCatalog(mutableListOf(post1, post2))

        replacePostCatalogWith(postCatalog)

        launchTimeLineFor(email, password, timelineTestRule) {

        } verify {
            postsAreDisplayed(post1, post2)
        }
    }

    private fun replacePostCatalogWith(postsCatalog: PostCatalog) {
        val replaceModule = module {
            factory { postsCatalog }
        }
        loadKoinModules(replaceModule)
    }

    @After
    fun tearDown() {
        replacePostCatalogWith(InMemoryPostCatalog())
    }
}
