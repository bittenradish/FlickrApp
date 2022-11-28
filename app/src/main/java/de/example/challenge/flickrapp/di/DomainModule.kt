package de.example.challenge.flickrapp.di

import dagger.Module
import dagger.Provides
import de.example.challenge.flickrapp.domain.repository.FlickrApiRepository
import de.example.challenge.flickrapp.domain.repository.UserDataBaseRepository
import de.example.challenge.flickrapp.domain.usecases.*

@Module
class DomainModule {

    @Provides
    fun provideSaveRequestUseCase(userDataBaseRepository: UserDataBaseRepository): SaveRequestUseCase =
        SaveRequestUseCase(userDataBaseRepository)

    @Provides
    fun provideSearchPhotoUseCase(flickrApiRepository: FlickrApiRepository): SearchPhotoUseCase =
        SearchPhotoUseCase(flickrApiRepository)

    @Provides
    fun provideDeleteRequestUseCase(userDataBaseRepository: UserDataBaseRepository): DeleteRequestUseCase =
        DeleteRequestUseCase(userDataBaseRepository)

    @Provides
    fun provideClearHistoryUseCase(userDataBaseRepository: UserDataBaseRepository): ClearHistoryUseCase =
        ClearHistoryUseCase(userDataBaseRepository)

    @Provides
    fun provideGetHistoryUseCase(userDataBaseRepository: UserDataBaseRepository): GetHistoryUseCase =
        GetHistoryUseCase(userDataBaseRepository)
}