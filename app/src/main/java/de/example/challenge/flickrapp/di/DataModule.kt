package de.example.challenge.flickrapp.di

import dagger.Module
import dagger.Provides
import de.example.challenge.flickrapp.data.repository.FlickrApiRepositoryImpl
import de.example.challenge.flickrapp.data.repository.UserDataBaseRepositoryImpl
import de.example.challenge.flickrapp.domain.repository.FlickrApiRepository
import de.example.challenge.flickrapp.domain.repository.UserDataBaseRepository
import javax.inject.Singleton

@Module
class DataModule {
    @Provides
    @Singleton
    fun provideUserDataBaseRepository(): UserDataBaseRepository =
        UserDataBaseRepositoryImpl()

    @Provides
    @Singleton
    fun provideFlickrApiRepository(): FlickrApiRepository =
        FlickrApiRepositoryImpl()
}