package com.masscode.animesuta.core.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.masscode.animesuta.core.data.AnimeRepository
import com.masscode.animesuta.core.di.Injection
import com.masscode.animesuta.detail.DetailAnimeViewModel
import com.masscode.animesuta.favorite.FavoriteViewModel
import com.masscode.animesuta.home.HomeViewModel

class ViewModelFactory private constructor(private val animeRepository: AnimeRepository) :
    ViewModelProvider.NewInstanceFactory() {

    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null

        fun getInstance(context: Context): ViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: ViewModelFactory(Injection.provideRepository(context))
            }
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(HomeViewModel::class.java) -> {
                HomeViewModel(animeRepository) as T
            }
            modelClass.isAssignableFrom(FavoriteViewModel::class.java) -> {
                FavoriteViewModel(animeRepository) as T
            }
            modelClass.isAssignableFrom(DetailAnimeViewModel::class.java) -> {
                DetailAnimeViewModel(animeRepository) as T
            }
            else -> throw Throwable("Unknown ViewModel class: " + modelClass.name)
        }
    }
}