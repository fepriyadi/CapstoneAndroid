package com.example.favourite

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.RequestManager
import com.example.core.data.Resource
import com.example.core.ui.EpoxyCallbacks
import com.example.core.ui.EqualSpaceGridItemDecoration
import com.example.core.utils.getNumberOfColumns
import com.example.favourite.databinding.FragmentFavouriteBinding
import com.example.favourite.di.favouriteModule
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.core.parameter.parametersOf
import org.koin.core.qualifier.named
import kotlin.math.roundToInt

class FavouriteFragment : Fragment() {

    private val favViewModel: FavouriteViewModel by viewModel()

    private var _binding: FragmentFavouriteBinding? = null
    private val binding get() = _binding!!

    private val glideRequestManager: RequestManager by inject(named("fragment-glide-request-manager")) {
        parametersOf(this)
    }

    private val homeEpoxyController: FavouriteEpoxyController by inject {
        parametersOf(callbacks, glideRequestManager)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavouriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loadKoinModules(favouriteModule)
        if (activity != null) {

            favViewModel.movies.observe(viewLifecycleOwner) { movie ->
                if (movie != null) {
                    when (movie) {
                        is Resource.Loading -> binding.progressBar.visibility = View.VISIBLE
                        is Resource.Success -> {
                            binding.progressBar.visibility = View.GONE
                            homeEpoxyController.setData(movie)
                            binding.viewEmpty.root.visibility =
                                if (movie.data?.isNotEmpty() == true)
                                    View.GONE
                                else
                                    View.VISIBLE
                        }

                        is Resource.Error -> {
                            binding.progressBar.visibility = View.GONE
                        }
                    }
                }
            }

            binding.rvHome.apply {
                val columns = resources.getDimension(com.example.core.R.dimen.movie_grid_poster_width).getNumberOfColumns(context)
                val space = resources.getDimension(com.example.core.R.dimen.movie_grid_item_space)
                layoutManager = GridLayoutManager(context, columns)
                addItemDecoration(EqualSpaceGridItemDecoration(space.roundToInt()))
                setController(homeEpoxyController)
            }
            (view.parent as ViewGroup).doOnPreDraw {
                startPostponedEnterTransition()
            }
        }
    }

    private val callbacks = object : EpoxyCallbacks {
        override fun onMovieItemClicked(id: Int) {
            val intent = Intent("com.example.capstonemovie.detail.DetailActivity")
            intent.putExtra("extra_data", id)
            startActivity(intent)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}