package de.example.challenge.flickrapp.fragments.childFragments.search

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import de.example.challenge.flickrapp.R
import de.example.challenge.flickrapp.application.App
import de.example.challenge.flickrapp.data.repository.flickrapi.ResponseCode
import de.example.challenge.flickrapp.data.repository.flickrapi.models.PhotoModel
import de.example.challenge.flickrapp.data.repository.flickrapi.models.SortEnum
import de.example.challenge.flickrapp.domain.usecases.SaveRequestUseCase
import de.example.challenge.flickrapp.domain.usecases.SearchPhotoUseCase
import kotlinx.coroutines.launch
import org.koin.java.KoinJavaComponent.get


class SearchViewModel(application: Application) : AndroidViewModel(application) {
    private var photosLiveData: MutableLiveData<List<PhotoModel>>? = null
    private var searchStateLiveData: MutableLiveData<SearchState> =
        MutableLiveData(SearchState.READY)
    private var responseCodeLiveData: MutableLiveData<ResponseCode> =
        MutableLiveData(ResponseCode.RESPONSE_OK)
    private var requestStringLiveData: MutableLiveData<String> = MutableLiveData()
    private var sortPositionLiveData: MutableLiveData<SortEnum> =
        MutableLiveData(SortEnum.RELEVANCE)
    private var currentPage: Int = 1
    private val saveRequestUseCase: SaveRequestUseCase = get(SaveRequestUseCase::class.java)
    private val searchPhotoUseCase: SearchPhotoUseCase = get(SearchPhotoUseCase::class.java)

    fun getPhotosLiveData(): LiveData<List<PhotoModel>> {
        if (photosLiveData == null) {
            photosLiveData = MutableLiveData()
            photosLiveData?.postValue(listOf<PhotoModel>())
        }
        return photosLiveData!!
    }

    fun getSearchStateLiveDate(): LiveData<SearchState> {
        return searchStateLiveData
    }

    fun getRequestStringLiveData(): LiveData<String> {
        return requestStringLiveData
    }

    fun getResponseCodeLiveData(): LiveData<ResponseCode> {
        return responseCodeLiveData
    }

    fun getSortPositionObserver(): LiveData<SortEnum> {
        return sortPositionLiveData
    }

    fun observerGotTheMessage() {
        responseCodeLiveData.postValue(ResponseCode.RESPONSE_OK)
    }

    fun searchFor(requestText: String, sort: SortEnum = SortEnum.RELEVANCE) {
        saveRequestUseCase.execute(requestText)
        sortPositionLiveData.postValue(sort)
        searchStateLiveData.postValue(SearchState.SEARCHING)
        photosLiveData?.postValue(listOf<PhotoModel>())
        currentPage = 1
        requestStringLiveData.postValue(requestText)
        newSearchPhotos(requestText = requestText, sort = sort)
    }

    fun loadMore() {
        if (searchStateLiveData.value!! == SearchState.READY) {
            searchStateLiveData.postValue(SearchState.LOADING_MORE)
            currentPage++
            requestStringLiveData.value.let { it1 ->
                if (it1 != null) {
                    newSearchPhotos(
                        currentPage,
                        it1
                    )
                }
            }
        }
    }

    private fun newSearchPhotos(
        page: Int = 1,
        requestText: String,
        sort: SortEnum = SortEnum.RELEVANCE
    ) {
        viewModelScope.launch {
            val result = searchPhotoUseCase.execute(
                page,
                requestText,
                sort,
                apiKey = getApplication<App>().resources.getString(R.string.API_KEY)
            )
            if (result.responseCode != ResponseCode.IO_EXCEPTION && result.responseCode != ResponseCode.RUNTIME_EXCEPTION) {
                searchStateLiveData.postValue(SearchState.READY)
                responseCodeLiveData.postValue(result.responseCode)
                val oldList: MutableList<PhotoModel> =
                    (photosLiveData?.value as List<PhotoModel>).map { it.copy() } as MutableList<PhotoModel>
                result.photos?.let { oldList.addAll(it) }
                photosLiveData?.postValue(oldList)
            } else {
                searchStateLiveData.postValue(SearchState.READY)
                responseCodeLiveData.postValue(
                    if (isInternetAvailable(getApplication<App>().applicationContext)) {
                        result.responseCode
                    } else {
                        ResponseCode.NO_NETWORK_CONNECTION
                    }
                )
            }
        }
    }

//    private fun searchPhotos(page: Int = 1, requestText: String, sort: SortEnum = SortEnum.RELEVANCE ) {
//        flickrApiService.searchPhoto(
//            page = page,
//            text = requestText,
//            apiKey = getApplication<App>().resources.getString(R.string.API_KEY),
//            sort = sort.getFullString()
//        ).apply {
//            enqueue(object : Callback<PhotosSearchModel> {
//                override fun onResponse(
//                    call: Call<PhotosSearchModel>,
//                    response: Response<PhotosSearchModel>
//                ) {
//                    searchStateLiveData.postValue(SearchState.READY)
//                    if (response.isSuccessful) {
//                        if (response.body()?.stat.equals("ok")) {
//                            if (page != 1) {
//                                val oldList: MutableList<PhotoModel> =
//                                    (photosLiveData?.value as List<PhotoModel>).map { it.copy() } as MutableList<PhotoModel>
//                                response.body()?.photos?.photo?.let { oldList.addAll(it) }
//                                photosLiveData?.postValue(oldList)
//                            } else {
//                                photosLiveData?.postValue(response.body()?.photos?.photo)
//                                if (response.body()?.photos?.photo?.size == 0) {
//                                    responseCodeLiveData.postValue(ResponseCode.NOTHING_FOUND)
//                                }
//                            }
//                        } else if (response.body()?.stat.equals("fail")) {
//                            responseCodeLiveData.postValue(apiErrorCodeHandling(response.body()!!.code))
//                        } else {
//                            responseCodeLiveData.postValue(ResponseCode.UNKNOWN_EXCEPTION)
//                        }
//                    } else {
//                        responseCodeLiveData.postValue(responseErrorCodeHandling(response.code()))
//                    }
//                }
//
//                override fun onFailure(call: Call<PhotosSearchModel>, throwable: Throwable) {
//                    searchStateLiveData.postValue(SearchState.READY)
//                    responseCodeLiveData.postValue(
//                        if (isInternetAvailable(getApplication<App>().applicationContext)) {
//                            errorChecker(call, throwable)
//                        } else {
//                            ResponseCode.NO_NETWORK_CONNECTION
//                        }
//                    )
//                }
//            })
//        }
//    }

    private fun isInternetAvailable(context: Context): Boolean {
        var result: Boolean
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val networkCapabilities = connectivityManager.activeNetwork ?: return false
        val actNw =
            connectivityManager.getNetworkCapabilities(networkCapabilities) ?: return false
        result = when {
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            else -> false
        }

        return result
    }
}