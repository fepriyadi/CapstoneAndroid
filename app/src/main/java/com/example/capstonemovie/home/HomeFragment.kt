package com.example.capstonemovie.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.RequestManager
import com.example.capstonemovie.databinding.FragmentHomeBinding
import com.example.capstonemovie.detail.DetailActivity
import com.example.core.ui.EpoxyCallbacks
import com.example.core.ui.EqualSpaceGridItemDecoration
import com.example.core.utils.getNumberOfColumns
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import org.koin.core.qualifier.named
import java.lang.ref.WeakReference
import kotlin.math.roundToInt

class HomeFragment : Fragment() {

    private val homeViewModel: HomeViewModel by viewModel()

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val glideRequestManager: RequestManager by inject(named("activity-glide-request-manager")) {
        parametersOf(activity)
    }

    private val homeEpoxyController: HomeEpoxyController by inject {
        parametersOf(callbacks, glideRequestManager)
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

        if (activity != null) {
            homeViewModel.state.observe(viewLifecycleOwner) { state ->
                homeEpoxyController.setData(state)
            }
        }

        binding.rvHome.apply {
            val columns = resources.getDimension(com.example.core.R.dimen.movie_grid_poster_width)
                .getNumberOfColumns(context)
            val space = resources.getDimension(com.example.core.R.dimen.movie_grid_item_space)
            layoutManager = GridLayoutManager(context, columns)
            addItemDecoration(EqualSpaceGridItemDecoration(space.roundToInt()))
            setController(homeEpoxyController)
        }
        (view.parent as ViewGroup).doOnPreDraw {
            startPostponedEnterTransition()
        }
    }

    private var callbacks = WeakReferenceEpoxyCallbacks(this)

    override fun onDestroyView() {
        super.onDestroyView()
        homeEpoxyController.cancelPendingModelBuild()
        binding.rvHome.adapter = null
        _binding = null
    }
}

private class WeakReferenceEpoxyCallbacks(fragment: HomeFragment) : EpoxyCallbacks {
    private val fragmentRef = WeakReference(fragment)

    override fun onMovieItemClicked(id: Int) {
        fragmentRef.get()?.let { fragment ->
            val intent = Intent(fragment.activity, DetailActivity::class.java)
            intent.putExtra(DetailActivity.EXTRA_DATA, id)
            fragment.startActivity(intent)
        }
    }
}