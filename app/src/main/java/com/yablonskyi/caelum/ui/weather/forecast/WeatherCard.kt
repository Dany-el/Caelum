package com.yablonskyi.caelum.ui.weather.forecast

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yablonskyi.caelum.R
import com.yablonskyi.caelum.common.Units
import com.yablonskyi.caelum.common.distanceUnit
import com.yablonskyi.caelum.common.humidityUnit
import com.yablonskyi.caelum.common.pressureUnit
import com.yablonskyi.caelum.common.temperatureUnit
import com.yablonskyi.caelum.common.windSpeedUnit
import com.yablonskyi.caelum.domain.model.Clouds
import com.yablonskyi.caelum.domain.model.DayForecast
import com.yablonskyi.caelum.domain.model.ForecastItem
import com.yablonskyi.caelum.domain.model.HourForecast
import com.yablonskyi.caelum.domain.model.Main
import com.yablonskyi.caelum.domain.model.Weather
import com.yablonskyi.caelum.domain.model.Wind
import com.yablonskyi.caelum.ui.theme.CaelumTheme
import kotlinx.coroutines.delay
import kotlin.math.roundToInt

@Composable
fun WeatherCard(
    hourForecast: HourForecast?,
    dayForecast: DayForecast?,
    isLoading: Boolean,
    unit: Units,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.fillMaxSize()
    ) {
        if (isLoading || hourForecast == null || dayForecast == null) {
            Box(
                Modifier.fillMaxSize()
            ) {
/*                CircularProgressIndicator(
                    modifier = Modifier.width(64.dp).align(Alignment.TopCenter),
                    color = MaterialTheme.colorScheme.secondary,
                    trackColor = MaterialTheme.colorScheme.surfaceVariant,
                )*/
                LaunchedEffect(Unit) {
                    delay(2000L)
                }
            }
        } else {
            WeatherOverview(
                temp = hourForecast.main.temp.roundToInt(),
                summary = hourForecast.weather[0].main,
                description = hourForecast.weather[0].description,
                sunrise = hourForecast.sunrise,
                sunset = hourForecast.sunset,
                timezone = hourForecast.timezone,
            )
            HourlyForecastList(
                list = dayForecast.list,
                sunrise = hourForecast.sunrise,
                sunset = hourForecast.sunset,
                timezone = hourForecast.timezone,
            )
            AdditionalWeatherData(
                feelsLike = hourForecast.main.feelsLike.roundToInt(),
                pressure = hourForecast.main.pressure,
                humidity = hourForecast.main.humidity,
                speed = hourForecast.wind.speed.roundToInt(),
                deg = hourForecast.wind.deg,
                visibility = hourForecast.visibility,
                cloudsPercentage = hourForecast.clouds.all,
                sunrise = hourForecast.sunrise,
                sunset = hourForecast.sunset,
                timezone = hourForecast.timezone,
                unit = unit
            )
        }
    }
}

@Composable
fun WeatherOverview(
    temp: Int,
    summary: String,
    description: String,
    sunrise: Long,
    sunset: Long,
    timezone: Long
) {
    val now = System.currentTimeMillis()

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(vertical = 32.dp)
            .fillMaxWidth()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier
                .padding(vertical = 8.dp)
                .fillMaxWidth()
        ) {
            Icon(
                painter = painterResource(
                    summary.toIcon(
                        description = description,
                        now, sunrise, sunset, timezone
                    )
                ),
                contentDescription = "",
                modifier = Modifier.size(64.dp)
            )
            Text(
                text = "$temp${Typography.degree}",
                style = MaterialTheme.typography.titleLarge,
            )
        }
        Text(
            text = summary
        )
    }
}

@Composable
fun HourlyForecastList(
    list: List<ForecastItem>,
    sunrise: Long,
    sunset: Long,
    timezone: Long,
) {
    Card(
        Modifier
            .padding(8.dp)
            .fillMaxWidth()
    ) {
        LazyRow(
            modifier = Modifier.fillMaxWidth()
        ) {
            items(list) { forecast ->
                HourlyForecastItem(
                    time = forecast.dt,
                    temp = forecast.main.temp.roundToInt(),
                    sunrise = sunrise,
                    sunset = sunset,
                    timezone = timezone,
                    summary = forecast.weather[0].main,
                    description = forecast.weather[0].description
                )
            }
        }
    }
}

@Composable
fun HourlyForecastItem(
    time: Long,
    temp: Int,
    sunrise: Long,
    sunset: Long,
    timezone: Long,
    summary: String,
    description: String
) {
    val now = System.currentTimeMillis() / 1000 + timezone
    // TODO
    val isCurrent = now >= time

    Column(
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .height(120.dp)
    ) {
        Text(
            text = time.toHourMinuteTime() // TODO
        )
        Icon(
            painter = painterResource(
                summary.toIcon(
                    description = description,
                    time, sunrise, sunset, timezone
                )
            ),
            contentDescription = "weather icon",
            modifier = Modifier.size(32.dp)
        )
        Text(
            text = "$temp${Typography.degree}"
        )
    }
}

