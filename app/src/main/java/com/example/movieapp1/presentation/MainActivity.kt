package com.example.movieapp1.presentation
import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.movieapp1.presentation.movie_detail.MovieDetailScreen
import com.example.movieapp1.presentation.popular_movies.view.ExploreScreen
import com.example.movieapp1.presentation.popular_movies.view.HomeScreen
import com.example.movieapp1.presentation.search_screen.SearchScreen
import com.example.movieapp1.presentation.theme.MovieAPP1Theme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MovieAPP1Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    MainScreen(navController = navController)
                    //MovieDetailScreen()
                    //HomeScreen()
                }
            }
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainScreen(navController: NavHostController) {
    val items = listOf(
        BottomNavItem.Home,
        BottomNavItem.Search,
        BottomNavItem.Explore
    )

    Scaffold(
        bottomBar = {
            NavigationBar(
                containerColor = Color.Black, // NavigationBar arka plan rengini ayarla
                contentColor = Color.White // İçerik renklerini ayarla
            ) {
                val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route
                items.forEach { item ->
                    NavigationBarItem(
                        icon = {
                            Icon(
                                imageVector = if (currentRoute == item.route) item.selectedIcon else item.unSelectedIcon,
                                contentDescription = item.title,
                                tint = Color.White
                            )
                        },
                        selected = currentRoute == item.route,
                        onClick = {
                            navController.navigate(item.route) {
                                popUpTo(navController.graph.startDestinationId) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )
                }
            }
        }
    ) {padding->
        NavHost(navController, startDestination = BottomNavItem.Home.route) {
            composable(BottomNavItem.Home.route) { HomeScreen(padding,navController) }
            composable(BottomNavItem.Search.route) { SearchScreen(padding,navController) }
            composable(BottomNavItem.Explore.route) { ExploreScreen(navController) }
            composable("movie_detail_screen/{movieId}") { backStackEntry ->
                val movieId = backStackEntry.arguments?.getString("movieId")?.toInt()
                if (movieId != null) {
                    MovieDetailScreen(movieId = movieId)
                }

            }
        }

    }
}

