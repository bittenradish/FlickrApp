package de.example.challenge.flickrapp.fragments.childFragments

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import de.example.challenge.flickrapp.R
import de.example.challenge.flickrapp.application.App
import de.example.challenge.flickrapp.flickrapi.FlickrApi
import de.example.challenge.flickrapp.flickrapi.ResponseCode
import de.example.challenge.flickrapp.flickrapi.error.RequestErrorChecker.Companion.apiErrorCodeHandling
import de.example.challenge.flickrapp.flickrapi.error.RequestErrorChecker.Companion.errorChecker
import de.example.challenge.flickrapp.flickrapi.error.RequestErrorChecker.Companion.responseErrorCodeHandling
import de.example.challenge.flickrapp.flickrapi.models.PhotoModel
import de.example.challenge.flickrapp.flickrapi.models.PhotosSearchModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class SearchViewModel(application: Application) : AndroidViewModel(application) {
    private var photosLiveData: MutableLiveData<List<PhotoModel>>? = null
    private var photoLoadingLiveData: MutableLiveData<Boolean> = MutableLiveData(false)
    private var photoSearchingLiveData: MutableLiveData<Boolean> = MutableLiveData(false)
    private var responseCodeLiveData: MutableLiveData<ResponseCode> =
        MutableLiveData(ResponseCode.RESPONSE_OK)
    private val flickrApiService = FlickrApi.createForSearch()
    private var requestStringLiveData: MutableLiveData<String> = MutableLiveData()
    private var currentPage: Int = 1

    fun getPhotosLiveData(): LiveData<List<PhotoModel>> {
        if (photosLiveData == null) {
            photosLiveData = MutableLiveData()
            photosLiveData?.postValue(listOf<PhotoModel>())
        }
        return photosLiveData!!
    }

    fun getPhotoLoadingMoreLiveData(): LiveData<Boolean> {
        return photoLoadingLiveData
    }

    fun getPhotoSearchingLiveData(): LiveData<Boolean> {
        return photoSearchingLiveData
    }

    fun getRequestStringLiveData(): LiveData<String> {
        return requestStringLiveData
    }

    fun getResponseCodeLiveData(): LiveData<ResponseCode> {
        return responseCodeLiveData
    }

    fun observerGotTheMessage() {
        responseCodeLiveData.postValue(ResponseCode.RESPONSE_OK)
    }

    fun searchFor(requestText: String) {
        photoLoadingLiveData.postValue(true)
        photoSearchingLiveData.postValue(true)
        photosLiveData?.postValue(listOf<PhotoModel>())
        currentPage = 1
        requestStringLiveData.postValue(requestText)
        searchPhotos(requestText = requestText)
    }

    fun loadMore() {
        //TODO: implement loading next pages for endless scrolling
        photoLoadingLiveData.postValue(true)
        photoSearchingLiveData.postValue(true)
        currentPage++
        requestStringLiveData.value.let { it1 ->
            if (it1 != null) {
                searchPhotos(
                    currentPage,
                    it1
                )
            }
        }
    }

    private fun searchPhotos(page: Int = 1, requestText: String) {
        Log.d("SIZE", "Starting search for: " + requestText + " page: " + page)
        flickrApiService.searchPhoto(
            page = page,
            text = requestText,
            apiKey = getApplication<App>().resources.getString(R.string.API_KEY)
        )
            .apply {
                enqueue(object : Callback<PhotosSearchModel> {
                    override fun onResponse(
                        call: Call<PhotosSearchModel>,
                        response: Response<PhotosSearchModel>
                    ) {
                        photoLoadingLiveData.postValue(false)
                        photoSearchingLiveData.postValue(false)
                        if (response.isSuccessful) {
                            if (response.body()?.stat.equals("ok")) {
                                //TODO: implement nothing found
                                //TODO: send response okay
                                if (page != 1) {
                                    val oldList: MutableList<PhotoModel> =
                                        (photosLiveData?.value as List<PhotoModel>).map { it.copy() } as MutableList<PhotoModel>
                                    response.body()?.photos?.photo?.let { oldList.addAll(it) }
                                    photosLiveData?.postValue(oldList)
                                } else {
                                    photosLiveData?.postValue(response.body()?.photos?.photo)
                                }
                            } else if (response.body()?.stat.equals("fail")) {
                                responseCodeLiveData.postValue(apiErrorCodeHandling(response.body()!!.code))
                            } else {
                                responseCodeLiveData.postValue(ResponseCode.UNKNOWN_EXCEPTION)
                            }
                            Log.d("Error", "Stat is: " + response.body()?.stat)
                        } else {
                            responseCodeLiveData.postValue(responseErrorCodeHandling(response.code()))
                        }
                    }

                    override fun onFailure(call: Call<PhotosSearchModel>, throwable: Throwable) {
                        photoLoadingLiveData.postValue(false)
                        photoSearchingLiveData.postValue(false)
                        responseCodeLiveData.postValue(
                            if (isInternetAvailable(getApplication<App>().applicationContext)) {
                                errorChecker(call, throwable)
                            } else {
                                ResponseCode.NO_NETWORK_CONNECTION
                            }
                        )
                    }
                })
            }
    }

    private fun isInternetAvailable(context: Context): Boolean {
        var result = false
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