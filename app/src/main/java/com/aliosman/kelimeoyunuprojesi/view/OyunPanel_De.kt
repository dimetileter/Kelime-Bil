package com.aliosman.kelimeoyunuprojesi.view

import android.content.Context
import android.content.Intent
import android.database.sqlite.SQLiteException
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.aliosman.kelimeoyunuprojesi.R
import com.aliosman.kelimeoyunuprojesi.database.KelimeVarMi
import com.aliosman.kelimeoyunuprojesi.database.SharedPreferencesLevel
import com.aliosman.kelimeoyunuprojesi.util.RekorIslemleri
import com.aliosman.kelimeoyunuprojesi.util.Renkler
import com.aliosman.kelimeoyunuprojesi.database.VeriTabani_De
import com.aliosman.kelimeoyunuprojesi.databinding.ActivityOyunPanelBinding
import com.aliosman.kelimeoyunuprojesi.databinding.BottomSheetDeBinding
import com.aliosman.kelimeoyunuprojesi.databinding.PesEtUyarisiBinding
import com.aliosman.kelimeoyunuprojesi.databinding.SayacBildirimiBinding
import com.aliosman.kelimeoyunuprojesi.databinding.UyariMetniBinding
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Runnable
import kotlinx.coroutines.launch

@Suppress("DEPRECATION")
class OyunPanel_De: AppCompatActivity() {

    private lateinit var binding: ActivityOyunPanelBinding

    private val EditTextList = ArrayList<EditText>()
    private val EditTextHarfTut = ArrayList<String>(5)
    private var tutulanKelime: String? = null
    private var tutulanKelimeAnlami: String? = null
    private var index = 0
    private var kutucuklarDoluMu: Boolean = true
    private var level: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOyunPanelBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        // Fullscreen modunu etkinleştir
        window.decorView.systemUiVisibility = (
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        or View.SYSTEM_UI_FLAG_FULLSCREEN
                        or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                )

        level = SharedPreferencesLevel(this).getLevel()

        //Pes Et
        binding.btnPesEt.setOnClickListener {
            pesEtMenusu()
        }

        //İpucu
        binding.btnIpUcu.setOnClickListener {

            binding.btnIpUcu.isEnabled = false
            binding.btnIpUcu.setBackgroundColor(Color.DKGRAY)
            ipUcu()
        }

        //Zamanlama
        binding.btnZamanlaYaris.setOnClickListener {

            if (!EditTextList[0].text.isNullOrEmpty() || index>0)
            {
                zamanlaYarisUyarisi()
            }
            else
            {
                //Sayacı ve geri sayım bildirimini göster ve butonu işlevsizleştir
                binding.txtZamanlayici.visibility = View.VISIBLE
                binding.txtRekorSure.visibility = View.VISIBLE

                val rekor = getString(R.string.rekor)
                val rekorSuresi = RekorIslemleri(this).rekorSureToString()
                binding.txtRekorSure.text = rekor + " " + rekorSuresi

                binding.btnZamanlaYaris.isEnabled = false
                binding.btnIpUcu.isEnabled = false

                binding.btnZamanlaYaris.setBackgroundColor(Color.DKGRAY)
                binding.btnIpUcu.setBackgroundColor(Color.DKGRAY)

                //Zamanlama işlemlerine git
                geriSayim()
            }
        }

        //Bottom Sheet Dialog
        binding.btnBottomSheet.setOnClickListener {
            buttomSheetDialog()
        }

