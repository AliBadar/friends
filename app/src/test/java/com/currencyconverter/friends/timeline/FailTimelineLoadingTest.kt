package com.currencyconverter.friends.timeline

import com.currencyconverter.friends.InstantTaskExecutorExtension
import com.currencyconverter.friends.domain.exeptions.BackEndException
import com.currencyconverter.friends.domain.exeptions.ConnectionUnavailableException
import com.currencyconverter.friends.domain.post.InMemoryPostCatalog
import com.currencyconverter.friends.domain.post.Post
import com.currencyconverter.friends.domain.post.PostCatalog
import com.currencyconverter.friends.domain.timeline.TimeLineRepository
import com.currencyconverter.friends.domain.user.InMemoryUserCatalog
import com.currencyconverter.friends.timeline.state.TimeLineState
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(InstantTaskExecutorExtension::class)
class FailTimelineLoadingTest {

    @Test
    fun backEndError() {

        val userCatalog = InMemoryUserCatalog()
        val postCatalog = UnavailablePostCatalog()
        val viewModel = TimeLineViewModel(
            TimeLineRepository(
                userCatalog,
                postCatalog
            )
        )
        viewModel.timeLineFor(":irrelevant")
        assertEquals(TimeLineState.BackendError, viewModel.timeLineState.value)
    }

    @Test
    fun offlineError() {


        val userCatalog = InMemoryUserCatalog()
        val postCatalog = OfflinePostCatalog()
        val viewModel = TimeLineViewModel(
            TimeLineRepository(
                userCatalog,
                postCatalog
            )
        )

        viewModel.timeLineFor(":irrelevant")
        assertEquals(TimeLineState.OfflineError, viewModel.timeLineState.value)
    }

    private class OfflinePostCatalog : PostCatalog {
        override fun postsFor(userIds: List<String>): List<Post> {
            throw ConnectionUnavailableException()
        }

    }

    private class UnavailablePostCatalog : PostCatalog {
        override fun postsFor(userIds: List<String>): List<Post> {
            throw BackEndException()
        }

    }
}