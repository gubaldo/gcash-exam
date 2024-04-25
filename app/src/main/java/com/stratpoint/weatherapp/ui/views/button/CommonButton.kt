package com.stratpoint.weatherapp.ui.views.button

import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.stratpoint.weatherapp.R
import com.stratpoint.weatherapp.ui.theme.WeatherAppTheme
import com.stratpoint.weatherapp.ui.views.topbar.CommonBackTopBar

@Composable
fun CommonButton(
    modifier: Modifier = Modifier,
    label: String,
    enabled: Boolean = true,
    onClick: () -> Unit
) {
    Button(
        modifier = modifier,
        onClick = onClick,
        enabled = enabled
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.titleLarge
        )
    }
}

@Preview(showBackground = true)
@Composable
fun CommonButtonPreview() {
    WeatherAppTheme {
        CommonButton(
            modifier = Modifier,
            label = stringResource(id = R.string.label_login),
            enabled = true,
            onClick = {}
        )
    }
}