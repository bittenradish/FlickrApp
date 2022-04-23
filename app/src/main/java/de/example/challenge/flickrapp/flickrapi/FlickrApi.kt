package de.example.challenge.flickrapp.flickrapi

import de.example.challenge.flickrapp.flickrapi.models.PhotosSearchModel
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface FlickrApi {

    companion object {
        private val SEARCH_BASE_URL: String = "https://api.flickr.com/services/rest/"

        /**
         * "https://farm66.static.flickr.com/65535/50746844348_f6b146228b.jpg"
         * Due to the fact that aforementioned url is not working with farm 0
         * Url from API documentation used here
         * https://www.flickr.com/services/api/misc.urls.html
         */
        //TODO: write correct url
        private val PHOTOS_BASE_URL: String = "";

        fun createForSearch(): FlickrApi {
            return create(SEARCH_BASE_URL)
        }

        fun createForPhoto(): FlickrApi {
            return create(PHOTOS_BASE_URL)
        }

        private fun create(url: String): FlickrApi {
            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(url)
                .build()
            return retrofit.create(FlickrApi::class.java)
        }
    }

    // photos searching request
    //TODO: delete media type
    @GET("?method=flickr.photos.search")
    fun searchPhoto(
        @Query("api_key") apiKey: String,
        @Query("text") text: String,
        @Query("page") page: Int = 1,
        @Query("format") format: String = "json",
        @Query("nojsoncallback") nojsoncallback: Int = 1,
        @Query("safe_search") safe_search: Int = 1,
        @Query("media") media: String = "all",
        @Query("sort") sort: String = "relevance"
    ): Call<PhotosSearchModel>

//    @GET("{serverId}/")
//    fun getPhoto()
}