package com.natanborges.smartposgertec.domain.usecase

import com.natanborges.smartposgertec.domain.repository.DisplayTestRepository

fun interface StartTestUseCase {
    suspend operator fun invoke()
}

internal class StartTestUseCaseImpl(private val repository: DisplayTestRepository): StartTestUseCase {
    override suspend operator fun invoke() = repository.startTest()
}
