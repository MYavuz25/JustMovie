package com.example.movieapp1.presentation.home_screen.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import com.example.movieapp1.domain.model.Movies
import com.example.movieapp1.R


@Composable
fun MovieListRow(
    movie : Movies,
    onItemClick : (Movies) -> Unit
) {
    Column(
        modifier = Modifier
            .wrapContentHeight()
            .clickable { onItemClick(movie) },

            ) {
        SubcomposeAsyncImage(
            model = "https://image.tmdb.org/t/p/w500/${movie.poster_path}",
            contentDescription = movie.original_title,
            modifier = Modifier
                .padding(4.dp)
                .height(225.dp)
                .clip(shape = RectangleShape)
                .align(Alignment.CenterHorizontally),
            loading = { CircularProgressIndicator(color = Color.White, modifier = Modifier.align(Center)) },
            contentScale = ContentScale.FillHeight
        )
        Row (
            verticalAlignment = Alignment.CenterVertically
        ){
            Spacer(modifier = Modifier.width(4.dp))
            Image(painter = painterResource(id = R.mipmap.ic_launcher_foreground), contentDescription = "",
                modifier = Modifier.size(32.dp))
            Text(text =String.format("%.2f", movie.vote_average), color = MaterialTheme.colorScheme.onBackground)
        }
    }
    
}
