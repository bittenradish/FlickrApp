package de.example.challenge.flickrapp.fragments.childFragments

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import de.example.challenge.flickrapp.application.App
import de.example.challenge.flickrapp.flickrapi.FlickrApi
import de.example.challenge.flickrapp.flickrapi.models.PhotoModel
import de.example.challenge.flickrapp.flickrapi.models.PhotosSearchModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchViewModel(application: Application) : AndroidViewModel(application) {
    private var photosLiveData: MutableLiveData<List<PhotoModel>>? = null
    private var photoLoadingLiveData: MutableLiveData<Boolean> = MutableLiveData(false)
    private var photoSearchingLiveData: MutableLiveData<Boolean> = MutableLiveData(false)
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

    fun searchFor(requestText: String, fromAnotherFragment: Boolean = false) {
        photoLoadingLiveData.postValue(true)
        photoSearchingLiveData.postValue(true)
        photosLiveData?.postValue(listOf<PhotoModel>())
        currentPage = 1
        if (fromAnotherFragment) {
            requestStringLiveData.postValue(requestText)
        }
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
        flickrApiService.searchPhoto(page = page, text = requestText, apiKey = App.API_KEY).apply {
            enqueue(object : Callback<PhotosSearchModel> {
                override fun onResponse(
                    call: Call<PhotosSearchModel>,
                    response: Response<PhotosSearchModel>
                ) {
                    photoLoadingLiveData.postValue(false)
                    photoSearchingLiveData.postValue(false)
                    Log.d("TAG", "is successful: " + response.isSuccessful)
                    if (response.isSuccessful) {
                        if (page != 1) {
                            val oldList: MutableList<PhotoModel> =
                                (photosLiveData?.value as List<PhotoModel>).map { it.copy() } as MutableList<PhotoModel>
                            response.body()?.photos?.photo?.let { oldList.addAll(it) }
                            Log.d("SIZE", "List Size: " + oldList.size)
                            photosLiveData?.postValue(oldList)
                        } else {
                            photosLiveData?.postValue(response.body()?.photos?.photo)
                            Log.d("SIZE", "photosLiveData is null: " + photosLiveData)
                            Log.d("SIZE", "new list Size: " + photosLiveData?.value?.size)
                        }
                        //TODO add behavior for empty list
                    } else {
                        //TODO implement error management
                    }
                }

                override fun onFailure(p0: Call<PhotosSearchModel>, p1: Throwable) {
                    Log.d("TAG", "onFailure: " + p1.cause)
                    photoLoadingLiveData.postValue(false)
                    photoSearchingLiveData.postValue(false)
                }
            })
        }
    }
}