package com.currencyconverter.friends.timeline

import com.currencyconverter.friends.InstantTaskExecutorExtension
import com.currencyconverter.friends.domain.post.Post
import com.currencyconverter.friends.domain.user.User
import com.currencyconverter.friends.infrastructure.builder.UserBuilder
import com.currencyconverter.friends.timeline.state.TimeLineState
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(InstantTaskExecutorExtension::class)
class LoadPostsTest {

    @Test
    fun noPostsAvailable() {
        val viewModel = TimeLineViewModel()

        val anna = User("tomId", "", "")
        viewModel.timeLineFor(anna.id)

        assertEquals(
            TimeLineState.Posts(emptyList<Post>()),
            viewModel.timeLineState.value
        )
    }

    @Test
    fun postsAvailable() {
        val tim = UserBuilder.aUser().withId("timId").build()
        val timPosts = listOf(Post("postId", tim.id, "post text", 1L))

        val viewModel = TimeLineViewModel()
        viewModel.timeLineFor(tim.id)


        assertEquals(
            TimeLineState.Posts(timPosts),
            viewModel.timeLineState.value
        )
    }

    @Test
    fun postsFormFriends() {
        val anna = UserBuilder.aUser().withId("annaId").build()
        val lucy = UserBuilder.aUser().withId("lucyId").build()
        val lucyPosts = listOf(
            Post("post2", lucy.id, "Post 2",  2L),
            Post("post1", lucy.id, "Post 1",  1L),
        )

        val viewModel = TimeLineViewModel()

        viewModel.timeLineFor(anna.id)

        assertEquals(TimeLineState.Posts(lucyPosts), viewModel.timeLineState.value)
    }

}