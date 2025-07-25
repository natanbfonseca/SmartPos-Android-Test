package com.natanborges.smartposgertec.domain.usecase

import com.natanborges.smartposgertec.domain.repository.DisplayTestRepository

fun interface StartTestUseCase {
    suspend operator fun invoke()
}

internal class StartTestUseCaseImpl(private val repository: DisplayTestRepository): StartTestUseCase {
    // Definimos 10 linhas e 7 colunas, mas pode ser dinâmico
    override suspend operator fun invoke() = repository.startTest()
}
