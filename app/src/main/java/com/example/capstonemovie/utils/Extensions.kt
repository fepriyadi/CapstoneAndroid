package com.example.capstonemovie.utils

import android.database.Observable
import android.util.Log
import androidx.fragment.app.Fragment
import com.airbnb.epoxy.CarouselModelBuilder
import com.airbnb.epoxy.CarouselModel_
import com.airbnb.epoxy.EpoxyController
import com.airbnb.epoxy.EpoxyModel
import com.example.core.BuildConfig
import com.google.android.material.textfield.TextInputEditText

class Extensions {

    fun CarouselModelBuilder.withModelsFrom(models: List<EpoxyModel<*>>): CarouselModelBuilder {
        return this.models(models)
    }

    /** For use in the buildModels method of EpoxyController. A shortcut for creating a Carousel model, initializing it, and adding it to the controller.
     *
     */
    private inline fun EpoxyController.carousel(modelInitializer: CarouselModelBuilder.() -> Unit) {
        CarouselModel_().apply {
            modelInitializer()
        }.addTo(this)
    }

    /** Add models to a CarouselModel_ by transforming a list of items into EpoxyModels.
     *
     * @param items The items to transform to models
     * @param modelBuilder A function that take an item and returns a new EpoxyModel for that item.
     */
    private inline fun <T> CarouselModelBuilder.withModelsFrom(
        items: List<T>,
        modelBuilder: (T) -> EpoxyModel<*>
    ) {
        models(items.map { modelBuilder(it) })
    }
}