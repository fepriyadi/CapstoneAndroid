package com.example.core.ui

import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.Nullable
import androidx.core.view.ViewCompat
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import com.bumptech.glide.RequestManager
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.example.core.R
import org.koin.core.component.KoinComponent


@EpoxyModelClass
abstract class HeaderModel : EpoxyModelWithHolder<HeaderModel.HeaderViewHolder>() {

    @EpoxyAttribute
    var title: String = ""

    @EpoxyAttribute
    var transitionName: String? = ""

    override fun bind(holder: HeaderViewHolder) {
        super.bind(holder)
        holder.title.text = title
    }

    override fun getDefaultLayout(): Int {
        return R.layout.item_section_header
    }

    class HeaderViewHolder : KotlinEpoxyHolder() {
        val title by bind<TextView>(R.id.tvSectionHeader)
    }
}

@EpoxyModelClass
abstract class MovieModel : EpoxyModelWithHolder<MovieModel.MovieViewHolder>() {

    @EpoxyAttribute
    var posterUrl: String = ""

    @EpoxyAttribute(EpoxyAttribute.Option.DoNotHash)
    var clickListener: View.OnClickListener? = null

    @EpoxyAttribute
    var transitionName: String = ""

    @EpoxyAttribute
    var movieId: Int? = null

    @EpoxyAttribute
    lateinit var glide: RequestManager

    override fun getDefaultLayout(): Int {
        return R.layout.item_moviegrid
    }

    override fun bind(holder: MovieViewHolder) {
        super.bind(holder)
        glide.load(posterUrl)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(holder.poster)
        ViewCompat.setTransitionName(holder.poster, transitionName)
        holder.poster.setOnClickListener(clickListener)
    }

    inner class MovieViewHolder : KotlinEpoxyHolder(), KoinComponent {
        val poster by bind<ImageView>(R.id.ivPoster)
    }
}

@EpoxyModelClass
abstract class InfoTextModel : EpoxyModelWithHolder<InfoTextModel.InfoTextViewHolder>() {

    @EpoxyAttribute
    lateinit var text: String

    override fun getDefaultLayout(): Int {
        return R.layout.view_info_text
    }

    override fun bind(holder: InfoTextViewHolder) {
        super.bind(holder)
        holder.textView.text = text
    }

    inner class InfoTextViewHolder : KotlinEpoxyHolder() {
        val textView by bind<TextView>(R.id.tvInfoText)
    }
}

@EpoxyModelClass
abstract class ActorModel : EpoxyModelWithHolder<ActorModel.ActorHolder>() {

    @EpoxyAttribute
    var actorId: Int? = null

    @EpoxyAttribute
    lateinit var name: String

    @EpoxyAttribute
    lateinit var pictureUrl: String

    @EpoxyAttribute
    lateinit var transitionName: String

    @EpoxyAttribute
    lateinit var glide: RequestManager

    override fun getDefaultLayout(): Int {
        return R.layout.item_credit_actor
    }

    override fun bind(holder: ActorHolder) {
        super.bind(holder)
        with(holder) {
            actorName.text = name
            glide
                .load(pictureUrl)
                .transition(DrawableTransitionOptions.withCrossFade())
                .override(300, 300)
                .apply(
                    RequestOptions()
                        .placeholder(R.drawable.ic_round_account_circle_24px)
                        .error(R.drawable.ic_round_account_circle_24px)
                        .fallback(R.drawable.ic_round_account_circle_24px)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .circleCrop()
                )
                .into(object : CustomTarget<Drawable?>() {
                    override fun onResourceReady(
                        resource: Drawable,
                        transition: Transition<in Drawable?>?
                    ) {
                        // Set the drawable to the left of the text
                        actorName.setCompoundDrawablesWithIntrinsicBounds(null, resource, null, null)
                    }

                    override fun onLoadCleared(placeholder: Drawable?) {
                        // Handle clearing if necessary
                    }
                })
        }
    }

    inner class ActorHolder : KotlinEpoxyHolder(), KoinComponent {
        val actorName by bind<TextView>(R.id.tvCreditActorName)
    }
}

@EpoxyModelClass
abstract class MovieSearchResultModel : EpoxyModelWithHolder<MovieSearchResultModel.MovieSearchResultHolder>() {

    @EpoxyAttribute
    lateinit var posterUrl: String

    @EpoxyAttribute(EpoxyAttribute.Option.DoNotHash)
    lateinit var clickListener: View.OnClickListener

    @EpoxyAttribute
    lateinit var transitionName: String

    @EpoxyAttribute
    var movieId: Int? = null

    @EpoxyAttribute
    lateinit var glide: RequestManager

    @EpoxyAttribute
    lateinit var movieTitle: String

    override fun getDefaultLayout(): Int {
        return R.layout.item_movie_search_result
    }

    override fun bind(holder: MovieSearchResultHolder) {
        super.bind(holder)
        with(holder) {
            ViewCompat.setTransitionName(poster, transitionName)
            poster.setOnClickListener(clickListener)
            title.text = movieTitle
            glide.load(posterUrl)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(holder.poster)
        }
    }

    override fun unbind(holder: MovieSearchResultHolder) {
        super.unbind(holder)
        glide.clear(holder.poster)
    }

    inner class MovieSearchResultHolder : KotlinEpoxyHolder(), KoinComponent {
        val poster by bind<ImageView>(R.id.ivPosterSearchResult)
        val title by bind<TextView>(R.id.tvTitleSearchResult)
    }
}

@EpoxyModelClass
abstract class LoadingModel: EpoxyModelWithHolder<LoadingModel.LoadingHolder>() {

    @EpoxyAttribute
    lateinit var description: String

    override fun getDefaultLayout(): Int {
        return R.layout.view_loading
    }

    override fun bind(holder: LoadingHolder) {
        super.bind(holder)
        holder.description.text = description
    }

    inner class LoadingHolder: KotlinEpoxyHolder() {
        val description by bind<TextView>(R.id.tvLoadingDescription)
    }
}