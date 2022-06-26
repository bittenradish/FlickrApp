package de.example.challenge.flickrapp.di

import de.example.challenge.flickrapp.fragments.childFragments.HistoryViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel<HistoryViewModel> { parameters ->
        HistoryViewModel(parameters.get(), get(), get(), get())
    }

//    viewModel<SearchViewModel>{ parameters ->
//        SearchViewModel(parameters.get())
//    }
}