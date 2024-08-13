package com.example.movieapp1.presentation.popular_movies.view

import com.example.movieapp1.presentation.popular_movies.MainScreenViewModel
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.rememberScrollState
import androidx.navigation.NavController
import com.example.movieapp1.R
import com.example.movieapp1.domain.model.Movies


@Composable
fun HomeScreen(
    paddingValues: PaddingValues,
    navController: NavController,
    viewModel: MainScreenViewModel = hiltViewModel()
) {
    val state = viewModel.state.value

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .background(Color.Black)
            .verticalScroll(rememberScrollState()) // Dikey kaydırma eklendi
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            MovieSection(
                title = stringResource(id = R.string.popular_header),
                movies = state.popularMovies,
                onClick = {
                    // Tıklama işlemleri
                    navController.navigate("movie_detail_screen/${it.id}")
                }
            )

            Spacer(modifier = Modifier.height(24.dp))

            MovieSection(
                title = "Vizyondaki Filmler",
                movies = state.nowPlayingMovies,
                onClick = {
                    // Tıklama işlemleri
                    navController.navigate("movie_detail_screen/${it.id}")
                }
            )
            Spacer(modifier = Modifier.height(24.dp))

            MovieSection(
                title = "Bugünün Trend Filmleri",
                movies = state.dailyTrendMovieList,
                onClick = {
                    // Tıklama işlemleri
                    navController.navigate("movie_detail_screen/${it.id}")
                }
            )
            Spacer(modifier = Modifier.height(24.dp))

            MovieSection(
                title = "Çocuk Filmleri",
                movies = state.movieListAccordingToFamilyGenre,
                onClick = {
                    // Tıklama işlemleri
                    navController.navigate("movie_detail_screen/${it.id}")
                }
            )
            Spacer(modifier = Modifier.height(24.dp))

            MovieSection(
                title = "Macera Filmleri",
                movies = state.movieListAccordingToAdventureGenre,
                onClick = {
                    // Tıklama işlemleri
                    navController.navigate("movie_detail_screen/${it.id}")
                }
            )


        }

        if (state.error.isNotBlank()) {
            Text(
                text = state.error,
                color = MaterialTheme.colorScheme.error,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .align(Alignment.Center)
            )
        }

        if (state.isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center),
                color = MaterialTheme.colorScheme.primary
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

        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            movies?.let {
                items(it) { movie ->
                    MovieListRow(movie = movie, onItemClick = onClick)
                }
            }
        }
    }
}

