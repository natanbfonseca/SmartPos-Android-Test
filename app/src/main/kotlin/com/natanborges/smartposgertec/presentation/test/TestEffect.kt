package com.natanborges.smartposgertec.presentation.test

import androidx.annotation.StringRes

sealed interface TestEffect {
    data class ShowSnackbar(
        @field:StringRes val message: Int
    ) : TestEffect
}