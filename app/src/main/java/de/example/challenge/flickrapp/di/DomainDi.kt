package de.example.challenge.flickrapp.di

import de.example.challenge.flickrapp.domain.usecases.*
import org.koin.dsl.module

val domainModule = module {

    factory<SaveRequestUseCase> {
        SaveRequestUseCase(userDataBaseRepository = get())
    }

    factory<SearchPhotoUseCase> {
        SearchPhotoUseCase(flickrApiRepository = get())
    }

    factory<DeleteRequestUseCase> {
        DeleteRequestUseCase(userDataBaseRepository = get())
    }

    factory<ClearHistoryUseCase> {
        ClearHistoryUseCase(userDataBaseRepository = get())
    }

    factory<GetHistoryUseCase> {
        GetHistoryUseCase(userDataBaseRepository = get())
    }

}