package com.example.favourite.di

import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.example.core.ui.EpoxyCallbacks
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val favouriteModule = module {

    factory(named("fragment-glide-request-manager")) { (fragment: Fragment) ->
        Glide.with(fragment)
    }
    factory { (callbacks: EpoxyCallbacks, glide: RequestManager) ->
        com.example.favourite.FavouriteEpoxyController(
            callbacks,
            glide,
            get(named("epoxy-handler"))
        )
    }

    viewModel { com.example.favourite.FavouriteViewModel(get()) }
}





