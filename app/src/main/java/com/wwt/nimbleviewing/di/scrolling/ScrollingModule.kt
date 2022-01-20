package com.wwt.nimbleviewing.di.scrolling

import com.wwt.nimbleviewing.network.api.ScrollingApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit

@InstallIn(SingletonComponent::class)
@Module
class ScrollingModule {

    @Provides
    fun provideScrollingApi(retrofit: Retrofit): ScrollingApi = retrofit.create(ScrollingApi::class.java)

}