package com.vimal.infiniteimagelist.service

import android.util.Log
import com.vimal.infiniteimagelist.model.Photo
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private const val TAG = "FlickrService"
/**
 * Flickr API communication setup via Retrofit.
 */
interface FlickrService{
    @GET("services/rest/?method=flickr.photos.search&api_key=f57b3c30fdf2b657e678533c23483893&format=json&nojsoncallback=1&text=cats&extras=url_o")
    fun callFlickApi(
    @Query("page") page : Int,
    @Query("per_page") pageCount : Int
    ) : Call<FlickerResponse>

    companion object {
        private const val BASE_URL = "https://api.flickr.com/"

        fun create() : FlickrService {
            val logger = HttpLoggingInterceptor()
            logger.level = HttpLoggingInterceptor.Level.BASIC

            val client = OkHttpClient.Builder()
                .addInterceptor(logger)
                .build()

            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(FlickrService::class.java)
        }
    }
}

/**
 * Get the flickr photos from the Public API Section
 * @param page request page index
 * @param itemsPerPage number of repositories to be returned by the Github API per page
 *
 * The result of the request is handled by the implementation of the functions passed as params
 * @param onSuccess function that defines how to handle the list of photos received
 * @param onError function that defines how to handle request failure
 */
fun getFlickrPhotos(
    service: FlickrService,
    page: Int,
    itemsPerPage: Int,
    onSuccess : (photos : List<Photo>) -> Unit,
    onFailure : (error : String) -> Unit
){
    Log.d(TAG, "page: $page, itemsPerPage: $itemsPerPage")

    service.callFlickApi(page, itemsPerPage).enqueue(object : Callback<FlickerResponse>{
        override fun onFailure(call: Call<FlickerResponse>, t: Throwable) {
            Log.d(TAG, "fail to get data")
            onFailure(t.message ?: "unknown error")
        }

        override fun onResponse(call: Call<FlickerResponse>, response: Response<FlickerResponse>) {
            Log.d(TAG, "got a response $response")
            if(response.isSuccessful){
                val photos = response.body()?.response?.items ?: emptyList()
                onSuccess(photos)
            } else {
                onFailure(response.errorBody()?.string() ?: "Unknown error")
            }
        }
    })
}