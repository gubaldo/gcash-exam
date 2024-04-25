package com.stratpoint.weatherapp.ui.views.topbar

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.stratpoint.weatherapp.R
import com.stratpoint.weatherapp.auth.login.presentation.LoginScreenContent
import com.stratpoint.weatherapp.auth.login.presentation.rememberLoginScreenState
import com.stratpoint.weatherapp.ui.theme.WeatherAppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CommonBackTopBar(
    modifier: Modifier = Modifier,
    title: String,
    onClickBackIcon: () -> Unit
) {

    TopAppBar(
        title = {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = title,
                style = MaterialTheme.typography.titleLarge
            )
        },
        modifier = modifier,
        navigationIcon = {
            IconButton(onClick = onClickBackIcon) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_arrow_back),
                    contentDescription = stringResource(id = R.string.cd_arrow_back)
                )
            }
        },
        colors = topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            navigationIconContentColor = MaterialTheme.colorScheme.inversePrimary,
            titleContentColor = MaterialTheme.colorScheme.inversePrimary
        )
    )

}

@Preview(showBackground = true)
@Composable
fun CommonBackTopBarPreview() {
    WeatherAppTheme {
        CommonBackTopBar(
            modifier = Modifier,
            title = stringResource(id = R.string.label_create_account),
            onClickBackIcon = {}
        )
    }
}