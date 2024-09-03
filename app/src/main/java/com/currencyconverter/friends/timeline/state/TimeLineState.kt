package com.currencyconverter.friends.timeline.state

import com.currencyconverter.friends.domain.post.Post

sealed class TimeLineState {
    data class Posts(val posts: List<Post>) : TimeLineState()
    object BackendError: TimeLineState()
    object OfflineError: TimeLineState()

}
