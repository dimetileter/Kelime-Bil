package com.aliosman.kelimeoyunuprojesi.database

import android.content.Context
import android.content.res.Configuration
import java.util.Locale

class LanguageManager(private val context: Context) {

    private val languagePref = context.getSharedPreferences("LanguagePrefs", Context.MODE_PRIVATE)

    //Son seçilen dili kaydet
    fun setLocalLanguge(language: String){
        languagePref.edit().putString("language", language).apply()
    }

    //Son seçilen dili getir
    fun getLocalLanguage(): String
    {
        return languagePref.getString("language", "tr") ?: "tr"
    }

    //Dil ayarlarını değiştir
    fun updateResources(language: String)
    {
        val locale = Locale(language)
        Locale.setDefault(locale)
        val config = Configuration()
        config.setLocale(locale)
        context.resources.updateConfiguration(config, context.resources.displayMetrics)
    }


}