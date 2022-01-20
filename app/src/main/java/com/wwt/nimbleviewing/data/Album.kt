package com.wwt.nimbleviewing.data

data class Album(val userId: Int, val id: Int, val title: String, var url : String)

data class AlbumArt(val albumId: Int, val id: Int, val url: String, val thumbnailUrl: String)