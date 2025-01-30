package com.example.capstonemovie.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ConcatAdapter
import com.example.capstonemovie.databinding.FragmentHomeBinding
import com.example.capstonemovie.detail.DetailActivity
import com.example.capstonemovie.home.adapter.MovieCategoryAdapter
import com.example.core.domain.model.MovieCat
import com.jakewharton.rxrelay2.PublishRelay
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : Fragment() {

    private val homeViewModel: HomeViewModel by viewModel()
    private var rowListAdapter: MovieCategoryAdapter? = null
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val onDestroyView: PublishRelay<Unit> = PublishRelay.create()

    private val rowListListener: MovieCategoryAdapter.OnItemInteractionListener =
        object : MovieCategoryAdapter.OnItemInteractionListener {
            override fun onRowItemClick(id: Int) {
                val intent = Intent(requireActivity(), DetailActivity::class.java)
                intent.putExtra(DetailActivity.EXTRA_DATA, id)
                startActivity(intent)
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.run {
            recyclerViewMovies.run {
                itemAnimator = null
                rowListAdapter = MovieCategoryAdapter(rowListListener)
                adapter = ConcatAdapter(rowListAdapter)
            }
        }

        homeViewModel.homeScreenState.observe(viewLifecycleOwner) { state ->
            val categories = mutableListOf<MovieCat>()

            state.popularMoviesResource?.data?.let { movies ->
                categories.add(MovieCat("Popular Movies", movies))
            }

            state.topRatedMoviesResource?.data?.let { movies ->
                categories.add(MovieCat("Top Rated Movies", movies))
            }

            state.nowPlayingResultsResource?.data?.let { movies ->
                categories.add(MovieCat("Now Playing", movies))
            }

            rowListAdapter?.submitList(categories)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        onDestroyView.accept(Unit)
        _binding = null
    }
}