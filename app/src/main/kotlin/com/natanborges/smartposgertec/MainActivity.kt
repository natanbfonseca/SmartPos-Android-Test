package com.natanborges.smartposgertec

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.natanborges.smartposgertec.presentation.navigation.AppNavigation
import com.natanborges.smartposgertec.presentation.ui.theme.SmartPOSGERTECTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val snackbarHostState = remember { SnackbarHostState() }
            val coroutineScope = rememberCoroutineScope()

            SmartPOSGERTECTheme {
                MainContent(
                    snackbarHostState = snackbarHostState,
                    coroutineScope = coroutineScope
                )
            }
        }
    }
}

@Composable
private fun MainContent(
    snackbarHostState: SnackbarHostState,
    coroutineScope: CoroutineScope
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.background,
        snackbarHost = {
            SnackbarHost(
                snackbarHostState, modifier = Modifier
                    .padding(bottom = 36.dp)
            ) {
                Card(
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                    shape = RoundedCornerShape(24.dp),
                ) {
                    Text(
                        it.visuals.message,
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }
    ) { innerPadding ->
        AppNavigation(
            modifier = Modifier.padding(innerPadding),
            onShowSnackbar = { message ->
                coroutineScope.launch {
                    snackbarHostState.showSnackbar(
                        message = message,
                        duration = SnackbarDuration.Short
                    )
                }
            })
    }
}

@Preview
@Composable
private fun MainContentPreview() {
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    SmartPOSGERTECTheme(
        darkTheme = true
    ) {
        MainContent(
            snackbarHostState = snackbarHostState,
            coroutineScope = coroutineScope
        )
    }
}
