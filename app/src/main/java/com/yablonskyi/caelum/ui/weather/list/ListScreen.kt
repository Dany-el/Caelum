package com.yablonskyi.caelum.ui.weather.list

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.yablonskyi.caelum.domain.model.City
import com.yablonskyi.caelum.ui.theme.CaelumTheme
import com.yablonskyi.caelum.ui.weather.forecast.AppPreview
import com.yablonskyi.caelum.ui.weather.forecast.viewmodel.day.CityCoordState
import com.yablonskyi.caelum.ui.weather.forecast.viewmodel.day.LocationListState
import kotlinx.coroutines.launch
import kotlin.math.roundToInt


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListScreen(
    locationListState: LocationListState,
    cityCoordState: CityCoordState,
    onSearchButtonClicked: (String) -> Unit,
    onAddButtonClicked: (City) -> Unit,
    onDeleteButtonClicked: (City) -> Unit,
    onBackButtonClicked: () -> Unit,
    onStartScreen: () -> Unit,
) {
    var listState: ListState by remember {
        mutableStateOf(ListState.DefaultListState)
    }

    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    // TODO
    LaunchedEffect(Unit) {
        onStartScreen()
    }

    Scaffold(
        snackbarHost = {
            SnackbarHost(snackbarHostState)
        },
        topBar = {
            TopAppBar(
                title = {
                    CitiesSearchBar(
                        onSearch = { city ->
                            listState = ListState.SearchListState
                            onSearchButtonClicked(city)
                        }
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            when (listState) {
                                ListState.DefaultListState -> onBackButtonClicked()
                                ListState.SearchListState -> listState = ListState.DefaultListState
                            }
                        }
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            modifier = Modifier.size(32.dp)
                        )
                    }
                },
                modifier = Modifier.padding(vertical = 16.dp)
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            when (listState) {
                ListState.DefaultListState -> {
                    CitiesList(
                        cityList = locationListState.cities,
                        onDelete = onDeleteButtonClicked
                    )
                }

                ListState.SearchListState -> {
                    if (cityCoordState.isLoading) {
                        LoadingBox()
                    } else {
                        val foundCity: City? = cityCoordState.city

                        if (foundCity == null) {
                            LaunchedEffect(Unit) {
                                scope.launch {
                                    snackbarHostState.showSnackbar(
                                        message = "City not found"
                                    )
                                }
                            }
                        } else {
                            SearchCitiesList(
                                cityList = listOf(foundCity),
                                onAdd = { city ->
                                    scope.launch {
                                        snackbarHostState.showSnackbar(
                                            message = if (locationListState.cities.any {
                                                    it.name == city.name &&
                                                            it.lat == city.lat &&
                                                            it.lon == city.lon
                                                }) {
                                                "City already in your list"
                                            } else {
                                                onAddButtonClicked(city)
                                                "Successfully added"
                                            }
                                        )
                                    }
                                    listState = ListState.DefaultListState
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun LoadingBox(modifier: Modifier = Modifier) {
    Box(
        modifier.fillMaxWidth()
    ) {
        CircularProgressIndicator(
            Modifier
                .size(32.dp)
                .align(Alignment.Center)
        )
    }
}

@Composable
fun ListScreenTopAppBar(
    onBackButtonClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = modifier
            .padding(16.dp)
            .fillMaxWidth()
    ) {
        IconButton(onClick = onBackButtonClicked) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Back",
                modifier = Modifier.size(48.dp)
            )
        }
    }
}

@Composable
fun CitiesSearchBar(
    onSearch: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var textFieldValue by remember {
        mutableStateOf("")
    }

    val focusManager = LocalFocusManager.current

    Box(
        modifier = modifier
            .height(40.dp)
            .fillMaxWidth()
            .border(
                width = 1.dp,
                color = Color.Gray,
                shape = RoundedCornerShape(percent = 50)
            )
            .padding(horizontal = 16.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxSize()
        ) {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "search",
                tint = Color.Gray,
                modifier = Modifier
                    .size(24.dp)
                    .padding(end = 8.dp)
            )

            BasicTextField(
                value = textFieldValue,
                onValueChange = {
                    textFieldValue = it
                },
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Search
                ),
                keyboardActions = KeyboardActions(
                    onSearch = {
                        onSearch(textFieldValue)
                        focusManager.clearFocus()
                    }
                ),
                textStyle = MaterialTheme.typography.bodyMedium,
                decorationBox = { innerTextField ->
                    if (textFieldValue.isEmpty()) {
                        Text(
                            text = "City",
                            color = Color.LightGray,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                    innerTextField()
                },
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
fun CitiesList(
    cityList: List<City>,
    onDelete: (City) -> Unit,
) {
    LazyColumn(
        contentPadding = PaddingValues(vertical = 12.dp),
        modifier = Modifier.fillMaxSize()
    ) {
        itemsIndexed(
            items = cityList,
            key = { _, item -> item.hashCode() }
        ) { _, city ->
            CityItem(
                cityName = city.name,
                onDelete = { onDelete(city) },
            )
        }
    }
}

@Composable
fun CityItem(
    cityName: String,
    onDelete: () -> Unit,
) {

    val scope = rememberCoroutineScope()
    val offsetX = remember { Animatable(0f) }
    val maxOffsetPx = 300f

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
            .padding(vertical = 4.dp)
            .pointerInput(Unit) {
                detectHorizontalDragGestures(
                    onHorizontalDrag = { _, delta ->
                        scope.launch {
                            val newOffset = (offsetX.value + delta).coerceIn(0f, maxOffsetPx)
                            println(newOffset)
                            offsetX.snapTo(newOffset)
                        }
                    },
                    onDragEnd = {
                        scope.launch {
                            val threshold = 20f
                            val target = if (offsetX.value < threshold) 0f else 120f
                            offsetX.animateTo(
                                targetValue = target,
                                animationSpec = tween(durationMillis = 300)
                            )
                        }
                    }
                )
            }
    ) {
        Surface(
            shape = CardDefaults.shape,
            color = Color(0xFFE34566),
            modifier = Modifier
                .align(Alignment.Center)
                .height(70.dp)
                .fillMaxWidth()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp)
            ) {
                IconButton(
                    onClick = onDelete,
                    modifier = Modifier
                        .width(120.dp)
                        .fillMaxHeight()
                ) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Delete",
                        modifier = Modifier.align(Alignment.CenterStart)
                    )
                }
            }
        }

        Box(
            modifier = Modifier
                .offset { IntOffset(offsetX.value.roundToInt(), 0) }
        ) {
            CityCard(
                name = cityName
            )
        }
    }
}

@Composable
fun CityCard(
    name: String,
) {
    Card(
        colors = CardDefaults.cardColors().copy(
            containerColor = MaterialTheme.colorScheme.secondaryContainer
        ),
        modifier = Modifier
            .height(80.dp)
            .fillMaxWidth()
            .shadow(2.dp, CardDefaults.shape)
    ) {
        Box(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxSize()
        ) {
            Text(
                text = name,
                modifier = Modifier
                    .padding(8.dp)
                    .align(Alignment.CenterStart)
            )
        }
    }
}

@Composable
fun SearchCitiesList(
    cityList: List<City>,
    onAdd: (City) -> Unit,
) {
    LazyColumn(
        contentPadding = PaddingValues(vertical = 12.dp),
        modifier = Modifier.fillMaxSize()
    ) {
        itemsIndexed(
            items = cityList,
            key = { _, item -> item.hashCode() }
        ) { _, city ->
            SearchCityItem(
                cityName = city.name,
                onAdd = { onAdd(city) },
            )
        }
    }
}

@Composable
fun SearchCityItem(
    cityName: String,
    onAdd: () -> Unit,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .height(80.dp)
            .fillMaxWidth()
    ) {
        Card(
            colors = CardDefaults.cardColors().copy(
                containerColor = MaterialTheme.colorScheme.secondaryContainer
            ),
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .shadow(2.dp, CardDefaults.shape)
        ) {
            Box(
                modifier = Modifier
                    .padding(8.dp)
            ) {
                Text(
                    text = cityName,
                    modifier = Modifier
                        .padding(8.dp)
                        .align(Alignment.CenterStart)
                )
            }
        }
        Card(
            colors = CardDefaults.cardColors().copy(
                containerColor = MaterialTheme.colorScheme.tertiaryContainer
            ),
            modifier = Modifier
                .fillMaxSize(0.8f)
                .shadow(2.dp, CardDefaults.shape)
                .clickable(
                    onClick = onAdd
                )
        ) {
            Box(
                Modifier.fillMaxSize()
            ) {
                Icon(
                    Icons.Default.Add,
                    contentDescription = "add",
                    modifier = Modifier
                        .size(32.dp)
                        .align(Alignment.Center)
                )
            }
        }
    }
}

@AppPreview
@Composable
private fun CityItemDefaultStatePreview() {
    CaelumTheme {
        CityItem(
            cityName = "Odesa",
            onDelete = {}
        )
    }
}

@AppPreview
@Composable
private fun CityItemSearchStatePreview() {
    CaelumTheme {
        CityItem(
            cityName = "Odesa",
            onDelete = {}
        )
    }
}

@AppPreview
@Composable
private fun CitiesListPreview() {
    val cityListPreview = listOf(
        City(name = "Odesa", lat = 46.4843023, lon = 30.7322878),
        City(name = "Kyiv", lat = 50.4501, lon = 30.5234),
        City(name = "Lviv", lat = 49.8397, lon = 24.0297),
        City(name = "Dnipro", lat = 48.4647, lon = 35.0462),
        City(name = "Kharkiv", lat = 49.9935, lon = 36.2304)
    )

    CaelumTheme {
        CitiesList(
            cityListPreview,
            onDelete = {}
        )
    }
}

@AppPreview
@Composable
private fun SearchBarPreview() {
    CaelumTheme {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            IconButton(onClick = { }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                    modifier = Modifier.size(32.dp)
                )
            }
            CitiesSearchBar(
                {}
            )
        }
    }
}

@AppPreview
@Composable
private fun SearchCityItemPreview() {
    CaelumTheme {
        SearchCityItem(
            cityName = "Odesa",
            {}
        )
    }
}

@AppPreview
@Composable
private fun TopAppBarPreview() {
    CaelumTheme {
        ListScreenTopAppBar(
            {}
        )
    }
}

sealed class ListState {
    data object SearchListState : ListState()
    data object DefaultListState : ListState()
}