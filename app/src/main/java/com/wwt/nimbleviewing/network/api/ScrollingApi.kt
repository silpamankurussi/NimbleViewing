package com.wwt.nimbleviewing.network.api

import com.google.gson.JsonObject
import com.wwt.nimbleviewing.data.Album
import com.wwt.nimbleviewing.data.AlbumArt
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

interface ScrollingApi {

    @GET("albums")
    suspend fun getAlbums(): Response<List<Album>>

    @GET("photos")
    suspend fun getPhotos(): Response<List<AlbumArt>>
}