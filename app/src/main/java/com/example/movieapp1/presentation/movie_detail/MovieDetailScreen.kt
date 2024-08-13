package com.example.movieapp1.presentation.movie_detail


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.SubcomposeAsyncImage

@Composable
fun MovieDetailScreen(
    movieId: Int,
    movieDetailViewModel: MovieDetailViewModel = hiltViewModel()
) {
    // ViewModel, movieId'yi alır ve gerekli veriyi çeker
    LaunchedEffect(movieId) {
        movieDetailViewModel.getMovieDetail(movieId)
    }

    val state = movieDetailViewModel.state.value

    // Ekran düzenini oluştur
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black),
        contentAlignment = Center
    ) {
        state.movie?.let {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = CenterHorizontally
            ) {
                SubcomposeAsyncImage(
                    model = "https://image.tmdb.org/t/p/w500/${it.poster_path}",
                    contentDescription = it.original_title,
                    modifier = Modifier
                        .padding(16.dp)
                        .size(400.dp, 400.dp)
                        .clip(shape = RectangleShape)
                        .align(CenterHorizontally),
                    loading = { CircularProgressIndicator() }
                )
                Text(
                    text = it.original_title,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(14.dp),
                    color = Color.White
                )
                Text(
                    text = it.release_date,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(14.dp),
                    color = Color.White
                )
                Text(
                    text = "IMDb Puanı: ${it.vote_average}",
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(14.dp),
                    color = Color.White
                )
            }
        }

        if (state.error.isNotBlank()) {
            Text(
                text = state.error,
                color = MaterialTheme.colorScheme.error,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(14.dp)
                    .align(Center)
            )
        }

        if (state.isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Center))
        }
    }
}
