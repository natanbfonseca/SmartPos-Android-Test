package com.natanborges.smartposgertec.domain.usecase

import com.natanborges.smartposgertec.domain.model.TestState
import com.natanborges.smartposgertec.domain.repository.DisplayTestRepository
import kotlinx.coroutines.flow.Flow

fun interface ObserveTestStateUseCase {
    operator fun invoke(): Flow<TestState>
}

internal class ObserveTestStateUseCaseImpl(private val repository: DisplayTestRepository): ObserveTestStateUseCase {
    override operator fun invoke() = repository.getTestState()
}