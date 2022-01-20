package com.wwt.nimbleviewing.ui.scrollimg

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wwt.nimbleviewing.data.Album
import com.wwt.nimbleviewing.data.AlbumArt
import com.wwt.nimbleviewing.util.NetworkResult
import com.wwt.nimbleviewing.util.NetworkResult.Loading
import com.wwt.nimbleviewing.util.NetworkResult.Success
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ScrollingViewModel @Inject constructor(private val scrollingRepo: ScrollingRepo) :
    ViewModel() {

    private val _albumState = MutableStateFlow<NetworkResult<List<Album>>>(Loading())
    val albumState: StateFlow<NetworkResult<List<Album>>> = _albumState

    private val _photosState = MutableStateFlow<NetworkResult<List<AlbumArt>>>(Loading())

    fun getData() {

        viewModelScope.launch {

            //fetch photos by calling the api
            scrollingRepo.getAllPhotos()
                .collect { values ->
                    _photosState.value = values
                }

            //fetch albums by calling the api
            scrollingRepo.getAllAlbums()
                .collect { values ->

                    val albumList = values.data
                    val photosList = _photosState.value.data

                    if (albumList != null && photosList != null) {

                        val photosByAlbumId: Map<Int, AlbumArt> =
                            photosList.associateBy { it.albumId } //last photo from the photo list for each album id

                        var result =
                            albumList
                                .filter { photosByAlbumId[it.id] != null }.mapNotNull { album ->
                                    photosByAlbumId[album.id]?.let { photo ->
                                        Album(
                                            album.userId,
                                            album.id,
                                            album.title.replace(Regex("(?i)(e)"), ""),
                                            photo.url
                                        )
                                    }
                                }
                                .sortedBy { it.id }

                        if (result != null)
                            _albumState.value = Success(result)
                        else
                            _albumState.value = NetworkResult.Error("No items found", null)

                    } else {
                        _albumState.value = values
                    }

                }

        }


    }
}

