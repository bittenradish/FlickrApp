package de.example.challenge.flickrapp.di

import android.app.Application
import dagger.Module
import dagger.Provides
import de.example.challenge.flickrapp.domain.usecases.*
import de.example.challenge.flickrapp.fragments.ViewModelFactory
import javax.inject.Singleton

@Module
class AppModule(private val application: Application) {
    @Provides
    @Singleton
    fun provideViewModelFactory(
        deleteRequestUseCase: DeleteRequestUseCase,
        clearHistoryUseCase: ClearHistoryUseCase,
        getHistoryUseCase: GetHistoryUseCase,
        saveRequestUseCase: SaveRequestUseCase,
        searchPhotoUseCase: SearchPhotoUseCase,
    ): ViewModelFactory =
        ViewModelFactory(
            application,
            deleteRequestUseCase,
            clearHistoryUseCase,
            getHistoryUseCase,
            saveRequestUseCase,
            searchPhotoUseCase
        )
}