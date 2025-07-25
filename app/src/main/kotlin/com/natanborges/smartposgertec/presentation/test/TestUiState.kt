package com.natanborges.smartposgertec.presentation.test

import com.natanborges.smartposgertec.domain.model.TestState

data class TestUiState(
    val testState: TestState? = null,
    val timeout: Boolean = false
)