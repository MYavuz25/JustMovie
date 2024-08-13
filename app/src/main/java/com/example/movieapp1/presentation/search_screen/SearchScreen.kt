package com.example.movieapp1.presentation.search_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.room.util.query
import com.example.movieapp1.R
import com.example.movieapp1.domain.model.Movies
import com.example.movieapp1.presentation.popular_movies.MainScreenViewModel
import com.example.movieapp1.presentation.popular_movies.view.MovieListRow


@Composable
fun SearchScreen(
    paddingValues: PaddingValues,
    navController: NavController,
    viewModel : SearchScreenViewModel = hiltViewModel()
) {
    var query by remember { mutableStateOf("") }
    val state = viewModel.state.value

    Box(modifier = Modifier
        .fillMaxSize()
        .padding(paddingValues)
        .background(Color.Black)) {
        Column() {
            MovieSearchBar(modifier = Modifier
                .fillMaxWidth(),
                hint = "Star Wars",
                onSearch = {
                    viewModel.onEvent(SearchScreenEvent.Search(it))
                },

            )

            MovieSection(
                title = "arama sonuçları",
                movies = state.movies,
                onClick = {
                    // Tıklama işlemleri
                    navController.navigate("movie_detail_screen/${it.id}")
                }
            )
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
            //style = MaterialTheme.typography.h6,
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
    viewModel : SearchScreenViewModel = hiltViewModel()
) {
    var text by remember {
        mutableStateOf("")
    }
    var isHintDisplayed by remember {
        mutableStateOf(hint != "")
    }
    val keyboardController = LocalSoftwareKeyboardController.current


    Box(
        modifier = modifier
            .fillMaxWidth()
            .shadow(5.dp, CircleShape)
            .background(Color.White, CircleShape)
            .padding(horizontal = 20.dp)
    ) {
        OutlinedTextField(
            value = text,
            onValueChange = {
                text=it
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
                .padding(horizontal = 20.dp)
                .onFocusChanged {
                    isHintDisplayed = it.isFocused != true && text.isEmpty()
                }
        )
        if (isHintDisplayed) {
            Text(
                text = hint,
                color = Color.LightGray,
                modifier = Modifier
                    .padding(horizontal = 70.dp, vertical = 15.dp)
            )
        }
    }
}


