package com.natanborges.smartposgertec.domain.usecase

import com.natanborges.smartposgertec.domain.repository.DisplayTestRepository

fun interface TouchCellUseCase {
    suspend operator fun invoke(row: Int, col: Int)
}

internal class TouchCellUseCaseImpl(private val repository: DisplayTestRepository): TouchCellUseCase {
    override suspend operator fun invoke(row: Int, col: Int) = repository.touchCell(row, col)
}