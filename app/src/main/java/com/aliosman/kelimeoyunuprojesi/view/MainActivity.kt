package com.aliosman.kelimeoyunuprojesi.view



import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import com.aliosman.kelimeoyunuprojesi.R
import com.aliosman.kelimeoyunuprojesi.database.LanguageManager
import com.aliosman.kelimeoyunuprojesi.database.VeriTabani
import com.aliosman.kelimeoyunuprojesi.database.VeriTabani_De
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class MainActivity : AppCompatActivity() {

    private val decorView: View by lazy { window.decorView }
    private lateinit var languageManager: LanguageManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Fullscreen modunu etkinleştir
        window.decorView.systemUiVisibility = (

                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        or View.SYSTEM_UI_FLAG_FULLSCREEN
                        or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                )

        CoroutineScope(Dispatchers.IO).launch {

            //Daha önce seçilen dil bilgisini al ve dil ayarlarını değiştir
            languageManager = LanguageManager(this@MainActivity)
            val currentLanguage = languageManager.getLocalLanguage()
            languageManager.updateResources(currentLanguage)

            //Eğer Almanca dil seçimi Almanca ise aktivitesini çağırve işlemleri arkaplanda yap
            if (currentLanguage == "de")
            {
                //Veri tabaı yoksa oluştur
                if (!VeriTabani_De(this@MainActivity).veriTabaniVarMi())
                {
                    VeriTabani_De(this@MainActivity)
                }
            }
            else
            {
                //Veri tabanı yoksa oluştur
                if (!VeriTabani(this@MainActivity).veriTabaniVarMi())
                {
                    VeriTabani(this@MainActivity)
                }
            }
        }
    }
}

