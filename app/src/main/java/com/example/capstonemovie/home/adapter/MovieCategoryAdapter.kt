package com.example.capstonemovie.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.capstonemovie.databinding.AdapterRowListBinding
import com.example.core.domain.model.MovieCat

class MovieCategoryAdapter(private val rowListListener: OnItemInteractionListener) :
    ListAdapter<MovieCat, MovieCategoryAdapter.MovieRowHolder>(
        diffCallback
    ) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MovieRowHolder {
        return MovieRowHolder(
            AdapterRowListBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            ), rowListListener
        )
    }

    override fun onBindViewHolder(holder: MovieRowHolder, position: Int) {
        getItem(position)?.also { data ->
            holder.bind(data)
        }

    }

    class MovieRowHolder(
        private val binding: AdapterRowListBinding,
        private val rowListListener: OnItemInteractionListener
    ) : ViewHolder(binding.root) {

        fun bind(category: MovieCat) {
            binding.rowNameText.text = category.categoryName

            val movieAdapter = MovieItemAdapter(category.movies,
                object : MovieItemAdapter.OnItemInteractionListener {
                    override fun onRowItemClick(id: Int?) {
                        id?.let { rowListListener.onRowItemClick(it) }
                    }
                }
            )
            binding.titlesRecyclerView.apply {
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                adapter = movieAdapter
            }
        }
    }

    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<MovieCat>() {
            override fun areItemsTheSame(
                oldItem: MovieCat,
                newItem: MovieCat
            ): Boolean {
                return oldItem.categoryName == newItem.categoryName
            }

            override fun areContentsTheSame(
                oldItem: MovieCat,
                newItem: MovieCat
            ): Boolean {
                return oldItem == newItem
            }
        }
    }

    interface OnItemInteractionListener {
        fun onRowItemClick(
            id: Int
        )
    }

}
