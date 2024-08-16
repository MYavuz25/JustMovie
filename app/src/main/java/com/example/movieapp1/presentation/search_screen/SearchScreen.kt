package com.example.movieapp1.presentation.search_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.material.icons.filled.FilterAlt
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.RangeSlider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.movieapp1.domain.model.Movies
import com.example.movieapp1.presentation.popular_movies.view.MovieListRow
import com.google.accompanist.flowlayout.FlowRow
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    paddingValues: PaddingValues,
    navController: NavController,
    viewModel : SearchScreenViewModel = hiltViewModel()
) {
    val state = viewModel.state.value
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()
    var showBottomSheet by remember { mutableStateOf(false) }
    var isGenreExpanded by remember { mutableStateOf(false) }
    var isLanguageExpanded by remember { mutableStateOf(false) }
    var isReleaseYearExpanded by remember { mutableStateOf(false) }
    var isVoteAverageExpanded by remember { mutableStateOf(false) }
    var isSortByExpanded by remember { mutableStateOf(false) }
    val selectedGenres = remember { mutableStateListOf<String>() }
    var releaseYearSlider by remember { mutableStateOf(1900F..2024F)}
    var voteAverageSlider by remember { mutableStateOf(0F..10F)}

    val genres = arrayListOf(
        "12",
        "28",
        "Animation",
        "Comedy",
        "Crime",
        "Documentary",
        "Drama",
        "Family",
        "Fantasy",
        "History",
        "Horror",
        "Music",
        "Mystery",
        "Romance",
        "Science Fiction",
        "TV Movie",
        "Thriller",
        "War",
        "Western"
    )
    val languages = arrayListOf(
        "English",
        "France",
        "German",
        "Turkish"
    )
    val sortOptions = listOf(
        "popularity",
        "primary_release_date",
        "vote_average",
        "original_title"
    )
    var selectedSortOption by remember { mutableStateOf(sortOptions[0]) }
    var selectedLanguage by remember { mutableStateOf(languages[0]) }

    Box(modifier = Modifier
        .fillMaxSize()
        .padding(paddingValues)
        .background(Color.Black)) {
        Column {
            Row{
                MovieSearchBar(
                    hint = "Star Wars",
                    onSearch = {
                        viewModel.onEvent(SearchScreenEvent.Search(it))
                    },
                    onFilterClick = {
                        showBottomSheet=true
                    }
                    )
            }

            MovieSection(
                title = "arama sonuçları",
                movies = state.movies,
                onClick = {
                    // Tıklama işlemleri
                    navController.navigate("movie_detail_screen/${it.id}")
                }
            )

            Scaffold{ contentPadding ->
                // Screen content
                Box(modifier = Modifier.padding(contentPadding)) {
                    
                }
                if (showBottomSheet) {
                    ModalBottomSheet(
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight(),
                        onDismissRequest = {
                            showBottomSheet = false
                        },
                        containerColor = Color.Black,
                        sheetState = sheetState
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                                .verticalScroll(rememberScrollState())

                        ) {

                        // Sheet content
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)

                        ) {
                            MyCard(
                                title = "Genre",
                                isExpanded = isGenreExpanded,
                                onExpandChange = { isGenreExpanded = !isGenreExpanded }
                            ) {
                                FlowRow(
                                    mainAxisSpacing = 8.dp,
                                    crossAxisSpacing = 8.dp
                                ) {
                                    genres.forEach { genre ->
                                        FilterChip(
                                            selected = selectedGenres.contains(genre),
                                            onClick = {
                                                if (selectedGenres.contains(genre)) {
                                                    selectedGenres.remove(genre)
                                                } else {
                                                    selectedGenres.add(genre)
                                                }
                                            },
                                            label = { Text(genre, color = Color.LightGray) }
                                        )
                                    }
                                }
                            }

                            MyCard(
                                title = "Original Language",
                                isExpanded = isLanguageExpanded,
                                onExpandChange = { isLanguageExpanded = !isLanguageExpanded }
                            ) {
                                FlowRow(
                                    mainAxisSpacing = 8.dp,
                                    crossAxisSpacing = 8.dp
                                ) {
                                    languages.forEach { language ->
                                        Row(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .clickable { selectedLanguage = language }
                                                .padding(vertical = 8.dp),
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            RadioButton(
                                                selected = selectedLanguage == language,
                                                onClick = { selectedLanguage = language },
                                                colors = RadioButtonDefaults.colors(
                                                    selectedColor = Color(
                                                        0xFFFFC107
                                                    )
                                                )
                                            )
                                            Spacer(modifier = Modifier.width(8.dp))
                                            Text(text = language, color = Color.White)
                                        }
                                    }
                                }
                            }

                            MyCard(
                                title = "Release Year",
                                isExpanded = isReleaseYearExpanded,
                                onExpandChange = { isReleaseYearExpanded = !isReleaseYearExpanded }
                            ) {
                                Spacer(modifier = Modifier.height(16.dp))
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Text(
                                        text = "${releaseYearSlider.start.toInt()}",
                                        color = Color.White
                                    )
                                    RangeSlider(
                                        value = releaseYearSlider,
                                        onValueChange = { releaseYearSlider = it },
                                        valueRange = 1900f..2024f,
                                        colors = SliderDefaults.colors(
                                            thumbColor = Color(0xFFFFC107),
                                            activeTrackColor = Color(0xFFFFC107)
                                        ),
                                        modifier = Modifier.weight(1f)
                                    )
                                    Text(
                                        text = "${releaseYearSlider.endInclusive.toInt()}",
                                        color = Color.White
                                    )
                                }
                            }

                            MyCard(
                                title = "Vote average",
                                isExpanded = isVoteAverageExpanded,
                                onExpandChange = { isVoteAverageExpanded = !isVoteAverageExpanded }
                            ) {
                                Spacer(modifier = Modifier.height(16.dp))
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Text(
                                        text = "%.1f".format(voteAverageSlider.start),
                                        color = Color.White
                                    )
                                    RangeSlider(
                                        value = voteAverageSlider,
                                        onValueChange = { voteAverageSlider = it },
                                        valueRange = 0F..10F,
                                        steps = 100,
                                        colors = SliderDefaults.colors(
                                            thumbColor = Color(0xFFFFC107),
                                            activeTrackColor = Color(0xFFFFC107)
                                        ),
                                        modifier = Modifier.weight(1f)
                                    )
                                    Text(
                                        text = "%.1f".format(voteAverageSlider.endInclusive),
                                        color = Color.White
                                    )
                                }
                            }
                            MyCard(
                                title = "Sort By",
                                isExpanded = isSortByExpanded,
                                onExpandChange = { isSortByExpanded = !isSortByExpanded }
                            ) {
                                Spacer(modifier = Modifier.height(16.dp))
                                sortOptions.forEach { option ->
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .clickable { selectedSortOption = option }
                                            .padding(vertical = 8.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        RadioButton(
                                            selected = selectedSortOption == option,
                                            onClick = { selectedSortOption = option },
                                            colors = RadioButtonDefaults.colors(
                                                selectedColor = Color(
                                                    0xFFFFC107
                                                )
                                            )
                                        )
                                        Spacer(modifier = Modifier.width(8.dp))
                                        Text(text = option, color = Color.White)
                                    }
                                }
                            }
                        }


                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Button(onClick = {
                                selectedSortOption=sortOptions[0]
                                selectedGenres.clear()
                                selectedLanguage=languages[0]
                                releaseYearSlider = 1900F..2024F
                                voteAverageSlider=0F..10F
                            }) {
                                Text("Clear all")
                            }
                            Button(onClick = {
                                // Filtreleri uygula

                                viewModel.getFilteredMovies(
                                    "$selectedSortOption.desc", // Sıralama kriteri (örneğin: "popularity" veya "release_date")
                                    selectedGenres.joinToString(","), // Türleri virgülle ayırarak String formatında gönder
                                    voteAverageSlider.start, // Minimum oy ortalaması
                                    voteAverageSlider.endInclusive, // Maksimum oy ortalaması
                                    "${releaseYearSlider.start.toInt()}-01-01", // En erken çıkış tarihi
                                    "${releaseYearSlider.endInclusive.toInt()}-12-31" // En geç çıkış tarihi
                                )
                                println(selectedSortOption)


                                scope.launch { sheetState.hide() }.invokeOnCompletion {

                                    if (!sheetState.isVisible) {
                                        showBottomSheet = false
                                    }
                                }
                            }) {
                                Text("Apply")
                            }
                        }
                     }
                    }
                }
            }
        }
    }
}
@Composable
fun MyCard(
    title: String,
    isExpanded: Boolean,
    onExpandChange: () -> Unit,
    content: @Composable () -> Unit
) {
    var isExpandedState by remember { mutableStateOf(isExpanded) }
    Card(
        modifier = Modifier
            .padding(4.dp)
            .fillMaxWidth()
            .background(Color.Black)
            .border(1.dp, Color(0xFFFFD700)),
        colors = CardDefaults.cardColors(containerColor = Color.Black),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth()
                    .clickable {
                    isExpandedState = !isExpandedState
                    onExpandChange()
                },
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = title,
                    color = Color.LightGray
                )
                Icon(
                    imageVector = if (isExpandedState) Icons.Filled.ArrowDropUp else Icons.Filled.ArrowDropDown,
                    contentDescription = null,
                    tint = Color.LightGray
                )
            }

            if (isExpandedState) {
                Spacer(modifier = Modifier.height(16.dp))
                content()
            }
        }
    }
}

