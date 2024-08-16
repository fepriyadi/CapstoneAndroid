package com.example.capstonemovie.search

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.RequestManager
import com.example.capstoneMovie.databinding.FragmentSearchBinding
import com.example.capstonemovie.detail.DetailActivity
import com.example.core.data.Resource
import com.example.core.ui.EpoxyCallbacks
import com.example.core.ui.EqualSpaceGridItemDecoration
import com.example.core.utils.getNumberOfColumns
import com.jakewharton.rxbinding3.widget.textChanges
import com.jakewharton.rxrelay2.PublishRelay
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import org.koin.core.qualifier.named
import java.util.concurrent.TimeUnit
import kotlin.math.roundToInt

class SearchFragment: Fragment() {

    private val searchViewModel: SearchViewModel by viewModel()
    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    private val glideRequestManager: RequestManager by inject(named("fragment-glide-request-manager")) {
        parametersOf(this)
    }

    private val searchEpoxyController: SearchEpoxyController by inject {
        parametersOf(callbacks, glideRequestManager)
    }
    private val onDestroyView: PublishRelay<Unit> = PublishRelay.create()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupSearchBox()
        if (activity != null) {

            searchViewModel.movies.observe(viewLifecycleOwner) { movies ->
                if (movies != null) {
                    when (movies) {
                        is Resource.Loading -> binding.progressBar.visibility = View.VISIBLE
                        is Resource.Success -> {
                            binding.progressBar.visibility = View.GONE
                            searchEpoxyController.setData(movies)
                        }

                        is Resource.Error -> {}
                    }
                }
            }

            binding.rvSearch.apply {
                val columns = resources.getDimension(com.example.core.R.dimen.movie_grid_poster_width).getNumberOfColumns(context)
                val space = resources.getDimension(com.example.core.R.dimen.movie_grid_item_space)
                layoutManager = GridLayoutManager(context, columns)
                addItemDecoration(EqualSpaceGridItemDecoration(space.roundToInt()))
                setController(searchEpoxyController)
            }
            (view.parent as ViewGroup).doOnPreDraw {
                startPostponedEnterTransition()
            }
        }
        getView()?.requestFocus()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        onDestroyView.accept(Unit)
    }

    private val callbacks = object : EpoxyCallbacks {
        override fun onMovieItemClicked(id: Int) {
            val intent = Intent(activity, DetailActivity::class.java)
            intent.putExtra(DetailActivity.EXTRA_DATA, id)
            startActivity(intent)
        }
    }

    private fun setupSearchBox() {
        binding.searchBox.etSearchBox.textChanges()
            .debounce(300, TimeUnit.MILLISECONDS)
            .map { text -> text.toString() }
            .takeUntil(onDestroyView)
            .doOnNext { query ->
                if (query.isNotEmpty())
                    searchViewModel.searchMovie(query)
            }
            .subscribe()
    }
}