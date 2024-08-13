package com.example.movieapp1.presentation.popular_movies.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Gray
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import coil.compose.ImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import com.example.movieapp1.domain.model.Movies


@Composable
fun MovieListRow(
    movie : Movies,
    onItemClick : (Movies) -> Unit
) {
    Column(
        modifier = Modifier
            .clickable { onItemClick(movie) },

            ) {
        SubcomposeAsyncImage(
            model = "https://image.tmdb.org/t/p/w500/${movie.poster_path}",
            contentDescription = movie.original_title,
            modifier = Modifier
                .padding(4.dp)
                .height(225.dp) // Sadece yüksekliği belirtiyoruz
                .clip(shape = RectangleShape)
                .align(Alignment.CenterHorizontally),
            loading = { CircularProgressIndicator(modifier = Modifier.align(Center)) },
            contentScale = ContentScale.FillHeight // Yüksekliği doldurur, genişlik orantılı olarak ayarlanır
        )

    }
}
