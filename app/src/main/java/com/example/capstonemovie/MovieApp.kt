package com.example.capstonemovie

import android.app.Application
import androidx.multidex.MultiDexApplication
import com.example.capstonemovie.di.uiModule
import com.example.capstonemovie.di.useCaseModule
import com.example.capstonemovie.di.viewModelModule
import com.example.core.di.databaseModule
import com.example.core.di.networkModule
import com.example.core.di.repositoryModule
import com.example.core.utils.log
import com.google.android.play.core.splitinstall.SplitInstallManagerFactory
import com.google.android.play.core.splitinstall.SplitInstallRequest
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class MovieApp : Application() {
    override fun onCreate() {
        super.onCreate()
//        installFavModule()
        startKoin {
            androidLogger(Level.NONE)
            androidContext(this@MovieApp)
            modules(
                listOf(
                    databaseModule,
                    networkModule,
                    repositoryModule,
                    useCaseModule,
                    viewModelModule,
                    uiModule
                )
            )
        }
    }
    private fun installFavModule() {
        val splitInstallManager = SplitInstallManagerFactory.create(this)
        val moduleFav = "favourite"
        if (splitInstallManager.installedModules.contains(moduleFav)) {
            toString().log("contains module fav")
        } else {
            val request = SplitInstallRequest.newBuilder()
                .addModule(moduleFav)
                .build()
            splitInstallManager.startInstall(request)
                .addOnSuccessListener {
                    toString().log("succes installed module fav")
                }
                .addOnFailureListener {
                    toString().log("failed installed module fav")
                }
        }
    }
}