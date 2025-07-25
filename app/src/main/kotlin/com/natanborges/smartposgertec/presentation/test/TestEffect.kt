package com.natanborges.smartposgertec.presentation.test

sealed interface TestEffect {
    data class ShowSnackbar(val message: String) : TestEffect
}