@Composable
fun MovieSection(
    title: String,
    movies: List<Movies>?,
    modifier: Modifier = Modifier,
    onClick: (Movies) -> Unit
) {
    Column(modifier = modifier.fillMaxWidth()) {
        Text(
            text = title,
            color = Color.White,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier
                .fillMaxSize(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            movies?.let {
                items(it) { movie ->
                    MovieListRow(movie = movie, onItemClick = onClick)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieSearchBar(
    modifier: Modifier = Modifier,
    hint: String = "",
    onSearch: (String) -> Unit = {},
    onFilterClick: () -> Unit = {},
    viewModel : SearchScreenViewModel = hiltViewModel()
) {
    var text by remember {
        mutableStateOf("")
    }
    var isHintDisplayed by remember {
        mutableStateOf(hint != "")
    }
    val keyboardController = LocalSoftwareKeyboardController.current

    Row(
        modifier = modifier
            .shadow(5.dp, CircleShape)
            .background(Color.White, CircleShape)
            .padding(horizontal = 8.dp, vertical = 8.dp)
    ) {
        Box(
            modifier = Modifier
                .weight(1f)
                .padding(end = 8.dp)
        ) {
            OutlinedTextField(
                value = text,
                onValueChange = {
                    text = it
                    viewModel.onEvent(SearchScreenEvent.Search(text))
                },
                leadingIcon = {
                    Icon(Icons.Filled.Search, contentDescription = "Search")
                },
                maxLines = 1,
                singleLine = true,
                textStyle = TextStyle(color = Color.Black),
                shape = CircleShape,
                keyboardActions = KeyboardActions(onDone = {
                    onSearch(text)
                    keyboardController?.hide()
                }),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color.Transparent,
                    unfocusedBorderColor = Color.Transparent
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .onFocusChanged {
                        isHintDisplayed = it.isFocused != true && text.isEmpty()
                    }
            )

            if (isHintDisplayed) {
                Text(
                    text = hint,
                    color = Color.LightGray,
                    modifier = Modifier
                        .padding(horizontal = 40.dp, vertical = 15.dp)
                )
            }
        }

        IconButton(
            onClick = onFilterClick,
            modifier = Modifier.align(Alignment.CenterVertically)
        ) {
            Icon(imageVector = Icons.Filled.FilterAlt, contentDescription = "Filter")
        }
    }
}