@Composable
fun AdditionalWeatherData(
    feelsLike: Int,
    pressure: Int,
    humidity: Int,
    speed: Int,
    deg: Int, // TODO
    visibility: Int,
    cloudsPercentage: Int,
    sunrise: Long,
    sunset: Long,
    timezone: Long,
    unit: Units
) {
    Column(
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            // Visibility
            AdditionalWeatherItem(
                iconId = R.drawable.visibility,
                title = "visibility",
                data = visibility / 1000,
                units = unit.distanceUnit()
            )
            // Feels like
            AdditionalWeatherItem(
                iconId = R.drawable.temperature,
                title = "feels like",
                data = feelsLike,
                units = unit.temperatureUnit().toString()
            )
            // Humidity
            AdditionalWeatherItem(
                iconId = R.drawable.humidity,
                title = "humidity",
                data = humidity,
                units = unit.humidityUnit()
            )
        }
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            // Pressure
            AdditionalWeatherItem(
                iconId = R.drawable.pressure,
                title = "pressure",
                data = pressure,
                units = unit.pressureUnit()
            )
            // Wind speed TODO
            AdditionalWeatherItem(
                iconId = R.drawable.wind,
                title = "wind",
                data = speed,
                units = unit.windSpeedUnit()
            )
            // Clouds
            AdditionalWeatherItem(
                iconId = R.drawable.clouds_2,
                title = "Clouds",
                data = cloudsPercentage,
                units = unit.humidityUnit()
            )
        }
        SunPosition(sunrise, sunset, timezone)
    }
}

@Composable
fun AdditionalWeatherItem(
    iconId: Int,
    title: String,
    data: Int,
    units: String
) {
    Card(
        modifier = Modifier
            .padding(4.dp)
            .height(100.dp)
            .width(120.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth()
        ) {
            Icon(
                painter = painterResource(iconId),
                contentDescription = "icon",
                modifier = Modifier
                    .padding(end = 4.dp)
                    .size(24.dp)
            )
            Text(
                text = title.uppercase(),
                style = MaterialTheme.typography.bodyMedium
            )
        }
        Box(
            modifier = Modifier
                .padding(horizontal = 8.dp)
        ) {
            Text(
                text = "$data $units",
            )
        }
    }
}

@Composable
fun SunPosition(
    sunrise: Long,
    sunset: Long,
    timezone: Long,
) {
    val now = System.currentTimeMillis() / 1000
    val percentage = (((now - sunrise).toFloat() / (sunset - sunrise)) * 100) / 100
    val progress = percentage.coerceIn(0f, 1f)

    Card(
        Modifier
            .padding(8.dp)
            .fillMaxWidth()
    ) {
        Column(
            Modifier.fillMaxWidth()
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth()
            ) {
                Icon(
                    painter = painterResource(R.drawable.sunrise),
                    contentDescription = "sunrise",
                )
                Icon(
                    painter = painterResource(R.drawable.sunset),
                    contentDescription = "sunset"
                )
            }
            LinearProgressIndicator(
                progress = { progress },
                trackColor = Color.White,
                drawStopIndicator = {},
                modifier = Modifier
                    .padding(8.dp)
                    .height(8.dp)
                    .fillMaxWidth()
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth()
            ) {
                Text(
                    text = sunrise.toHourMinuteTime(timezone)
                )
                Text(
                    text = sunset.toHourMinuteTime(timezone)
                )
            }
        }

    }
}


@AppPreview
@Composable
private fun WeatherOverviewPreview() {
    CaelumTheme {
        WeatherOverview(
            26,
            "Clear",
            "few clouds",
            1747275813,
            1747329809,
            10800
        )
    }
}

@AppPreview
@Composable
private fun AdditionalWeatherDataPreview() {
    CaelumTheme {
        AdditionalWeatherData(
            25,
            1000,
            30,
            2,
            30,
            10000,
            0,
            1747275813,
            1747329809,
            10800,
            Units.METRICS
        )
    }
}

@AppPreview
@Composable
private fun AdditionalWeatherItemPreview() {
    CaelumTheme {
        AdditionalWeatherItem(
            iconId = R.drawable.visibility,
            title = "Visibility",
            data = 10,
            units = Units.METRICS.distanceUnit()
        )
    }
}

@AppPreview
@Composable
private fun SunPositionPreview() {
    CaelumTheme {
        SunPosition(
            1747275813,
            1747329809,
            10800,
        )
    }
}

@AppPreview
@Composable
private fun HourlyForecastListPreview() {
    CaelumTheme {
        HourlyForecastList(
            previewList,
            sunrise = 1747275813,
            sunset = 1747329809,
            timezone = 10800,
        )
    }
}

@AppPreview
@Composable
private fun HourlyForecastItemPreview() {
    CaelumTheme {
        Row {
            HourlyForecastItem(
                time = 1747321200,
                sunrise = 1747275813,
                sunset = 1747329809,
                timezone = 10800,
                temp = 23,
                summary = "Clear",
                description = "few clouds"
            )
            HourlyForecastItem(
                time = 1747332000,
                sunrise = 1747275813,
                sunset = 1747329809,
                timezone = 10800,
                temp = 25,
                summary = "Clear",
                description = "few clouds"
            )
        }
    }
}

@Preview(showBackground = true)
annotation class AppPreview

val previewList = List(8) { index ->
    val baseTime = System.currentTimeMillis() / 1000 // UTC time in seconds
    val dt = baseTime + index * 10800

    ForecastItem(
        dt = dt,
        dtString = "",
        main = Main(
            temp = 15.0 + index,
            feelsLike = 14.5 + index,
            pressure = 1013 + index,
            humidity = 60 + index
        ),
        weather = listOf(
            Weather(
                main = "Clouds",
                description = "Partly cloudy"
            )
        ),
        clouds = Clouds(all = 20 + index),
        wind = Wind(
            speed = 3.5 + index,
            deg = 90 + (index * 10)
        ),
        visibility = 10000 - (index * 500),
        pop = 0.1 * index
    )
}
