package com.yablonskyi.caelum.ui.weather.forecast

import android.util.Log
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.List
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.yablonskyi.caelum.common.Units
import com.yablonskyi.caelum.domain.model.City
import com.yablonskyi.caelum.ui.theme.CaelumTheme
import com.yablonskyi.caelum.ui.weather.forecast.viewmodel.day.ForecastBundle
import com.yablonskyi.caelum.ui.weather.forecast.viewmodel.day.LocationListState

@Composable
fun HomeScreen(
    forecastList: List<ForecastBundle>,
    locationListState: LocationListState,
    units: Units,
    onRefresh: (City) -> Unit,
    onListButtonClick: () -> Unit,
    onSettingsButtonClick: () -> Unit,
    onCityChange: (City) -> Unit,
) {
    val pagerState = rememberPagerState(pageCount = {
        locationListState.cities.size
    })

    LaunchedEffect(pagerState) {
        snapshotFlow { pagerState.currentPage }.collect { page ->
            onCityChange(locationListState.cities[page])
            Log.d("Page change", "Page changed to $page")
        }
    }

    Scaffold(
        bottomBar = {
            BottomAppBar(

            ) {
                Box(
                    Modifier.fillMaxWidth()
                ) {
                    IconButton(
                        onClick = onListButtonClick,
                        modifier = Modifier.align(Alignment.CenterEnd)
                    ) {
                        Icon(
                            Icons.AutoMirrored.Outlined.List,
                            contentDescription = "list",
                            modifier = Modifier.size(32.dp)
                        )
                    }
                    CitiesPaging(
                        pagerState.pageCount,
                        pagerState.currentPage,
                        Modifier.align(Alignment.Center)
                    )
                }
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            HorizontalPager(state = pagerState) { page ->
                val city = locationListState.cities[page]
                val forecastBundle = forecastList.find { it.cityState.city?.name == city.name }

                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxSize()
                ) {
                    Text(city.name, Modifier.padding(vertical = 8.dp))
                    WeatherCard(
                        hourForecast = forecastBundle?.currentForecastState?.forecast,
                        dayForecast = forecastBundle?.dayForecastState?.forecast,
                        isLoading =
                            forecastBundle?.currentForecastState?.isLoading == true ||
                                    forecastBundle?.dayForecastState?.isLoading == true,
                        unit = units,
                    )
                }
            }
        }
    }
}

@Composable
fun CitiesPaging(
    numberOfPages: Int,
    selectedPage: Int,
    modifier: Modifier = Modifier
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier.padding(8.dp)
    ) {
        repeat(numberOfPages) { index ->
            PagingItem(index == selectedPage)
        }
    }
}

@Composable
fun PagingItem(
    isActive: Boolean
) {
    val animationDurationInMillis = 300

    val color: Color by animateColorAsState(
        targetValue = if (isActive) {
            MaterialTheme.colorScheme.primary
        } else {
            Color.LightGray
        },
        animationSpec = tween(
            durationMillis = animationDurationInMillis,
        )
    )
    val width: Dp by animateDpAsState(
        targetValue = if (isActive) {
            32.dp
        } else {
            16.dp
        },
        animationSpec = tween(
            durationMillis = animationDurationInMillis,
        )
    )

    Canvas(
        modifier = Modifier
            .size(
                width = width,
                height = 16.dp,
            ),
    ) {
        drawRoundRect(
            color = color,
            topLeft = Offset.Zero,
            size = Size(
                width = width.toPx(),
                height = 16.dp.toPx(),
            ),
            cornerRadius = CornerRadius(
                x = 16.dp.toPx(),
                y = 16.dp.toPx(),
            ),
        )
    }
}

@Preview(
    showBackground = true,
    backgroundColor = 0xFF929292
)
@Composable
private fun CitiesPagingPreview() {
    CaelumTheme {
        CitiesPaging(7, 2)
    }
}

@Preview(
    showBackground = true,
    backgroundColor = 0xFF7D7A7A
)
@Composable
private fun PagingItemPreview() {
    CaelumTheme {
        Row {
            PagingItem(true)
            PagingItem(false)
        }
    }
}