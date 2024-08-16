package com.example.capstonemovie.di

import android.app.Activity
import android.os.Handler
import android.os.HandlerThread
import android.view.View
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.example.capstonemovie.MainViewModel
import com.example.capstonemovie.detail.DetailViewModel
import com.example.capstonemovie.detail.DetailEpoxyController
import com.example.capstonemovie.home.HomeEpoxyController
import com.example.capstonemovie.home.HomeViewModel
import com.example.capstonemovie.search.SearchEpoxyController
import com.example.capstonemovie.search.SearchViewModel
import com.example.core.domain.usecase.MovieInteractor
import com.example.core.domain.usecase.MovieUseCase
import com.example.core.ui.EpoxyCallbacks
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val useCaseModule = module {
    factory<MovieUseCase> { MovieInteractor(get()) }
}

val viewModelModule = module {
    viewModel { HomeViewModel(get()) }
    viewModel { DetailViewModel(get()) }
    viewModel { MainViewModel() }
    viewModel { SearchViewModel(get()) }
}

val uiModule = module {

    factory(named("fragment-glide-request-manager")) { (fragment: Fragment) ->
        Glide.with(fragment)
    }

    factory(named("activity-glide-request-manager")) { (activity: Activity) ->
        Glide.with(activity)
    }

    factory(named("view-glide-request-manager")) { (view: View) ->
        Glide.with(view)
    }

    factory { (callbacks: EpoxyCallbacks, glide: RequestManager) ->
        SearchEpoxyController(callbacks, glide, get(named("epoxy-handler")))
    }

    factory { (callbacks: EpoxyCallbacks, glide: RequestManager) ->
        HomeEpoxyController(callbacks, glide, get(named("epoxy-handler")))
    }

    factory { (callbacks: DetailEpoxyController.MovieDetailsCallbacks, glide: RequestManager) ->
        DetailEpoxyController(callbacks, glide, get(named("epoxy-handler")))
    }

    single(named("epoxy-handler-thread")) {
        HandlerThread("epoxy").apply {
            start()
        }
    }

    single(named("epoxy-handler")) {
        val handlerThread = get<HandlerThread>(named("epoxy-handler-thread"))
        Handler(handlerThread.looper)
    }
}