package com.example.movieapp1.presentation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Explore
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Explore
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Search
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomNavItem(val route: String, val selectedIcon: ImageVector,val unSelectedIcon: ImageVector, val title: String) {
    object Home : BottomNavItem("home", Icons.Filled.Home,Icons.Outlined.Home, "Ana Sayfa")
    object Search : BottomNavItem("search", Icons.Filled.Search,Icons.Outlined.Search, "Arama")
    object Explore : BottomNavItem("explore", Icons.Filled.Explore,Icons.Outlined.Explore, "Keşfet")
}