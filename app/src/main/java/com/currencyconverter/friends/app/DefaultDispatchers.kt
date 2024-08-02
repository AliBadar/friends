package com.currencyconverter.friends.app

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

class DefaultDispatchers : CoroutinesDispatchers {
    override val backGround = Dispatchers.IO

}
