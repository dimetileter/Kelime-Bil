package com.aliosman.kelimeoyunuprojesi.util

import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.aliosman.kelimeoyunuprojesi.R

class RekorIslemleri(context: Context) {

    val sharedPreferences: SharedPreferences = context.getSharedPreferences("rekor", Context.MODE_PRIVATE)
    private val context = context




    //Rekor bilgisini kaydet
    private fun rekorSaveInt(sure: Int)
    {
        sharedPreferences.edit().putInt("rekor", sure).apply()
    }

    //Rekor süreyi göster
    private fun rekorGetInt(): Int
    {
        return sharedPreferences.getInt("rekor", 0)
    }

    //Rekor süreyi string formata çevir
    public fun rekorSureToString(): String
    {
        val rekor = rekorGetInt()

        val dakika = rekor / 60
        val saniye = rekor % 60

        return String.format("%02d:%02d", dakika, saniye)

    }

    //Rekor süreyi sorgula
    public fun rekorSureyiSorgula(yeniSure: String)
    {
        val yeniRekor = yeniSure.split(":")
        val eskiRekor = rekorGetInt()

        val yeniDakika = yeniRekor[0].toInt()
        val yeniSaniye = yeniRekor[1].toInt()

        val toplamYeniSaniye = yeniDakika * 60 + yeniSaniye

        if((toplamYeniSaniye < eskiRekor) || eskiRekor == 0)
        {
            yeniRekor(yeniSure)
            rekorSaveInt(toplamYeniSaniye)
        }
    }


    private fun yeniRekor(yeniSure: String)
    {
        val alert = AlertDialog.Builder(context)
        val inflater = LayoutInflater.from(context)
        val yeniRekor = inflater.inflate(R.layout.yeni_rekor, null)

        val btn_tamam = yeniRekor.findViewById<Button>(R.id.btn_tamam)
        val txt_eskiRekor = yeniRekor.findViewById<TextView>(R.id.txt_eskiRekor)
        val txt_yeniRekor = yeniRekor.findViewById<TextView>(R.id.txt_yeniRekor)

        txt_eskiRekor.text = rekorSureToString()
        txt_yeniRekor.text = yeniSure

        alert.setView(yeniRekor)
        val alertDialog = alert.create()
        val backgroundDrawable = ColorDrawable(Color.TRANSPARENT)
        alertDialog.window?.setBackgroundDrawable(backgroundDrawable)
        alertDialog.show()

        btn_tamam.setOnClickListener {
            alertDialog.dismiss()
        }



    }

}