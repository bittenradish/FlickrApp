package de.example.challenge.flickrapp.fragments

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import de.example.challenge.flickrapp.domain.usecases.*
import de.example.challenge.flickrapp.fragments.childFragments.HistoryViewModel
import de.example.challenge.flickrapp.fragments.childFragments.search.SearchViewModel
import java.lang.IllegalArgumentException

class ViewModelFactory(
    private val application: Application,
    private val deleteRequestUseCase: DeleteRequestUseCase,
    private val clearHistoryUseCase: ClearHistoryUseCase,
    private val getHistoryUseCase: GetHistoryUseCase,
    private val saveRequestUseCase: SaveRequestUseCase,
    private val searchPhotoUseCase: SearchPhotoUseCase,
    ): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        when{
            modelClass.isAssignableFrom(HistoryViewModel::class.java) ->
                HistoryViewModel(
                    application,
                    deleteRequestUseCase,
                    clearHistoryUseCase,
                    getHistoryUseCase
                ) as T
            modelClass.isAssignableFrom(SearchViewModel::class.java) ->
                SearchViewModel(
                    application,
                    saveRequestUseCase,
                    searchPhotoUseCase
                ) as T

            else -> throw IllegalArgumentException("Unknown ViewModel class")
        }

}