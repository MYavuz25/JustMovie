package com.example.movieapp1.presentation.movie_detail


import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.wear.compose.material.ButtonDefaults
import androidx.wear.compose.material.OutlinedButton
import coil.compose.SubcomposeAsyncImage
import com.example.movieapp1.domain.model.Movies
import com.example.movieapp1.presentation.home_screen.view.MovieListRow

@OptIn(ExperimentalLayoutApi::class, ExperimentalMaterial3Api::class)
@Composable
fun MovieDetailScreen(
    movieId: Int,
    navController: NavController,
    paddingValues: PaddingValues,
    movieDetailViewModel: MovieDetailViewModel = hiltViewModel()
) {
    var isExpanded by remember { mutableStateOf(false) }
    val state = movieDetailViewModel.state.value
    val scrollState = rememberScrollState()
    LaunchedEffect(movieId) {
        movieDetailViewModel.getMovieDetail(movieId)
    }
        Column(modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(paddingValues)
            .verticalScroll(scrollState)
            ) {
            state.movie?.let {
                LaunchedEffect (movieId){
                    movieDetailViewModel.getSimilarMovies(movieId)
                }
                Column(
                    horizontalAlignment = CenterHorizontally
                ) {
                    Box {
                        SubcomposeAsyncImage(
                            model = "https://image.tmdb.org/t/p/w500/${it.backdrop_path}",
                            contentDescription = it.original_title,
                            modifier = Modifier
                                .padding(8.dp)
                                .fillMaxWidth()
                                .height(250.dp)
                                .clip(shape = RectangleShape)
                                .align(alignment = Center),
                            loading = {
                                CircularProgressIndicator(
                                    modifier = Modifier.fillMaxSize(),
                                    color = Color.White,
                                )
                            }
                        )
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .align(Alignment.BottomStart)
                                .padding(16.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "${it.release_date.take(4)} | Vote ${
                                    String.format(
                                        "%.2f",
                                        it.vote_average
                                    )
                                } (${it.vote_count} votes)",
                                color = Color.White,
                                fontSize = 14.sp
                            )
                        }
                    }
                    Text(
                        text = it.overview,
                        color = Color.White,
                        fontSize = 14.sp,
                        modifier = Modifier.padding(8.dp),
                        maxLines = if (!isExpanded) 5 else it.overview.length,
                        overflow = TextOverflow.Ellipsis
                    )

                    OutlinedButton(
                        onClick = { isExpanded = !isExpanded },
                        border = ButtonDefaults.buttonBorder(
                            borderStroke = BorderStroke(
                                1.dp,
                                color = MaterialTheme.colorScheme.secondary
                            )
                        ),
                        modifier = Modifier
                            .width(200.dp)
                            .clip(RoundedCornerShape(4.dp))
                    ) {
                        Text(
                            text = if (isExpanded) "Daha Az Göster" else "Daha Fazla Göster",
                            color = MaterialTheme.colorScheme.onBackground,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(8.dp)
                        )
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(color = Color.DarkGray)
                            .align(Alignment.Start)
                            .padding(2.dp)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {

                            Text(text = "Filmin Türü: ", modifier = Modifier.width(100.dp))
                            FlowRow {
                                it.genres.forEach {
                                    FilterChip(
                                        modifier = Modifier.padding(2.dp),
                                        selected = false,
                                        onClick = {

                                        },
                                        label = { Text(it.name, color = Color.LightGray) }
                                    )
                                }
                            }
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Start
                        ) {

                            Text(text = "Yapımcı Ülke: ", modifier = Modifier.width(100.dp))
                            FlowRow {
                                it.production_countries.forEach{
                                    FilterChip(
                                        modifier = Modifier.padding(2.dp),
                                        selected = false,
                                        onClick = {},
                                        label = { Text(it.iso_3166_1, color = Color.LightGray) }
                                    )
                                }
                            }
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Start
                        ) {

                            Text(text = "Orjinal Dili: ", modifier = Modifier.width(100.dp))
                            FlowRow {
                                it.original_language?.let {
                                    FilterChip(
                                        selected = false,
                                        onClick = {},
                                        label = { Text(it, color = Color.LightGray) }
                                    )

                                }
                            }
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Start
                        ) {

                            Text(text = "Süre: ", modifier = Modifier.width(100.dp))
                            FilterChip(
                                selected = false,
                                onClick = { },
                                label = {
                                    Text(
                                        it.runtime.toString() + " dk",
                                        color = Color.LightGray
                                    )
                                }
                            )
                        }

                    }
                    if (state.similarMoviesList?.isNotEmpty() == true){
                        MovieSection(title = "Benzerler Filmler",
                            movies =state.similarMoviesList ,
                            onClick = {
                                navController.navigate("movie_detail_screen/${it.id}")
                            })
                    }else{
                        val genres:ArrayList<Int> = ArrayList()
                        it.genres.forEach{
                            genres.add(it.id)
                        }
                        if (genres.isNotEmpty()){
                            val genresString = genres.joinToString(separator = ",")
                            LaunchedEffect(genresString) {
                                movieDetailViewModel.getFilteredWithGenre(genresString)
                            }
                            state.genreMovieList?.let {
                                MovieSection(title = "Benzerler Filmler", movies =it ) {
                                    navController.navigate("movie_detail_screen/${it.id}")
                                }
                            }
                        }



                    }
                }
            }
            if (state.error.isNotBlank()) {
                println(state.error)
                Text(
                    text = state.error,
                    color = MaterialTheme.colorScheme.error,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(14.dp)
                        .align(CenterHorizontally)
                )
            }
            if (state.isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(alignment = CenterHorizontally), color = Color.White)
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
        .wrapContentHeight()) {
        Text(
            text = title,
            color = MaterialTheme.colorScheme.onBackground,
            fontSize = 24.sp,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        LazyRow(
            modifier = Modifier
                .fillMaxWidth(),
            //.height(250.dp),
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

