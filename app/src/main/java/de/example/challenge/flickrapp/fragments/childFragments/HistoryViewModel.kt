package de.example.challenge.flickrapp.fragments.childFragments

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import de.example.challenge.flickrapp.data.repository.database.RequestHistoryModel
import de.example.challenge.flickrapp.domain.usecases.ClearHistoryUseCase
import de.example.challenge.flickrapp.domain.usecases.DeleteRequestUseCase
import de.example.challenge.flickrapp.domain.usecases.GetHistoryUseCase

class HistoryViewModel(
    application: Application,
    private val deleteRequestUseCase: DeleteRequestUseCase,
    private val clearHistoryUseCase: ClearHistoryUseCase,
    private val getHistoryUseCase: GetHistoryUseCase
) : AndroidViewModel(application) {

    private var requestHistoryLiveData: LiveData<List<RequestHistoryModel>> =
        getHistoryUseCase.execute()

    fun getRequestsHistory(): LiveData<List<RequestHistoryModel>> {
        return requestHistoryLiveData
    }

    fun deleteRequest(requestText: String) {
        deleteRequestUseCase.execute(requestText)
    }

    fun clearRequestHistory() {
        clearHistoryUseCase.execute()
    }
}