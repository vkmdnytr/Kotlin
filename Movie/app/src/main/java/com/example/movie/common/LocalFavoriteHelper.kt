package com.example.movie.common


import android.content.SharedPreferences
import com.example.movie.entity.model.data.favorite.LocalFavoriteMovie
import com.example.movie.ui.MainActivity.Companion.mainApp
import com.google.gson.Gson

object LocalFavoriteHelper {
    private const val FAVORITE_LIST_KEY = "FAVORITE_LIST"

    private fun getFavoriteSharedPreferences(): SharedPreferences {
        return mainApp.getSharedPreferences(FAVORITE_LIST_KEY, 0)
    }
    fun addFavoriteToLocal(localFavoriteFlightItem: LocalFavoriteMovie) {
        getFavoriteSharedPreferences().edit().putString(localFavoriteFlightItem.id.toString(), Gson().toJson(localFavoriteFlightItem)).apply()
    }
    fun removeFavoriteToLocal(localFavoriteFlightItem: LocalFavoriteMovie) {
        getFavoriteSharedPreferences().edit().remove(localFavoriteFlightItem.id.toString()).apply()
    }
    fun getLocalFavoriteList(): List<LocalFavoriteMovie> {
        val gson = Gson()
        val stringFavoriteList = getFavoriteSharedPreferences().all.values.toList() as List<String>
        return stringFavoriteList.asSequence().map<String, LocalFavoriteMovie> {
            gson.fromJson(it, LocalFavoriteMovie::class.java)
        }.toList()
    }
    fun isExist(id: String): Boolean {
        return getFavoriteSharedPreferences().contains(id)
    }


}