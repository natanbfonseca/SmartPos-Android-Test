package com.natanborges.smartposgertec.presentation.test

sealed class TestEvent {
    object Start : TestEvent()
    data class Touch(val row: Int, val col: Int) : TestEvent()
    object Pass : TestEvent()
}