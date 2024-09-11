package com.example.movieapp1.presentation.home_screen.view

import androidx.compose.foundation.Image
import com.example.movieapp1.presentation.home_screen.MainScreenViewModel
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
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.movieapp1.R
import com.example.movieapp1.domain.model.Movies


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    paddingValues: PaddingValues,
    navController: NavController,
    viewModel: MainScreenViewModel = hiltViewModel()
) {
    val state = viewModel.state.value
    val scrollState = rememberScrollState()
    var isTopBarVisible by remember { mutableStateOf(true) }
    var lastScrollPosition by remember { mutableIntStateOf(0) }

    LaunchedEffect(scrollState.value) {
        val currentScrollPosition = scrollState.value
        isTopBarVisible = when {
            currentScrollPosition > lastScrollPosition -> false
            currentScrollPosition < lastScrollPosition -> true
            else -> isTopBarVisible
        }
        lastScrollPosition = currentScrollPosition
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        if (isTopBarVisible){
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.app_name),
                        color = Color.White
                    )
                },
                navigationIcon = {
                    Image(painter = painterResource(id = R.mipmap.ic_launcher_foreground), contentDescription = "")
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background,
                    titleContentColor = MaterialTheme.colorScheme.onBackground
                )

            )
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(MaterialTheme.colorScheme.background)
        ) {
            Column(modifier = Modifier.padding(16.dp).verticalScroll(scrollState)) {
                MovieSection(
                    title = stringResource(id = R.string.popular_header),
                    movies = state.popularMovies,
                    onClick = {
                        navController.navigate("movie_detail_screen/${it.id}")
                    }
                )
                Spacer(modifier = Modifier.height(24.dp))

                MovieSection(
                    title = stringResource(id = R.string.daily_trend_header),
                    movies = state.dailyTrendMovieList,
                    onClick = {
                        navController.navigate("movie_detail_screen/${it.id}")
                    }
                )
                Spacer(modifier = Modifier.height(24.dp))

                MovieSection(
                    title = stringResource(id = R.string.vizyon_header),
                    movies = state.nowPlayingMovies,
                    onClick = {
                        navController.navigate("movie_detail_screen/${it.id}")
                    }
                )
                Spacer(modifier = Modifier.height(24.dp))

                MovieSection(
                    title = stringResource(id = R.string.kids_header),
                    movies = state.movieListAccordingToFamilyGenre,
                    onClick = {
                        navController.navigate("movie_detail_screen/${it.id}")
                    }
                )
                Spacer(modifier = Modifier.height(24.dp))

                MovieSection(
                    title = stringResource(id = R.string.adventure_header),
                    movies = state.movieListAccordingToAdventureGenre,
                    onClick = {
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
}

@Composable
fun MovieSection(
    title: String,
    movies: List<Movies>?,
    modifier: Modifier = Modifier,
    onClick: (Movies) -> Unit
) {
    Column(modifier = modifier
        .fillMaxWidth()
        .height(300.dp)) {
        Text(
            text = title,
            color = MaterialTheme.colorScheme.onBackground,
            fontSize = 24.sp,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
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

