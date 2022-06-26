package de.example.challenge.flickrapp.di

import de.example.challenge.flickrapp.data.repository.FlickrApiRepositoryImpl
import de.example.challenge.flickrapp.data.repository.UserDataBaseRepositoryImpl
import de.example.challenge.flickrapp.domain.repository.FlickrApiRepository
import de.example.challenge.flickrapp.domain.repository.UserDataBaseRepository
import org.koin.dsl.module

val dataModule = module {
    single<UserDataBaseRepository> {
        UserDataBaseRepositoryImpl()
    }

    single<FlickrApiRepository> {
        FlickrApiRepositoryImpl()
    }
}