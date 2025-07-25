package com.natanborges.smartposgertec.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.natanborges.smartposgertec.presentation.start.StartScreen
import com.natanborges.smartposgertec.presentation.test.TestScreen

@Composable
fun AppNavigation(modifier: Modifier = Modifier, onShowSnackbar: (message: String) -> Unit) {
    val navController = rememberNavController()

    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = StartRoute
    ) {
        composable<StartRoute> {
            StartScreen(onStartClick = { navController.navigate(TestRoute) })
        }

        composable<TestRoute> {
            TestScreen(
                onShowSnackbar = onShowSnackbar,
                onNavigateBack = {
                    navController.popBackStack()
                })
        }
    }
}