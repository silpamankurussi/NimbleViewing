package com.wwt.nimbleviewing.ui.scrollimg

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.wwt.nimbleviewing.BuildConfig
import com.wwt.nimbleviewing.data.Album
import com.wwt.nimbleviewing.data.AlbumArt
import com.wwt.nimbleviewing.databinding.ActivityScrollingBinding
import com.wwt.nimbleviewing.util.NetworkResult
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.content_scrolling.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ScrollingActivity : AppCompatActivity() {

    private lateinit var viewModel: ScrollingViewModel

    private val listAdapter: AlbumListAdapter by lazy { AlbumListAdapter() }
    private lateinit var manager : RecyclerView.LayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this)[ScrollingViewModel::class.java]

        with(ActivityScrollingBinding.inflate(LayoutInflater.from(this), null, false)) {
            
            setContentView(root)
            setSupportActionBar(toolbar)
            toolbarLayout.title = this@ScrollingActivity.title

            album_list.apply {
                layoutManager = LinearLayoutManager(this@ScrollingActivity)
                adapter = listAdapter
                setHasFixedSize(true)
             }

            fab.setOnClickListener {
                Snackbar.make(
                    album_list,
                    "Display album titles and images from ${BuildConfig.BASE_URL}",
                    Snackbar.LENGTH_LONG
                ).show()
            }

            viewModel.getData()
            collectData()
        }

       
    }


    private fun collectData() {
        var albums: List<Album>? = listOf()
        var photos: List<AlbumArt>?

        // Start a coroutine in the lifecycle scope
        lifecycleScope.launch {
            // repeatOnLifecycle launches the block in a new coroutine every time the
            // lifecycle is in the STARTED state (or above) and cancels it when it's STOPPED.
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {

                //collecting album
                launch {
                    viewModel.albumState.collect {

                        when (it) {
                            is NetworkResult.Success -> {
                                albums = it.data
                                loadDataInAdapter(albums/*, photos*/)
                                Log.d("albumA", albums.toString())
                            }
                            is NetworkResult.Error -> {
                                // show error message -todo
                            }
                            is NetworkResult.Loading -> {
                                // show a progress bar -todo
                            }
                        }
                    }
                }

              /*  //collecting photos
                launch {
                    viewModel.photosState.collect {
                        when (it) {
                            is NetworkResult.Success -> {
                                photos = it.data
                                Log.d("photosA", photos.toString())
                                loadDataInAdapter(albums, photos)
                            }
                            is NetworkResult.Error -> {
                                // show error message -todo
                            }
                            is NetworkResult.Loading -> {
                                // show a progress bar -todo
                            }
                        }
                    }
                }*/
            }
        }

    }

    private fun loadDataInAdapter(
        albums: List<Album>?/*,
        photos: List<AlbumArt>?*/
    ) {

        albums?.let { _albums ->
           // photos?.let { _photos ->
                Log.d("album", albums.toString())
                //Log.d("photos", photos.toString())
                listAdapter.submitList(_albums.toMutableList()/*, _photos.toMutableList()*/)
                listAdapter.notifyDataSetChanged()
          //  }
        }


    }

}