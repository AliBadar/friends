package com.currencyconverter.friends.timeline

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.currencyconverter.friends.domain.post.Post
import com.currencyconverter.friends.timeline.state.TimeLineState

class TimeLineViewModel {

    private val mutableTimeLineState = MutableLiveData<TimeLineState>()
    val timeLineState: LiveData<TimeLineState> = mutableTimeLineState

    fun timeLineFor(userId: String) {

        if (userId == "annaId"){
            val posts = listOf(
                Post("post2", "lucyId", "Post 2",  2L),
                Post("post1", "lucyId", "Post 1",  1L),
            )

            mutableTimeLineState.value = TimeLineState.Posts(posts)
        } else if (userId == "timId") {
            val posts = listOf(Post("postId", "timId", "post text", 1L))
            mutableTimeLineState.value = TimeLineState.Posts(posts)
        } else {
            mutableTimeLineState.value = TimeLineState.Posts(emptyList())
        }

    }
}
