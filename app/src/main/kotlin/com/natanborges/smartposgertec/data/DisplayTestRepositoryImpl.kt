package com.natanborges.smartposgertec.data

import com.natanborges.smartposgertec.domain.model.TestState
import com.natanborges.smartposgertec.domain.repository.DisplayTestRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

internal class DisplayTestRepositoryImpl : DisplayTestRepository {

    private val _testState = MutableStateFlow(
        TestState(grid = emptyList(), touchedCells = 0, totalCells = 0)
    )

    override fun getTestState(): Flow<TestState> = _testState.asStateFlow()

    override suspend fun startTest() {
        val initialGrid = List(INITIAL_ROWS) { List(INITIAL_COLS) { false } }
        _testState.value = TestState(
            grid = initialGrid,
            touchedCells = 0,
            totalCells = INITIAL_ROWS * INITIAL_COLS
        )
    }

    override suspend fun touchCell(row: Int, col: Int) {
        _testState.update { currentState ->
            if (row < 0 || row >= currentState.grid.size || col < 0 || col >= currentState.grid.first().size) {
                return@update currentState
            }

            if (currentState.grid[row][col]) {
                return@update currentState
            }

            val newGrid = currentState.grid.toMutableList().map { it.toMutableList() }
            newGrid[row][col] = true

            val newTouchedCount = currentState.touchedCells + 1
            currentState.copy(
                grid = newGrid,
                touchedCells = newTouchedCount,
                isComplete = newTouchedCount == currentState.totalCells
            )
        }
    }

    private companion object {
        const val INITIAL_ROWS = 7
        const val INITIAL_COLS = 5
    }
}