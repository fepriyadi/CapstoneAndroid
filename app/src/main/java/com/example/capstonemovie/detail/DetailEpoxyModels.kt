package com.example.capstonemovie.detail

import android.view.View
import android.widget.Button
import android.widget.TextView
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import com.example.capstonemovie.R.id
import com.example.capstonemovie.R.layout.view_main_text
import com.example.capstonemovie.R.layout.view_movie_info_bar
import com.example.core.ui.KotlinEpoxyHolder

@EpoxyModelClass
abstract class MainTextModel : EpoxyModelWithHolder<MainTextModel.MainTextViewHolder>() {

    @EpoxyAttribute
    lateinit var text: String

    override fun getDefaultLayout(): Int {
        return view_main_text
    }

    override fun bind(holder: MainTextViewHolder) {
        super.bind(holder)
        holder.mainText.text = text
    }

    inner class MainTextViewHolder : KotlinEpoxyHolder() {
        val mainText by bind<TextView>(id.tvMainText)
    }
}

@EpoxyModelClass
abstract class InfoBarModel : EpoxyModelWithHolder<InfoBarModel.InfoBarHolder>() {

    @EpoxyAttribute
    open var isFavorite: Boolean = false

    @EpoxyAttribute(EpoxyAttribute.Option.DoNotHash)
    lateinit var favouritesClickListener: View.OnClickListener

    override fun getDefaultLayout(): Int {
        return view_movie_info_bar
    }

    override fun bind(holder: InfoBarHolder) {
        super.bind(holder)
        isFavorite.let { favourite ->
            holder.favouriteButton.apply {
//                setRemoveFromListState(favourite)
                text = if (favourite) "Un-Favourite" else "Add to Favourites"
                setOnClickListener(favouritesClickListener)
            }
        }
    }

    override fun unbind(holder: InfoBarHolder) {
        super.unbind(holder)
        holder.favouriteButton.apply {
//            setRemoveFromListState(false)
        }
    }

    inner class InfoBarHolder : KotlinEpoxyHolder() {
        val favouriteButton by bind<Button>(id.btToggleFavourite)
    }
}