package com.example.capstonemovie.detail

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.RequestManager
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.bitmap.BitmapTransitionOptions
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.capstonemovie.MainViewModel
import com.example.capstonemovie.databinding.ActivityDetailBinding
import com.example.core.R.dimen
import com.example.core.R.string
import com.example.core.data.Resource
import com.example.core.domain.model.MovieDetail
import com.example.core.utils.formatYear
import com.example.core.utils.getNumberOfColumns
import com.example.core.utils.log
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import org.koin.core.qualifier.named

class DetailActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_DATA = "extra_data"
    }

    private val movieDetailsViewModel: DetailViewModel by viewModel()
    private val mainViewModel: MainViewModel by viewModel()
    private lateinit var binding: ActivityDetailBinding

    private val glideRequestManager: RequestManager by inject(named("activity-glide-request-manager")) {
        parametersOf(this)
    }

    private val detailsEpoxyController: DetailEpoxyController by inject {
        parametersOf(callbacks, glideRequestManager)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar.mainToolbar)

        supportActionBar?.apply {
            title = getString(string.app_name)
        }

        val movieId = intent.extras?.getInt(EXTRA_DATA)
        binding.rvMovieDetails.apply {
            val columns = resources.getDimension(dimen.cast_member_picture_size).getNumberOfColumns(context)
            layoutManager = GridLayoutManager(context, columns)
            setController(detailsEpoxyController)
        }

        with(movieDetailsViewModel) {
            movieId?.let { getMovieDetail(it) }
            dataMovieDetail.observe(this@DetailActivity) { movieDetail ->
                if (movieDetail != null) {
                    when (movieDetail) {
                        is Resource.Loading -> {
                            binding.progressBar.visibility = View.VISIBLE
                        }
                        is Resource.Success -> {
                            val isFav = movieDetail.data?.isFavoriteMovie ?: false
                            isFav.toString().log("isFav ")
                            binding.progressBar.visibility = View.GONE
                            mainViewModel.updateToolbarTitle(movieDetail.data?.title.toString())
                            renderMovieHeader(movieDetail)
                            detailsEpoxyController.apply {
                                val data = movieDetail.data?.credits?.cast?.map { cast ->
                                    Resource.Success(cast)
                                }?.let { DetailScreenState(isFav, movieDetail, it) }
                                setData(data)
                            }
                        }

                        is Resource.Error -> {
                            binding.progressBar.visibility = View.INVISIBLE
                        }
                    }
                }
            }
        }

        with(mainViewModel) {
            toolbarTitle.observe(this@DetailActivity) { newTitle ->
                supportActionBar?.apply {
                    title = newTitle
                }
            }
        }
    }

    private val callbacks = object : DetailEpoxyController.MovieDetailsCallbacks {
        override fun toggleMovieFavouriteStatus(newState: Boolean) {
            movieDetailsViewModel.toggleMovieFavouriteStatus(newState)
        }
    }

    private fun renderMovieHeader(movieResource: Resource<MovieDetail>){
        when (movieResource) {
            is Resource.Success -> {
                val movie = movieResource.data
                glideRequestManager
                    .load(movie?.posterURl)
                    .listener(object : RequestListener<Drawable> {
                        override fun onLoadFailed(
                            e: GlideException?,
                            model: Any?,
                            target: Target<Drawable>?,
                            isFirstResource: Boolean
                        ): Boolean {
                            startPostponedEnterTransition()
                            return false
                        }

                        override fun onResourceReady(
                            resource: Drawable?,
                            model: Any?,
                            target: Target<Drawable>?,
                            dataSource: DataSource?,
                            isFirstResource: Boolean
                        ): Boolean {
                            startPostponedEnterTransition()
                            return false
                        }
                    })
                    .into(binding.ivPoster)

                glideRequestManager
                    .asBitmap()
                    .transition(BitmapTransitionOptions.withCrossFade())
                    .load(movie?.backdropURl)
                    .into(binding.ivBackdrop)

                movie?.let {
                    binding.tvTitle.text = it.title
                    binding.chipMovieYear.text = it.releaseDate.formatYear()
                    binding.chipMovieGenre.text = it.genreName
                    binding.chipMovieRating.text = it.rating
                }
            }
            else -> Unit
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        detailsEpoxyController.cancelPendingModelBuild()
    }

}