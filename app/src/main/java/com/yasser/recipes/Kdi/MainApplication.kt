package com.yasser.recipes.Kdi

import android.app.Application
import com.yasser.recipes.data.db.AppDatabase
import com.yasser.recipes.data.network.ConnectionStatus
import com.yasser.recipes.data.network.ServiceApi
import com.yasser.recipes.data.preferences.PreferenceProvider
import com.yasser.recipes.repository.RecipeRepository
import com.yasser.recipes.viewmodelfactory.RecipesDetailsViewModelFactory
import com.yasser.recipes.viewmodelfactory.RecipesViewModelFactory
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.androidCoreModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton

class MainApplication():Application(),KodeinAware {
    override val kodein = Kodein.lazy {
        import(androidCoreModule(this@MainApplication))

        bind() from singleton { AppDatabase(instance()) }
        bind() from singleton { ConnectionStatus(instance()) }
        bind() from singleton { ServiceApi(instance()) }
        bind() from singleton { PreferenceProvider(instance()) }

        bind() from singleton { RecipeRepository(instance(), instance(),instance()) }




        bind() from singleton { RecipesViewModelFactory(instance()) }

        bind() from singleton { RecipesDetailsViewModelFactory(instance(),instance()) }


    }
}