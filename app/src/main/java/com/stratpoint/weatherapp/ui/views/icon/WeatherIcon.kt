package com.stratpoint.weatherapp.ui.views.icon

import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource

@Composable
fun WeatherIcon(
    modifier: Modifier,
    imageId: Int
) {
    Icon(
        imageVector = ImageVector.vectorResource(id = imageId),
        contentDescription = null,
        modifier = modifier
    )
}