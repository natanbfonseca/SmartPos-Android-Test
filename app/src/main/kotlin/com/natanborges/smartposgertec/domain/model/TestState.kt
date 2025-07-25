package com.natanborges.smartposgertec.domain.model

data class TestState(
    val grid: List<List<Boolean>>,
    val isComplete: Boolean = false,
    val totalCells: Int,
    val touchedCells: Int
)