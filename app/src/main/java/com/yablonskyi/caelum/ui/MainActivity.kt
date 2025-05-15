package com.yablonskyi.caelum.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.yablonskyi.caelum.ui.theme.CaelumTheme
import com.yablonskyi.caelum.ui.weather.forecast.MainScreen
import com.yablonskyi.caelum.ui.weather.forecast.viewmodel.day.ForecastViewModel
import com.yablonskyi.caelum.ui.weather.list.LocationListViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.serialization.Serializable

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CaelumApp()
        }
    }
}

@Composable
fun CaelumApp(modifier: Modifier = Modifier) {
    CaelumTheme {
        Surface(
            tonalElevation = 5.dp,
            modifier = modifier.fillMaxSize()
        ) {
            val navController = rememberNavController()

            CaelumNavHost(navController)
        }
    }
}

@Composable
fun CaelumNavHost(
    navController: NavHostController,
    forecastViewModel: ForecastViewModel = viewModel(),
    locationListViewModel: LocationListViewModel = viewModel(),
) {
    val forecastList = forecastViewModel.forecastList
    val cityCoordState by forecastViewModel.cityCoordState
    val locationListState by locationListViewModel.state
    val units by forecastViewModel.units

    NavHost(
        navController = navController,
        startDestination = Home
    ) {
        composable<Home> {
            MainScreen(
                forecastList = forecastList,
                locationListState = locationListState,
                units = units,
                onRefresh = { currentCity ->
                    forecastViewModel.getForecastForCity(currentCity.name)
                },
                onListButtonClick = {

                },
                onSettingsButtonClick = {

                },
                onCityChange = { city ->
                    forecastViewModel.getForecastForCity(city.name)
                }
            )
        }
        composable<List> {

        }
        composable<Settings> {

        }
    }
}

@Serializable
object Home

@Serializable
object List

@Serializable
object Settings