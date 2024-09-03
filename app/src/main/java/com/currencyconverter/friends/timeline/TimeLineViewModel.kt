package com.currencyconverter.friends.timeline

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.currencyconverter.friends.domain.post.PostCatalog
import com.currencyconverter.friends.domain.timeline.TimeLineRepository
import com.currencyconverter.friends.domain.user.UserCatalog
import com.currencyconverter.friends.timeline.state.TimeLineState

class TimeLineViewModel(
    private val timeLineRepository: TimeLineRepository
): ViewModel() {

    private val mutableTimeLineState = MutableLiveData<TimeLineState>()
    val timeLineState: LiveData<TimeLineState> = mutableTimeLineState

    fun timeLineFor(userId: String) {

        val result = timeLineRepository.getTimeLineFor(userId)

        mutableTimeLineState.value = result

    }


}
