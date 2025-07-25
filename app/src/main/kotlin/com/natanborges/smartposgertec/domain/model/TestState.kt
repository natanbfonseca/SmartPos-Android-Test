package com.natanborges.smartposgertec.domain.model

data class TestState(
    val grid: List<List<Boolean>>, // true se a célula foi tocada, false caso contrário
    val isComplete: Boolean = false,
    val totalCells: Int,
    val touchedCells: Int
)