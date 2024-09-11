package com.example.movieapp1.data.remote.dto.movie_watch_providers

data class WatchProvidersResponse(
    val id: Int,
    val results: Map<String, CountryWatchProviders>
)

data class CountryWatchProviders(
    val link: String?,
    val flatrate: List<Provider>?,
    val rent: List<Provider>?,
    val buy: List<Provider>?
)

data class Provider(
    val provider_name: String,
    val logo_path: String?
)
