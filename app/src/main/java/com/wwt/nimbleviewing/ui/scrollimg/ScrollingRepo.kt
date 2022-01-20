package com.wwt.nimbleviewing.ui.scrollimg

import android.util.Log
import com.wwt.nimbleviewing.data.Album
import com.wwt.nimbleviewing.data.AlbumArt
import com.wwt.nimbleviewing.di.IoDispatcher
import com.wwt.nimbleviewing.network.api.ScrollingApi
import com.wwt.nimbleviewing.util.BaseApiResponse
import com.wwt.nimbleviewing.util.NetworkResult
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@ActivityRetainedScoped
class ScrollingRepo @Inject constructor(private val scrollingApi: ScrollingApi, @IoDispatcher private val ioDispatcher : CoroutineDispatcher) :
    BaseApiResponse() {


    suspend fun getAllAlbums() : Flow<NetworkResult<List<Album>>> {

        return flow {
            emit(safeApiCall { getAlbum() })
        }.flowOn(ioDispatcher)
    }

    suspend fun getAllPhotos() : Flow<NetworkResult<List<AlbumArt>>> {
        Log.d("photosR", "called")
        return flow {
             emit(safeApiCall { getPhotos() })
        }.flowOn(ioDispatcher)
    }

    private suspend fun getAlbum() = scrollingApi.getAlbums()

    private suspend fun getPhotos() = scrollingApi.getPhotos()


}

/*abstract class BaseApiResponse {
    suspend fun <T> safeApiCall(apiCall: suspend () -> Response<T>): NetworkResult<T> {
        try {
            val response = apiCall()
            if (response.isSuccessful) {
                val body = response.body()
                body?.let {
                    return NetworkResult.Success(body)
                }
            }
            return error("${response.code()} ${response.message()}")
        } catch (e: Exception) {
            return error(e.message ?: e.toString())
        }
    }
    private fun <T> error(errorMessage: String): NetworkResult<T> =
        NetworkResult.Error("Api call failed $errorMessage")
}*/
