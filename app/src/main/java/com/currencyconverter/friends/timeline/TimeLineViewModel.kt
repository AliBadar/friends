package com.currencyconverter.friends.timeline

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.currencyconverter.friends.domain.post.InMemoryPostCatalog
import com.currencyconverter.friends.domain.user.InMemoryUserCatalog
import com.currencyconverter.friends.domain.user.UserCatalog
import com.currencyconverter.friends.timeline.state.TimeLineState

class TimeLineViewModel(
    private val userCatalog: UserCatalog,
    private val postCatalog: InMemoryPostCatalog
) {

    private val mutableTimeLineState = MutableLiveData<TimeLineState>()
    val timeLineState: LiveData<TimeLineState> = mutableTimeLineState

    fun timeLineFor(userId: String) {

        val userIds = listOf(userId) + userCatalog.followedBy(userId)
        val postsForUser = postCatalog.postsFor(userIds)
        mutableTimeLineState.value = TimeLineState.Posts(postsForUser)
    }


}
