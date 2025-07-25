package com.natanborges.smartposgertec.domain.repository

import com.natanborges.smartposgertec.domain.model.TestState
import kotlinx.coroutines.flow.Flow

interface DisplayTestRepository {
    fun getTestState(): Flow<TestState>
    suspend fun startTest()
    suspend fun touchCell(row: Int, col: Int)
}