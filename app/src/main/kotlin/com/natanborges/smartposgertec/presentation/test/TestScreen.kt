package com.natanborges.smartposgertec.presentation.test

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.natanborges.smartposgertec.domain.model.TestState
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.compose.koinViewModel

@Composable
internal fun TestScreen(
    onShowSnackbar: (message: String) -> Unit,
    onNavigateBack: () -> Unit
) {
    val viewModel: TestViewModel = koinViewModel()
    val testState by viewModel.testState.collectAsStateWithLifecycle()
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.onEvent(TestEvent.Start)
    }

    LaunchedEffect(Unit) {
        viewModel.effect.collectLatest { effect ->
            when (effect) {
                is TestEffect.ShowSnackbar -> {
                    onShowSnackbar(context.getString(effect.message))
                    onNavigateBack()
                }
            }
        }
    }

    if (testState == null)
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("Iniciando...")
        }
    else
        TestContent(
            testState = testState,
            onEvent = viewModel::onEvent
        )
}

@Composable
private fun TestContent(
    testState: TestState?,
    onEvent: (TestEvent) -> Unit
) {
    val gridState = testState!!.grid
    val gridRows = gridState.size
    val gridCols = gridState.firstOrNull()?.size ?: 0

    Column(
        Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .pointerInput(gridRows, gridCols) {
                    if (gridRows == 0 || gridCols == 0) return@pointerInput
                    val cellWidth = size.width / gridCols.toFloat()
                    val cellHeight = size.height / gridRows.toFloat()
                    var lastTouchedCell: Pair<Int, Int>? = null

                    awaitPointerEventScope {
                        while (true) {
                            val event = awaitPointerEvent()
                            val position = event.changes.first().position
                            val col = (position.x / cellWidth).toInt().coerceIn(0, gridCols - 1)
                            val row = (position.y / cellHeight).toInt().coerceIn(0, gridRows - 1)
                            val currentCell = Pair(row, col)
                            if (currentCell != lastTouchedCell) {
                                onEvent(TestEvent.Touch(row, col))
                                lastTouchedCell = currentCell
                            }
                        }
                    }
                }
        ) {
            LazyVerticalGrid(
                columns = GridCells.Fixed(gridCols),
                modifier = Modifier.fillMaxWidth(),
                userScrollEnabled = false
            ) {
                items(gridRows * gridCols) { index ->
                    val row = index / gridCols
                    val col = index % gridCols
                    GridCell(isTouched = gridState[row][col])
                }
            }
        }
        if (testState.isComplete) {
            Button(
                onClick = { onEvent(TestEvent.Pass) },
                modifier = Modifier
                    .fillMaxWidth(0.5f)
                    .padding(16.dp)
            ) {
                Text("Passou")
            }
        }
    }
}

@Composable
private fun GridCell(isTouched: Boolean) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .aspectRatio(1f)
            .background(if (isTouched) Color.Green else Color.LightGray)
            .border(0.5.dp, Color.White)
    )
}

@Preview
@Composable
private fun TestContentPreview() {
    val gridState = List(7) { List(5) { false } }
    val gridRows = 7
    val gridCols = 5
    val testState = TestState(
        grid = gridState,
        isComplete = false,
        totalCells = gridRows * gridCols,
        touchedCells = 0
    )

    TestContent(
        testState = testState,
        onEvent = {}
    )
}