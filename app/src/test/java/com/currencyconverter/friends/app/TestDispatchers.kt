package com.currencyconverter.friends.app

import kotlinx.coroutines.Dispatchers

class TestDispatchers : CoroutinesDispatchers {
    override val backGround = Dispatchers.Unconfined
}