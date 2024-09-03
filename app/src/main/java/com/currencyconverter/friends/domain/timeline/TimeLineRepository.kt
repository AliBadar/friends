package com.currencyconverter.friends.domain.timeline

import com.currencyconverter.friends.domain.exeptions.BackEndException
import com.currencyconverter.friends.domain.exeptions.ConnectionUnavailableException
import com.currencyconverter.friends.domain.post.PostCatalog
import com.currencyconverter.friends.domain.user.UserCatalog
import com.currencyconverter.friends.timeline.state.TimeLineState

class TimeLineRepository(
    private val userCatalog: UserCatalog,
    private val postCatalog: PostCatalog
) {
    fun getTimeLineFor(userId: String) = try {
        val userIds = listOf(userId) + userCatalog.followedBy(userId)
        val postsForUser = postCatalog.postsFor(userIds)
        TimeLineState.Posts(postsForUser)
    } catch (e: BackEndException) {
        TimeLineState.BackendError
    } catch (e: ConnectionUnavailableException) {
        TimeLineState.OfflineError
    }
}