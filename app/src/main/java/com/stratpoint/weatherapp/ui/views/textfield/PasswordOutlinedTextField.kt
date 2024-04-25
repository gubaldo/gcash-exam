package com.stratpoint.weatherapp.ui.views.textfield

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import com.stratpoint.weatherapp.R
import com.stratpoint.weatherapp.ui.theme.WeatherAppTheme

@Composable
fun PasswordOutlinedTextField(
    modifier: Modifier = Modifier,
    value: String = "",
    onValueChange: (String) -> Unit,
    label: String,
    errorMessage: String? = null,
    isSingleLine: Boolean = true,
    maxLines: Int = 1
) {

    var isPasswordHidden by remember { mutableStateOf(true) }

    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier,
        label = { Text(text = label) },
        trailingIcon = {
            IconButton(onClick = { isPasswordHidden = !isPasswordHidden }) {
                val (icon, description) = if (isPasswordHidden) {
                    R.drawable.ic_visibility to R.string.cd_show_password
                } else {
                    R.drawable.ic_visibility_off to R.string.cd_hide_password
                }

                Icon(
                    painter = painterResource(id = icon),
                    contentDescription = stringResource(id = description)
                )
            }
        },
        supportingText = {
            errorMessage?.let { Text(text = it) }
        },
        isError = errorMessage != null,
        visualTransformation = if (isPasswordHidden) PasswordVisualTransformation() else VisualTransformation.None,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        singleLine = isSingleLine,
        maxLines = maxLines
    )
}

@Preview(showBackground = true)
@Composable
fun PasswordOutlinedTextFieldPreview() {
    WeatherAppTheme {
        PasswordOutlinedTextField(
            modifier = Modifier,
            onValueChange = {},
            label = stringResource(id = R.string.label_password)
        )
    }
}