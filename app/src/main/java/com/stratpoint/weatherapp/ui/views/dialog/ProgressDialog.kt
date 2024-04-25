package com.stratpoint.weatherapp.ui.views.dialog

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.stratpoint.weatherapp.ui.theme.spacing

@Composable
fun ProgressDialog(
    showDialog: Boolean,
    title: String? = null,
    message: String
) {
    if (showDialog) {
        Dialog(onDismissRequest = { /* Do nothing, not dismissible */ }) {
            Surface(
                color = MaterialTheme.colorScheme.background,
                shape = RoundedCornerShape(10.dp)
            ) {
                Column(
                    modifier = Modifier.padding(MaterialTheme.spacing.large)
                ) {
                    title?.let {
                        Text(
                            text = title,
                            style = MaterialTheme.typography.titleLarge,
                            modifier = Modifier.padding(bottom = MaterialTheme.spacing.medium)
                        )
                    }
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        CircularProgressIndicator()
                        Text(
                            text = message,
                            modifier = Modifier.padding(start = MaterialTheme.spacing.medium)
                        )
                    }
                }
            }
        }
    }
}