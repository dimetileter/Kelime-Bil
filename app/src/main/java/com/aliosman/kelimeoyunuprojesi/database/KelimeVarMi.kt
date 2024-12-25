package com.aliosman.kelimeoyunuprojesi.database

import android.content.Context
import android.database.sqlite.SQLiteException

class KelimeVarMi(val context: Context, val tahminKelime: String) {

    //Almanca kelimeyi sorgula
    fun kelimeVarMi_De(level: String?): Boolean
    {
        try {
            val database = context.openOrCreateDatabase("Wörter", Context.MODE_PRIVATE, null)
            val cursor = database.rawQuery("SELECT * FROM $level WHERE worter = ?", arrayOf(tahminKelime))

            //Eğer veri varsa cursor içinde yer alacağından eleman sayısı sıfırdan büyük olur
            val varMi = cursor.count > 0
            cursor.close()
            return varMi

        } catch (e: SQLiteException) {
            e.printStackTrace()
        }
        return false
    }

    //Kelimeyi sorgula
    fun kelimeVarMi_Tr(): Boolean
    {
        try {
            val database = context.openOrCreateDatabase("Kelimeler", Context.MODE_PRIVATE, null)
            val cursor = database.rawQuery("SELECT * FROM kelimeler WHERE kelime = ?", arrayOf(tahminKelime))

            //Eğer veri varsa cursor içinde yer alacağından eleman sayısı sıfırdan büyük olur
            val varMi = cursor.count > 0
            cursor.close()

            return varMi

        } catch (e: SQLiteException) {
            e.printStackTrace()
        }
        return false
    }

    //Ayıp kelimeyi sorgula
    fun ayipKelimeVarMi_Tr(): Boolean
    {
        try {
            val database = context.openOrCreateDatabase("Kelimeler", Context.MODE_PRIVATE, null)
            val cursor = database.rawQuery("SELECT * FROM kelimeler WHERE ayipKelime = ?", arrayOf(tahminKelime))

            //Eğer veri varsa cursor içinde yer alacağından eleman sayısı sıfırdan büyük olur
            val ayipMi = cursor.count > 0
            cursor.close()

            return ayipMi

        } catch (e: SQLiteException) {
            e.printStackTrace()
        }
        return false
    }
}