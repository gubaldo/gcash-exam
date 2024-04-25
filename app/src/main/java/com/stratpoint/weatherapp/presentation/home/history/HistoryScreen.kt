package com.stratpoint.weatherapp.presentation.home.history

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.tooling.preview.Preview
import com.stratpoint.weatherapp.ui.theme.WeatherAppTheme

@Composable
fun HistoryScreen(
    modifier: Modifier,
    navigateToAuth: () -> Unit,
) {

    HistoryContent(modifier = modifier, navigateToAuth)

}

@Composable
fun HistoryContent(
    modifier: Modifier,
    navigateToAuth: () -> Unit
) {
    Box(
        modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentAlignment = Alignment.Center
    ) {
        ClickableText(text = AnnotatedString("History Screen")) {
            navigateToAuth()
        }
    }
}

@Preview
@Composable
private fun HistoryPreview() {
    WeatherAppTheme {
        HistoryContent(modifier = Modifier, {})
    }
}