package com.natanborges.smartposgertec.presentation.test

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.natanborges.smartposgertec.domain.model.TestState
import com.natanborges.smartposgertec.domain.usecase.ObserveTestStateUseCase
import com.natanborges.smartposgertec.domain.usecase.StartTestUseCase
import com.natanborges.smartposgertec.domain.usecase.TouchCellUseCase
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class TestViewModel(
    observeTestStateUseCase: ObserveTestStateUseCase,
    private val startTestUseCase: StartTestUseCase,
    private val touchCellUseCase: TouchCellUseCase
) : ViewModel() {

    private val _effect = MutableSharedFlow<TestEffect>()
    val effect = _effect.asSharedFlow()
    private var timeoutJob: Job? = null
    val testState: StateFlow<TestState?> = observeTestStateUseCase()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    fun onEvent(event: TestEvent) {
        when (event) {
            is TestEvent.Start -> beginTest()
            is TestEvent.Touch -> onTouch(event.row, event.col)
            is TestEvent.Pass -> onTestPassed()
        }
    }

    private fun beginTest() {
        viewModelScope.launch {
            startTestUseCase()
            startTimeout()
        }
    }

    private fun startTimeout() {
        timeoutJob?.cancel()
        timeoutJob = viewModelScope.launch {
            delay(10_000)
            if (testState.value?.isComplete == false) {
                _effect.emit(TestEffect.ShowSnackbar("O teste falhou por tempo!"))
            }
        }
    }

    private fun onTouch(row: Int, col: Int) {
        viewModelScope.launch {
            touchCellUseCase(row, col)
        }
    }

    private fun onTestPassed() {
        viewModelScope.launch {
            _effect.emit(TestEffect.ShowSnackbar("O teste passou!"))
            timeoutJob?.cancel()
        }
    }
}