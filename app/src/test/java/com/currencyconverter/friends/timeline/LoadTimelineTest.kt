package com.currencyconverter.friends.timeline

import com.currencyconverter.friends.InstantTaskExecutorExtension
import com.currencyconverter.friends.domain.post.InMemoryPostCatalog
import com.currencyconverter.friends.domain.post.Post
import com.currencyconverter.friends.domain.user.Following
import com.currencyconverter.friends.domain.user.InMemoryUserCatalog
import com.currencyconverter.friends.infrastructure.builder.UserBuilder.Companion.aUser
import com.currencyconverter.friends.timeline.state.TimeLineState
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(InstantTaskExecutorExtension::class)
class LoadTimelineTest {

    private val lucy = aUser().withId("lucyId").build()
    private val sara = aUser().withId("saraId").build()
    private val anna = aUser().withId("annaId").build()
    private val tim = aUser().withId("timId").build()

    private val timPosts = listOf(Post("postId", tim.id, "post text", 1L))
    private val lucyPosts = listOf(
        Post("post2", lucy.id, "Post 2", 2L),
        Post("post1", lucy.id, "Post 1", 1L),
    )

    private val saraPosts = listOf(
        Post("post4", sara.id, "Post 4", 4L),
        Post("post3", sara.id, "Post 3", 3L),
    )

    private val availablePosts = timPosts + lucyPosts + saraPosts

    @Test
    fun noPostsAvailable() {
        val viewModel = TimeLineViewModel(
            InMemoryUserCatalog(), InMemoryPostCatalog(
                availablePosts
            )
        )

        viewModel.timeLineFor("tomId")

        assertEquals(
            TimeLineState.Posts(emptyList<Post>()),
            viewModel.timeLineState.value
        )
    }

    @Test
    fun postsAvailable() {

        val viewModel = TimeLineViewModel(
            InMemoryUserCatalog(
                followings = listOf(
                    Following("saraId", "lucyId"),
                    Following("annaId", "lucyId")
                )
            ), InMemoryPostCatalog(
                availablePosts
            )
        )
        viewModel.timeLineFor(tim.id)


        assertEquals(
            TimeLineState.Posts(timPosts),
            viewModel.timeLineState.value
        )
    }

    @Test
    fun postsFormFriends() {

        val viewModel = TimeLineViewModel(
            InMemoryUserCatalog(
                followings = listOf(
                    Following(anna.id, lucy.id)
                )
            ), InMemoryPostCatalog(
                availablePosts
            )
        )

        viewModel.timeLineFor(anna.id)

        assertEquals(TimeLineState.Posts(lucyPosts), viewModel.timeLineState.value)
    }


    @Test
    fun postsFromFriendsAlongOwn() {
        val viewModel = TimeLineViewModel(
            InMemoryUserCatalog(
                followings = listOf(
                    Following(sara.id, lucy.id)
                )
            ), InMemoryPostCatalog(
                availablePosts
            )
        )

        viewModel.timeLineFor(sara.id)
        assertEquals(TimeLineState.Posts(lucyPosts + saraPosts), viewModel.timeLineState.value)
    }


}