        CoroutineScope(Dispatchers.IO).launch {
            rastgeleKelimeTut()
        }
        editTextListele()
        metinDinleyiciEkle()
    }

    //Rastgele kelime tut
    private fun rastgeleKelimeTut()
    {
        val rastgeleKelime = VeriTabani_De(this@OyunPanel_De).rastgeleAlmancaKelimeSec(level)
        tutulanKelime = rastgeleKelime[0]
        tutulanKelimeAnlami = rastgeleKelime[1]

        CoroutineScope(Dispatchers.Main).launch {
            Toast.makeText(this@OyunPanel_De, "level: $level", Toast.LENGTH_LONG).show()
        }
    }

    //EditText ögelerini listele
    private fun editTextListele()
    {
        for (i in 1..6) {
            for (j in 1..5) {
                val editTextId = resources.getIdentifier("textbox${i}_${j}", "id", packageName)
                val textbox = findViewById<EditText>(editTextId)
                EditTextList.add(textbox)
            }
        }
    }

    //Bottom Sheet Dialog
    private fun buttomSheetDialog() {
        val bottomSheetDialog = BottomSheetDialog(this)
        val bottomSheetBinding = BottomSheetDeBinding.inflate(layoutInflater)
        bottomSheetDialog.setContentView(bottomSheetBinding.root)
        bottomSheetDialog.show()

        bottomSheetBinding.levelA1Button.setOnClickListener {
            level = "A1"
            SharedPreferencesLevel(this).saveLevel(level)
            bottomSheetDialog.dismiss()
            restartActivity()
            this.finish()
        }

        bottomSheetBinding.levelA2Button.setOnClickListener{
            level = "A2"
            SharedPreferencesLevel(this).saveLevel(level)
            bottomSheetDialog.dismiss()
            restartActivity()
            this.finish()
        }

        bottomSheetBinding.levelB1Button.setOnClickListener{
            level = "B1"
            SharedPreferencesLevel(this).saveLevel(level)
            bottomSheetDialog.dismiss()
            restartActivity()
            this.finish()
        }

        bottomSheetBinding.levelB2Button.setOnClickListener{
            Toast.makeText(this, "Wird in Kürze hinzugefügt", Toast.LENGTH_SHORT).show()
//            level = "B2"
//            SharedPreferencesLevel(this).saveLevel(level)
//            bottomSheetDialog.dismiss()
//            restartActivity()
//            this.finish()
        }
    }



    /**-----------Kutucuk işlemleri-----------**/

    //EditText ögelerindeki değişimi dinle
    private fun metinDinleyiciEkle() {
        var textbox: EditText?

        for (i in 0 until EditTextList.size)
        {
            textbox = EditTextList[i]

            textbox.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

                override fun afterTextChanged(s: Editable?) {
                    //Eğer metin girişi yapılırsa
                    if (!s.isNullOrEmpty())
                    {
                        //Bir sonraki indis 5'in katı ise satırın ilk elemanıdır
                        index = i
                        val nextIndex = if (i + 1 < EditTextList.size) i + 1 else i
                        val satirAtla = if (nextIndex < EditTextList.size) (i + 1) % 5 else 0

                        //Kutucuk değişimlerini ve kelime kontrollerini yap
                        kutucukDegisimIslemleri(satirAtla, nextIndex, i)
                    }
                    else
                    {
                        val lastIndex = if (i - 1 < 0) i else i - 1
                        val nextIndex = if (i + 1 < EditTextList.lastIndex) i + 1 else i
                        EditTextList[lastIndex].requestFocus()
                        EditTextList[nextIndex].isEnabled = false
                    }
                }
            })
        }
        EditTextList[0].requestFocus()
    }

    //Sonraki ve önceki kutucuğa geçişleri kontrol et
    private fun kutucukDegisimIslemleri(satirAtla: Int, nextIndex: Int, i: Int) {
        val ilkIndex = (i / 5) * 5
        val sonIndex = ilkIndex + 4

        //Sonraki satıra geçiliyorsa kelime kontrol edilir
        if ((satirAtla == 0 && i > 3 && kutucuklarDoluMu))
        {
            kelimeKontrol()
        }
        //Eeğer son kutcuk doluysa otomatik olarak kontrole git
        else if (!EditTextList[sonIndex].text.isNullOrEmpty() && index == ilkIndex + 3 && kutucuklarDoluMu)
        {
            //Bu durumda index 4. harftedir ve 1 arttırılarak son harf konumuna getirilir
            index += 1
            kelimeKontrol()
        }
        //Sonraki kutucuk doluysa atlanarak sonrakine geç
        else if (!EditTextList[nextIndex].text.isNullOrEmpty() && nextIndex < 29)
        {
            EditTextList[nextIndex].isEnabled = true
            EditTextList[nextIndex + 1].isEnabled = true
            EditTextList[nextIndex + 1].requestFocus()
        }
        //Sonraki kutucuk boş ise sonraki kutucuğa geç
        else if (kutucuklarDoluMu)
        {
            EditTextList[nextIndex].isEnabled = true
            EditTextList[nextIndex].requestFocus()
        }
    }

    //Rastgele bir harf ver
    private fun ipUcu() {
        //Rastgele index seç ve indexteki harfi al
        val randomIndex = (0 until tutulanKelime!!.length).random()
        val randomHarf = tutulanKelime!![randomIndex].toString()
        val randomHarfKonumu: Int?

        //Eğer ilk harf açılırsa öncesi olamayacağından index+1 yaparak sonraki kutucuğa odaklan
        val oncekiIndex: Int

        if (randomIndex == 4 && index == 0) {
            oncekiIndex = index + 1
        } else if ((randomIndex == 0 || randomIndex == 4) && index < 29) {
            oncekiIndex = index + 1
        } else {
            oncekiIndex = index
        }

        //Eğer sonraki satıra geçiliyorsa bir alt satıra ekleme yapılır ve konum bulunur.
        var satirBasi = (index / 5) * 5
        if ((index + 1) % 5 == 0) {
            satirBasi += 5
            randomHarfKonumu = satirBasi + randomIndex
        } else {
            randomHarfKonumu = satirBasi + randomIndex
        }

        //Eğer son harf verildiyse önceki kutuların doluluğunu kontrol et
        if (randomIndex == 4) {
            kutucuklarDoluMu = kutucuklarDoluMu()
        }

        //Harfi kutucuğa yazdır ve renkleri düzünle, en son kalınan kutcuğa odaklan
        EditTextList[randomHarfKonumu].isEnabled = false
        EditTextList[randomHarfKonumu].text = Editable.Factory.getInstance().newEditable(randomHarf)
        EditTextList[randomHarfKonumu].setBackgroundColor(Renkler(this).mavi)

        //Harf yerleştirildikten sonra kelime kontrolüne izin ver.
        kutucuklarDoluMu = true
        EditTextList[oncekiIndex].requestFocus()

    }



    /**-----------Kontrol işlemleri-----------**/

    //Kelimeyi kontrol et
    private fun kelimeKontrol() {

        //textbox'a girilern harfler sırayla alınır.
        EditTextHarfTut.add(EditTextList[index - 4].text.toString()) //1. HARF
        EditTextHarfTut.add(EditTextList[index - 3].text.toString()) //2. HARF
        EditTextHarfTut.add(EditTextList[index - 2].text.toString()) //3. HARF
        EditTextHarfTut.add(EditTextList[index - 1].text.toString()) //4. HARF
        EditTextHarfTut.add(EditTextList[index].text.toString())   //5. HARF
        val tahminKelime = EditTextHarfTut.joinToString("")

        val varMi = kelimeVarMi(tahminKelime)

        if (varMi)
        {   //Kazandıysa
            if (tahminKelime == tutulanKelime)
            {

                for (i in tutulanKelime!!.indices)
                {
                    EditTextList[index - 4 + i].setBackgroundColor(Renkler(this).yesil)
                    EditTextList[index - 4 + i].setTextColor(Color.WHITE)
                }

                kazandinizMesaji()
                oyunBittiButonlari(true)
                isEnableFalse()
                EditTextHarfTut.clear()
                onPause()
                return
            }
            //Kazanamadıysa ve tahmin hakkı varsa
            else if (index != EditTextList.lastIndex)
            {
                harfRenkKontrol(tahminKelime)
                isEnableFalse()
                EditTextHarfTut.clear()

                EditTextList[index + 1].isEnabled = true
                EditTextList[index + 1].requestFocus()
            }
            //Kazanamadıysa ve Tahmin hakkı bittiyse
            else
            {
                harfRenkKontrol(tahminKelime)
                kaybettinizMesaji()
                oyunBittiButonlari(false)
                isEnableFalse()
                EditTextHarfTut.clear()
                onPause()
                return
            }
        }
        //Kelime yoksa
        else
        {
            kelimeBulunamadiMesaji()
            EditTextHarfTut.clear()
        }
    }

    //Kutuların doluluğunu kontrol et
    private fun kutucuklarDoluMu(): Boolean {

        val newIndex = if ((index % 5) == 4) index + 1 else index

        val satirBasi = (newIndex / 5) * 5
        val satirSonu = satirBasi + 4

        for (i in satirBasi..satirSonu) {
            if (EditTextList[i].text.isNullOrEmpty()) {
                return false
            }
        }
        return true
    }


    //Kelimeyi veri tabanında ara
    private fun kelimeVarMi(tahminKelime: String): Boolean {
        return KelimeVarMi(this, tahminKelime).kelimeVarMi_De(level)
    }

    //Kelimenin harflerini kontrol et
    private fun harfRenkKontrol(tahminKelime: String) {
        //Harfler aynıysa yeşil değil ama ortaksa turuncu
        for (i in 0 until 5) {
            if (tutulanKelime!![i] == tahminKelime[i])
            { //MASAL == BULUT ?  //İFADE == ARABA
                EditTextList[index - 4 + i].setBackgroundColor(Renkler(this).yesil)
                EditTextList[index - 4 + i].setTextColor(Color.WHITE)
            } else if (tutulanKelime!!.contains(tahminKelime[i]))
            {
                EditTextList[index - 4 + i].setBackgroundColor(Renkler(this).turuncu)
            } else
            {
                EditTextList[index - 4 + i].setBackgroundColor(Renkler(this).koyuGri)
            }
        }
    }



    /**-----------Bilgi metinleri ve bsait işlemler-----------**/

    //Tekrar oyna ya da çıkış yap butonlarını göster
    private fun oyunBittiButonlari(kazandiMi: Boolean) {

        zamanlamaDurdur(kazandiMi)

        binding.cardViewTekrarOyna.visibility = View.VISIBLE
        binding.cardviewCikis.visibility = View.VISIBLE
        binding.btnOyunTekrarOyna.isEnabled = true
        binding.btnOyunCikis.isEnabled = true

        //Arayüz butonlarının işlevselliğini kapat
        binding.btnIpUcu.isEnabled = false
        binding.btnPesEt.isEnabled = false
        binding.btnZamanlaYaris.isEnabled = false

        binding.btnOyunTekrarOyna.setOnClickListener {
            finish()
            restartActivity()
        }

        binding.btnOyunCikis.setOnClickListener {
            finish()
        }
    }

    //İşi biten kutucukların erişimini kapat
    private fun isEnableFalse() {
        EditTextList[index - 1].isEnabled = false
        EditTextList[index - 2].isEnabled = false
        EditTextList[index - 3].isEnabled = false
        EditTextList[index - 4].isEnabled = false
        EditTextList[index].isEnabled = false
    }

    //Yeni bir aktivite ile oyunu sıfırla
    private fun restartActivity() {
        val intent = Intent(this, OyunPanel_De::class.java)
        startActivity(intent)
    }

    //Kazandınız mesajı göster
    private fun kazandinizMesaji() {
        val message = resources.getString(R.string.kazanma_mesaji)
        val message2 =  resources.getString(R.string.turkce_anlami)
        binding.txtOyunSonuBilgisi.text = message
        binding.txtOyunSonuBilgisiTurkisch.text = message2 + " " + tutulanKelimeAnlami
    }

    //Mesajı ve tutulan kelimeyi göster
    private fun kaybettinizMesaji() {
        val message = resources.getString(R.string.yenilgi_mesaji)
        val message2 =  resources.getString(R.string.turkce_anlami)
        binding.txtOyunSonuBilgisi.text =  message + tutulanKelime
        binding.txtOyunSonuBilgisiTurkisch.text = message2 + " " + tutulanKelimeAnlami

    }

    //Kelime bulunamadı mesajı göster
    private fun kelimeBulunamadiMesaji() {
        val message = resources.getString(R.string.kelime_bulunamadi)
        Toast.makeText(this,  message, Toast.LENGTH_SHORT).show()
    }



    /**Uyarı bildirimleri ve Zamanlama işlemleri**/

    //Zamanlayıcı değişkenleri
    private lateinit var handler: Handler
    private lateinit var runnable: Runnable
    var sayacVarMi = false

    //Pes et uyarsınını görüntüle
    private fun pesEtMenusu()
    {
        //Uyarı tanımları ve uyarının gösterilmesi
        val alert = AlertDialog.Builder(this)
        val ozelUyariBinding = PesEtUyarisiBinding.inflate(layoutInflater)

        alert.setCancelable(false)
        alert.setView(ozelUyariBinding.root).show()

        zamanlamaDurdur(false)

        //Buton tanımları
        val btn_tekrarOyna = ozelUyariBinding.btnPesEtTekrarOyna
        val btn_cikis = ozelUyariBinding.btnPesEtCikis
        val txt_tutulanKelimeBilgisi = ozelUyariBinding.txtPesEtTutulanKelimeBilgi
        val txt_turkceAnlami = ozelUyariBinding.txtPesEtTurkcAnlami

        val message = resources.getString(R.string.dogru_kelime)
        val message2 = resources.getString(R.string.turkce_anlami)
        val dogruKelime = message + " " + tutulanKelime
        txt_tutulanKelimeBilgisi.text = dogruKelime
        txt_turkceAnlami!!.text = message2 + " " + tutulanKelimeAnlami

        btn_cikis.setOnClickListener {
            finish()
        }

        btn_tekrarOyna.setOnClickListener {
            finish()
            restartActivity()
        }
    }

    //Zamanlayıcıyı başlat
    private fun zamanlamaBaslat()
    {
        var timer: Int = 0

        // Handler ve Runnable oluştur
        handler = Handler()
        runnable = object : Runnable {

            //Her 1000ms de bir aşağıdaki işlemi tekrarla
            override fun run() {

                timer += 1
                val seconds = timer % 60
                val minutes = timer / 60
                binding.txtZamanlayici.text = String.format("%02d:%02d", minutes, seconds)
                handler.postDelayed(this, 1000)
            }
        }
        // İlk çağrıyı başlat
        handler.post(runnable)
    }

    //Zamanlayıcıyı durdur
    private fun zamanlamaDurdur(kazandiMi: Boolean)
    {
        //Handler'ı durdur
        if (sayacVarMi)
        {
            handler.removeCallbacks(runnable)

            if(kazandiMi)
            {
                val txtZamanlayici = binding.txtZamanlayici
                val sure = txtZamanlayici.text.toString()

                //Rekor işlemleri için ayrı sınıfa git
                RekorIslemleri(this).rekorSureyiSorgula(sure)
            }

        }
    }

    //Geris sayim bildirimi
    private fun geriSayim()
    {
        //AlertDialog tanımları
        val alert = AlertDialog.Builder(this)
        val geriSayimBinding = SayacBildirimiBinding.inflate(layoutInflater)

        //AlertDialog özellikleri
        alert.setCancelable(false)
        alert.setView(geriSayimBinding.root)

        val alertDialog = alert.create()
        val backgroundDrawable = ColorDrawable(Color.TRANSPARENT)
        alertDialog.window?.setBackgroundDrawable(backgroundDrawable)
        alertDialog.show()

        //Zamanlama işlemleri
        val txt_geriSayim = geriSayimBinding.txtGeriSayim
        val sayac = arrayOf("3", "2", "1", "0")
        val sayacRenkleri = arrayOf(
            Renkler(this).acikGri,
            Renkler(this).turuncu,
            Renkler(this).yesil,
            Color.WHITE
        )

        object : CountDownTimer(4000, 1000) {
            var index = 0

            override fun onFinish() {
                alertDialog.dismiss()
                sayacVarMi = true
                zamanlamaBaslat()
            }

            override fun onTick(millisUntilFinished: Long) {
                //Her sayımda farklı renkleri kullan
                txt_geriSayim.text = sayac[index]
                txt_geriSayim.setTextColor(sayacRenkleri[index])

                //index 3 iken sayaç 0 da olur ve arkaplan rengi değiştirilir
                if (index == 3) {
                    geriSayimBinding.geriSayimBildirimi.setBackgroundColor(Renkler(this@OyunPanel_De).yesil)
                }
                index++
            }
        }.start()
    }

    //Oyuna başlanmışsa zamanla yarış uyarısı göster
    private fun zamanlaYarisUyarisi()
    {
        val alert = AlertDialog.Builder(this)
        val uyariMetniBinding = UyariMetniBinding.inflate(layoutInflater)

        //AlertDialog özellikleri
        alert.setCancelable(true)
        alert.setView(uyariMetniBinding.root)

        val alertDialog = alert.create()
        val backgroundDrawable = ColorDrawable(Color.TRANSPARENT)
        alertDialog.window?.setBackgroundDrawable(backgroundDrawable)
        alertDialog.show()

        //2000ms sonra uyarıyı kapat
        val handler = Handler()
        handler.postDelayed( {alertDialog.dismiss()}, 2000)

    }

}
