package com.stratpoint.weatherapp.ui.views.textfield

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.stratpoint.weatherapp.R
import com.stratpoint.weatherapp.ui.theme.WeatherAppTheme

@Composable
fun CommonOutlinedTextField(
    modifier: Modifier = Modifier,
    value: String = "",
    onValueChange: (String) -> Unit,
    label: String,
    errorMessage: String? = null,
    isSingleLine: Boolean = true,
    maxLines: Int = 1,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default
) {

    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier,
        label = { Text(text = label) },
        supportingText = {
            errorMessage?.let { Text(text = it) }
        },
        isError = errorMessage != null,
        keyboardOptions = keyboardOptions,
        singleLine = isSingleLine,
        maxLines = maxLines
    )
}

@Preview(showBackground = true)
@Composable
fun CommonOutlinedTextFieldPreview() {
    WeatherAppTheme {
        CommonOutlinedTextField(
            modifier = Modifier,
            label = stringResource(id = R.string.label_email),
            onValueChange = {}
        )
    }
}