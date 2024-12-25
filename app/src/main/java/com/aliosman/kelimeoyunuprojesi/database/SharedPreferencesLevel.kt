package com.aliosman.kelimeoyunuprojesi.database

import android.content.Context

class SharedPreferencesLevel(context: Context) {


    private var sharedPreferences = context.getSharedPreferences("com.aliosman.kelimeoyunuprojesi.database" , Context.MODE_PRIVATE)

    fun getLevel(): String
    {
        return sharedPreferences.getString("level", "A1") ?: "A1"
    }

    fun saveLevel(level: String?)
    {
        sharedPreferences.edit().putString("level" , level).apply()
    }

}