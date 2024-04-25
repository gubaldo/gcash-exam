package com.stratpoint.weatherapp.ui.views.dialog

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.stratpoint.weatherapp.ui.theme.spacing

@Composable
fun BasicAlertDialog(
    showDialog: Boolean,
    title: String? = null,
    message: String,
    buttonLabel: String,
    onButtonClick: () -> Unit
) {
    ConfirmationAlertDialog(
        showDialog = showDialog,
        title,
        message = message,
        positiveButtonLabel = buttonLabel,
        positiveButtonClick = onButtonClick
    )
}

@Composable
fun ConfirmationAlertDialog(
    showDialog: Boolean,
    title: String? = null,
    message: String,
    positiveButtonLabel: String,
    negativeButtonLabel: String? = null,
    positiveButtonClick: () -> Unit,
    negativeButtonClick: (() -> Unit)? = null
) {

    if (showDialog) {
        AlertDialog(
            onDismissRequest = {},
            containerColor = MaterialTheme.colorScheme.background,
            title = {
                title?.let {
                    Text(
                        text = title,
                        style = MaterialTheme.typography.titleLarge
                    )
                }
            },
            text = {
                Text(
                    text = message,
                    modifier = Modifier.padding(
                        start = MaterialTheme.spacing.small
                    )
                )
            },
            confirmButton = {
                TextButton(onClick = positiveButtonClick) {
                    Text(text = positiveButtonLabel)
                }
            },
            dismissButton = {
                negativeButtonLabel?.let {
                    TextButton(onClick = negativeButtonClick!!) {
                        Text(text = negativeButtonLabel)
                    }
                }
            }
        )
    }

}