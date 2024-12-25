package com.aliosman.kelimeoyunuprojesi.util

import android.content.Context
import androidx.core.content.ContextCompat
import com.aliosman.kelimeoyunuprojesi.R

class Renkler(context: Context){

    val mavi = ContextCompat.getColor(context, R.color.mavi)
    val yesil = ContextCompat.getColor(context, R.color.yesil)
    val turuncu = ContextCompat.getColor(context, R.color.turuncu)
    val koyuGri = ContextCompat.getColor(context, R.color.boxbackgroundcolorDark)
    val acikGri = ContextCompat.getColor(context, R.color.boxbackgroundcolorLight)
}