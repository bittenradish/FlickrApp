package de.example.challenge.flickrapp.data.repository

import de.example.challenge.flickrapp.data.repository.flickrapi.FlickrApi
import de.example.challenge.flickrapp.data.repository.flickrapi.ResponseCode
import de.example.challenge.flickrapp.data.repository.flickrapi.error.RequestErrorChecker.Companion.apiErrorCodeHandling
import de.example.challenge.flickrapp.data.repository.flickrapi.error.RequestErrorChecker.Companion.errorChecker
import de.example.challenge.flickrapp.data.repository.flickrapi.error.RequestErrorChecker.Companion.responseErrorCodeHandling
import de.example.challenge.flickrapp.data.repository.flickrapi.models.SortEnum
import de.example.challenge.flickrapp.domain.model.searchmodels.SearchResultModel
import de.example.challenge.flickrapp.domain.repository.FlickrApiRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException

class FlickrApiRepositoryImpl : FlickrApiRepository {
    override suspend fun searchPhotos(
        page: Int, requestText: String, sort: SortEnum, apiKey: String
    ): SearchResultModel {
        return withContext(Dispatchers.IO) {
            val request = FlickrApi.createForSearch().searchPhoto(
                page = page,
                text = requestText,
                sort = sort.getFullString(),
                apiKey = apiKey
            )
            try {
                val response = request.execute()
                if (response.isSuccessful) {
                    if (response.body()?.stat.equals("ok")) {
                        if (response.body()?.photos?.photo?.size != 0) {
                            return@withContext SearchResultModel(
                                response.body()!!.photos.photo, ResponseCode.RESPONSE_OK
                            )
                        } else {
                            if(page==1) {
                                return@withContext SearchResultModel(
                                    mutableListOf(), ResponseCode.NOTHING_FOUND
                                )
                            }else{
                                return@withContext SearchResultModel(
                                    mutableListOf(), ResponseCode.END_OF_RESULTS
                                )
                            }
                        }
                    } else if (response.body()?.stat.equals("fail")) {
                        return@withContext SearchResultModel(
                            mutableListOf(), apiErrorCodeHandling(response.body()!!.code)
                        )
                    } else {
                        return@withContext SearchResultModel(
                            mutableListOf(),
                            ResponseCode.UNKNOWN_EXCEPTION
                        )
                    }
                } else {
                    return@withContext SearchResultModel(
                        mutableListOf(),
                        responseErrorCodeHandling(response.code())
                    )
                }
            } catch (ex: IOException) {
                return@withContext SearchResultModel(mutableListOf(), errorChecker(ex.cause))
            } catch (ex: RuntimeException) {
                return@withContext SearchResultModel(mutableListOf(), errorChecker(ex.cause))
            }
        }
    }
}