package com.example.capstonemovie.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.capstonemovie.databinding.MovieItemBinding
import com.example.core.domain.model.Movie

class MovieItemAdapter(
    private val movieRowItems: List<Movie>,
    private val rowItemListener: OnItemInteractionListener
) :
    ListAdapter<Movie, MovieItemAdapter.MovieRowItemViewHolder>(diffCallback) {

    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<Movie>() {
            override fun areItemsTheSame(
                oldItem: Movie,
                newItem: Movie
            ): Boolean {
                println("fep --> areItemsTheSame : ${oldItem.id == newItem.id}")
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: Movie,
                newItem: Movie
            ): Boolean {
                println("fep -> areContentsTheSame : ${oldItem.title == newItem.title}")
                return oldItem.title == newItem.title
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MovieRowItemViewHolder {
        println("fep --> onCreateViewHolder()")
        return MovieRowItemViewHolder(
            MovieItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            ),
            rowItemListener
        )
    }

    override fun getItem(position: Int): Movie {
        return movieRowItems[position]
    }

    override fun getItemCount(): Int {
        return movieRowItems.size
    }

    override fun onBindViewHolder(holder: MovieRowItemViewHolder, position: Int) {
        holder.onBind(getItem(position))
    }

    interface OnItemInteractionListener {
        fun onRowItemClick(id: Int? = 0)
    }

    class MovieRowItemViewHolder(
        private val binding: MovieItemBinding,
        private val rowItemListener: OnItemInteractionListener
    ) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(data: Movie) {
            itemView.setOnClickListener { rowItemListener.onRowItemClick(data.id) }
            with(binding) {
                Glide.with(itemView.context)
                    .load(data.posterURl)
                    .into(imageViewPoster)
            }
        }
    }
}