package com.example.movieapp1.data.di

import com.example.movieapp1.data.remote.retrofit.MovieApi
import com.example.movieapp1.data.repository.MovieRepositoryImp
import com.example.movieapp1.domain.repository.MovieRepository
import com.example.movieapp1.util.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideApi():MovieApi{
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MovieApi::class.java)
    }
    @Provides
    @Singleton
    fun provideMovieDetailRepository(api:MovieApi) : MovieRepository{
        return MovieRepositoryImp(api)
    }


}