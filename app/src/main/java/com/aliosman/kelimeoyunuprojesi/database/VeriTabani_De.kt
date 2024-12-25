package com.aliosman.kelimeoyunuprojesi.database

import android.content.Context
import android.content.SharedPreferences
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import java.sql.SQLException

class VeriTabani_De(val context: Context) {

    private lateinit var database: SQLiteDatabase
    private lateinit var listA1: List<Pair<String,String>>
    private lateinit var listA2: List<Pair<String, String>>
    private lateinit var listB1: List<Pair<String, String>>
    private lateinit var listB2: List<Pair<String, String>>
    private val sharedPreferences: SharedPreferences = context.getSharedPreferences("com.aliosman.wörtspiel", Context.MODE_PRIVATE)
    private val kontrolAnahtarı = "kontrol"

    init  {
        try {
            //Tablo tanımlaması
            database = context.openOrCreateDatabase("Wörter", Context.MODE_PRIVATE, null)
            database.execSQL("CREATE TABLE IF NOT EXISTS A1(id INTEGER PRIMARY KEY, worter VARCHAR, kelime VARCHAR)")
            database.execSQL("CREATE TABLE IF NOT EXISTS A2(id INTEGER PRIMARY KEY, worter VARCHAR, kelime VARCHAR)")
            database.execSQL("CREATE TABLE IF NOT EXISTS B1(id INTEGER PRIMARY KEY, worter VARCHAR, kelime VARCHAR)")
            database.execSQL("CREATE TABLE IF NOT EXISTS B2(id INTEGER PRIMARY KEY, worter VARCHAR, kelime VARCHAR)")

            if(!veriTabaniVarMi())
            {
                worterListA1()
                listA1Kayit()

                worterListA2()
                worterListB1()
                //worterListB2()
                listA2Kayit()
                listB1Kayit()
                //listB2Kayit()
                saveInt()
            }
        }
        catch(ex: SQLException)
        {
            ex.printStackTrace()
        }
    }

    //==========Kelime ekleme işlemleri==========//

    //A1 kelimeleri
    private fun worterListA1()
    {
        listA1 = listOf(
            //Pair("SECHS","ALTI"),
            Pair("ZWÖLF","ON İKİ"),
            Pair("NACHT","GECE"),
            Pair("ABEND","AKŞAM"),
            Pair("WOCHE","HAFTA"),
            Pair("APRIL","NİSAN"),
            Pair("LITER","LİTRE"),
            Pair("PFUND","POUND"),
            Pair("GRAMM","GRAM"),
            Pair("METER","METRE"),
            Pair("BRAUN","KAHVERENGİ"),
            Pair("SÜDEN","GÜNEY"),
            Pair("OSTEN","DOĞU"),
            Pair("ANRUF","ÇAĞRI"),
            Pair("APFEL","ELMA"),
            Pair("BADEN","BANYO YAPMAK"),
            Pair("BAUCH","KARIN"),
            Pair("BERUF","MESLEK"),
            Pair("BIRNE","ARMUT"),
            Pair("BITTE","LÜTFEN"),
            Pair("BRIEF","MEKTUP"),
            Pair("DANKE","TEŞEKKÜR"),
            Pair("DATUM","TARİH"),
            Pair("DURST","SUSAMAK"),
            Pair("EILIG","ACELE ETMEK"),
            Pair("ESSEN","YEMEK"),
            Pair("FEIER","KUTLAMA"),
            Pair("FEUER","ATEŞ"),
            Pair("FISCH","BALIK"),
            Pair("FRAGE","SORU"),
            Pair("FREMD","YABANCI"),
            Pair("GEBEN","VERMEK"),
            Pair("GEHEN","GİTMEK"),
            Pair("GLEIS", "PARÇA"),
            Pair("GLÜCK", "MUTLULUK"),
            Pair("GRÖßE", "BOYUT"),
            Pair("HALLE", "SALON"),
            Pair("HALLO", "MERHABA"),
            Pair("HANDY", "TELEFON"),
            Pair("HEUTE", "BUGÜN"),
            Pair("HILFE", "YARDIM"),
            Pair("HOBBY", "HOBİ"),
            Pair("HÖREN", "DUYMAK"),
            Pair("HOTEL", "OTEL"),
            Pair("JACKE", "CEKET"),
            Pair("JUNGE", "GENÇ"),
            Pair("KARTE", "HARİTA"),
            Pair("KLEIN", "KÜÇÜK"),
            Pair("KONTO", "HESAP"),
            Pair("KRANK", "HASTA"),
            Pair("KÜCHE", "MUTFAK"),
            Pair("KUNDE", "MÜŞTERİ"),
            Pair("LADEN", "MAĞAZA"),
            Pair("LANGE", "UZUN"),
            Pair("LEBEN", "HAYAT"),
            Pair("LEDIG", "MEVCUT"),
            Pair("LEGEN", "YER"),
            Pair("LEISE", "SESSİZLİK"),
            Pair("LESEN", "OKUMAK"),
            Pair("LICHT", "IŞIK"),
            Pair("LINKS", "BAĞLANTI"),
            Pair("LOKAL", "BÖLGE"),
            Pair("MIETE", "KİRALIK"),
            Pair("MILCH", "SÜT"),
            Pair("MITTE", "ORTA"),
            Pair("MÖBEL", "MOBİLYA"),
            Pair("MÖGEN", "BEĞENMEK"),
            Pair("PARTY", "PARTİ"),
            Pair("PAUSE", "MOLA"),
            Pair("PLATZ", "BOŞLUK"),
            Pair("PREIS", "FİYAT"),
            Pair("REGEN", "YAĞMUR"),
            Pair("REISE", "YOLCULUK"),
            Pair("SALAT", "SALATA"),
            Pair("SCHÖN", "GÜZEL"),
            Pair("SCHUH", "AYAKKABI"),
            Pair("SONNE", "GÜNEŞ"),
            Pair("SPIEL", "OYUN"),
            Pair("SPORT", "SPOR"),
            Pair("STADT", "ŞEHİR"),
            Pair("STOCK", "ÇUBUK"),
            Pair("THEMA", "KONU"),
            Pair("TISCH", "MASA"),
            Pair("UNTER", "ALTINDA"),
            Pair("VATER", "BABA"),
            Pair("WOHER", "NEREDEN"),
            Pair("WOHIN", "NERESİ"),
            Pair("LAMPE","LAMBA")
        )
    }

    //A2 kelimeleri
    private fun worterListA2()
    {
        listA2 = listOf(
            Pair("ONKEL","AMCA"),
            Pair("TANTE","BİRÇOK"),
            Pair("LEDIG","MEVCUT"),
            Pair("MUSİK","MÜZİK"),
            Pair("SPORT","SPOR"),
            Pair("AKTİV","AKTİF"),
            Pair("ALTER","YAŞ"),
            Pair("AMPEL","TRAFİK IŞIĞI"),
            Pair("ANRUF","ÇAĞRI"),
            Pair("APFEL","ELMA"),
            Pair("AUßER","HAİRÇ"),
            Pair("BÄCKT","PİŞİRMEK"),
            Pair("BADEN","YIKANMAK"),
            Pair("BAUCH","GÖBEK"),
            Pair("BERUF","İŞ"),
            Pair("WOLKE","BULUT"),
            Pair("BIRNE","ARMUT"),
            Pair("BITTE","LÜTFEN"),
            Pair("BLATT","YAPRAK"),
            Pair("BLOND","SARIŞIN"),
            Pair("BLUME","ÇİÇEK"),
            Pair("BLUSE","BLUZ"),
            Pair("BOHNE","FASULYE"),
            Pair("BREIF","MEKTUP"),
            Pair("CREME","KREM"),
            Pair("DANKE","TEŞEKÜR"),
            Pair("DATEI","DOSYA"),
            Pair("DATUM","TARİH"),
            Pair("EILIG","ACELE"),
            Pair("ENDEN","SON"),
            Pair("ETWAS","BİRŞEY"),
            Pair("FLUSS","NEHİR"),
            Pair("FRAGE","SORU"),
            Pair("FREMD","YABANCI"),
            Pair("FREUT","MUTLU"),
            Pair("GABEL","ÇATAL"),
            Pair("GEGEN","AYKIRI"),
            Pair("GENAU","KESİNLİKLE"),
            Pair("GENUG","YETERLİ"),
            Pair("GERÄT","CİHAZ"),
            Pair("GLESİ","PARÇA"),
            Pair("GLÜCK","MUTLULUK"),
            Pair("GRÖßE","BOYUT"),
            Pair("HALLE","SALON"),
            Pair("HALLO","MERHABA"),
            Pair("HANDY","TELEFON"),
            Pair("HEUTE","BUGÜN"),
            Pair("HILFE","YARDIM"),
            Pair("HOBBY","HOBİ"),
            Pair("HÖREN","DUYMAK"),
            Pair("HOTEL","OTEL"),
            Pair("IMMER","HER ZAMAN"),
            Pair("INSEL","ADA"),
            Pair("JACKE","CEKET"),
            Pair("JEANS","KOT PANTALON"),
            Pair("JEDER","HERKES"),
            Pair("JETZT","ŞİMDİ"),
            Pair("JUNGE","GENÇ"),
            Pair("KARTE","HARİTA"),
            Pair("KATZE","KEDİ"),
            Pair("KETTE","ZİNCİR"),
            Pair("KIRCHE","KİLİSE"),
            Pair("KLEID","ELBİSE"),
            Pair("KLEIN","KÜÇÜK"),
            Pair("KONTO","HESAP"),
            Pair("KRANK","HASTA"),
            Pair("KÜCHE","MUTFAK"),
            Pair("LADEN","MAĞAZA"),
            Pair("LAMPE","LAMBA"),
            Pair("LANGE","UZUN"),
            Pair("LEBEN","HAYAT"),
            Pair("LEGEN","YER"),
            Pair("LAISE","SESSİZ"),
            Pair("LESEN","OKUMAK"),
            Pair("LEUTE","İNSANLAR"),
            Pair("LİCHT","IŞIK"),
            Pair("LOKAL","BÖLGE"),
            Pair("MAGEN","KARIN"),
            Pair("MARKT","MARKET"),
            Pair("MENGE","TUTAR"),
            Pair("MESSE","ADİL"),
            Pair("MIETE","KİRALIK"),
            Pair("MİLCH","SÜT"),
            Pair("MITTE","ORTA"),
            Pair("MÖBEL","MOBİLYA"),
            Pair("MOTOR","MOTOR"),
            Pair("NEBEN","YANINDA"),
            Pair("NICHT","DEĞİL"),
            Pair("NOTIZ","NOT"),
            Pair("NUDEL","ÇIPLAK"),
            Pair("OFFEN","AÇIK"),
            Pair("PAKET","PAKET"),
            Pair("PAUSE","MOLA"),
            Pair("PFERD","AT"),
            Pair("PİZZA","PİZZA"),
            Pair("PREIS","FİYAT"),
            Pair("RADIO","RADYO"),
            Pair("RATEN","TAHMİN"),
            Pair("REGEN","YAĞMUR"),
            Pair("REIHE","SERİ"),
            Pair("REISE","YOLCULUK"),
            Pair("RUHIG","SESSİZLİK"),
            Pair("SACHE","ŞEY"),
            Pair("SALAT","SALATA"),
            Pair("SAUER","EKŞİ"),
            Pair("SCHÖN","GÜZEL"),
            Pair("SCHUH","AYAKKABI"),
            Pair("SEHEN","GÖRMEK"),
            Pair("SEIFE","SABUN"),
            Pair("SOGAR","EŞİT"),
            Pair("SONNE","GÜNEŞ"),
            Pair("SPIEL","OYUN"),
            Pair("STADT","ŞEHİR"),
            Pair("STARK","ŞİDDETLİ"),
            Pair("STIFT","KALEM"),
            Pair("STOCK","YIĞIN"),
            Pair("STUHL","SANDALYE"),
            Pair("SUPPE","ÇORBA"),
            Pair("TAFEL","KARA TAHTA"),
            Pair("TASSE","FİNCAN"),
            Pair("TEUER","PAHALI"),
            Pair("THEMA","KONU"),
            Pair("TISCH","MASA"),
            Pair("TITEL","BAŞLIK"),
            Pair("TORTE","TURTA"),
            Pair("TRAUM","HAYAL"),
            Pair("UNTEN","ALTINDA"),
            Pair("UNTER","ALTINDA"),
            Pair("VOGEL","KUŞ"),
            Pair("WAGEN","ARABA"),
            Pair("WARUM","NEDEN"),
            Pair("WEICH","YUMUŞAK"),
            Pair("WENIG","BİRKAÇ"),
            Pair("WOHER","NEREDEN"),
            Pair("WOHIN","NERESİ"),
            Pair("ABEND","AKŞAM"),
            Pair("GEBEN","VERMEK")


        )
    }

    //B1 kelimeleri
    private fun worterListB1()
    {
        listB1 = listOf(
            Pair("ABWART","BEKÇİ"),
            Pair("ALARM","ALARM"),
            Pair("ALTER","ESKİ"),
            Pair("AMPEL","LAMBA"),
            Pair("ANGST","KORKU"),
            Pair("ANRUF","ÇAĞRI"),
            Pair("ANZUG","TAKIM ELBİSE"),
            Pair("APFEL","ELMA"),
            Pair("ÄRGER","KIZMAK"),
            Pair("ATMEN","NEFES ALMAK"),
            Pair("AUTOR","YAZAR"),
            Pair("BADEN","YIKANMAK"),
            Pair("BAUCH","KARIN"),
            Pair("BAUER","ÇİFTÇİ"),
            Pair("BERUF","İŞ"),
            Pair("BEVOR","ÖNCE"),
            Pair("BIRNE","ARMUT"),
            Pair("BLASS","SOLGUN"),
            Pair("BLATT","YAPRAK"),
            Pair("BLICK","GÖRÜŞ"),
            Pair("BLUME","ÇİÇEK"),
            Pair("BLOND","SARIŞIN"),
            Pair("BLUSE","BLUZ"),
            Pair("BODEN","ZEMİN"),
            Pair("BONEN","FASULYE"),
            Pair("BREIT","GENİŞ"),
            Pair("BRIEF","MEKTUP"),
            Pair("BRUST","GÖĞÜS"),
            Pair("JUNGE","OĞLAN"),
            Pair("BÜHNE","SAHNE"),
            Pair("COUCH","KANEPE"),
            Pair("CREME","KREM"),
            Pair("DANKE","TEŞEKKÜR"),
            Pair("DATUM","TARİH"),
            Pair("DECKE","TAVAN"),
            Pair("DRECK","KİR"),
            Pair("DROGE","İLAÇ"),
            Pair("DURST","SUSAMAK"),
            Pair("ENKEL","TORUN"),
            Pair("ERNTE","HASAT"),
            Pair("ESSEN","YEMEK"),
            Pair("ESSIG","SİRKE"),
            Pair("EXTRA","EKSTRA"),
            Pair("FARBE","RENK"),
            Pair("FEIER","PARTİ"),
            Pair("FIGUR","FİGÜR"),
            Pair("FIRMA","ŞİRKET"),
            Pair("FLECK","LEKE"),
            Pair("FLÖTE","FLÜT"),
            Pair("FLUSS","NEHİR"),
            Pair("FREMD","YABANCI"),
            Pair("GABEL","ÇATAL"),
            Pair("GENUG","YETERLİ"),
            Pair("GENAU","KESİNLİKLE"),
            Pair("GERÄT","CİHAZ"),
            Pair("GLATT","KAYGAN"),
            Pair("GLÜCK","MUTLULUK"),
            Pair("GRILL","IZGARA"),
            Pair("HAFEN","LİMAN"),
            Pair("HALLE","SALON"),
            Pair("HALLO","MERHABA"),
            Pair("HİLFE","YARDIM"),
            Pair("HEUTE","BUGÜN"),
            Pair("HITZE","SICAKLIK"),
            Pair("HONIG","BAL"),
            Pair("HOTEL","OTEL"),
            Pair("HÜGEL","TEPE"),
            Pair("HUMOR","MİZAH"),
            Pair("HÜTTO","KULÜBE"),
            Pair("IDEAL","IDEAL"),
            Pair("IMMER","HER ZAMAN"),
            Pair("INSEL","ADA"),
            Pair("JACKE","CEKET"),
            Pair("JETZT","ŞİMDİ"),
            Pair("KABEL","KABLO"),
            Pair("KAKAO","KAKAO"),
            Pair("KANAL","KANAL"),
            Pair("KANNE","ÇAYDANLIK"),
            Pair("KARTE","HARİTA"),
            Pair("KASSE","KASA"),
            Pair("KERZE","MUM"),
            Pair("KETTE","ZİNCİR"),
            Pair("KİSTE","KUTU"),
            Pair("KLEID","ELBİSE"),
            Pair("KLIMA","KLİMA"),
            Pair("KNOPF","DÜĞME"),
            Pair("KÖNIG","KRAL"),
            Pair("KONTO","HESAP"),
            Pair("KOPIE","KOPYA"),
            Pair("KRAFT","GÜÇ"),
            Pair("KRANK","HASTA"),
            Pair("KREIS","DAİRE"),
            Pair("KREUZ","ÇARPI"),
            Pair("KRIEG","SAVAŞ"),
            Pair("KRISE","KRİZ"),
            Pair("KÜCHE","MURFAK"),
            Pair("KUNDE","MÜŞTERİ"),
            Pair("KUNST","SANAT"),
            Pair("KURVE","VİRAJ"),
            Pair("KÜSTE","SAHİL"),
            Pair("LADEN","DÜKKAN"),
            Pair("LANGE","UZUN"),
            Pair("LAUNE","MORAL"),
            Pair("LEBEN","HAYAT"),
            Pair("LEDIG","BEKAR"),
            Pair("LEHRE","ÖĞRETMEK"),
            Pair("LICHT","IŞIK"),
            Pair("LINIE","IŞIK"),
            Pair("LIPPE","DUDAK"),
            Pair("LISTE","LİSTE"),
            Pair("LOBEN","ÖVMEK"),
            Pair("LOCAL","YEREL"),
            Pair("LÖSEN","ÇÖZMEK"),
            Pair("LÜGEN","YALAN"),
            Pair("MAGEN","KARIN"),
            Pair("MAPPE","HARİTA"),
            Pair("MANTEL","CEKET"),
            Pair("MARKT","MARKET"),
            Pair("MAUER","DUVAR"),
            Pair("MENGE","TUTAR"),
            Pair("MEMSA","KAFETERYA"),
            Pair("MESSE","ADİL"),
            Pair("MILCH","SÜT"),
            Pair("MITTE","ORTA"),
            Pair("MÖBEL","MOBİLYA"),
            Pair("MÖHRE","HAVUÇ"),
            Pair("MOTOR","MOTOR"),
            Pair("MÜNZE","MADENİ PARA"),
            Pair("MUSIK","MÜZİK"),
            Pair("MÜSLI","MISIR GEVREĞİ "),
            Pair("MUTIG","CESUR"),
            Pair("NADEL","İĞNE"),
            Pair("NAGEL","TIRNAK"),
            Pair("NAME","İSİM"),
            Pair("NATUR","DOĞA"),
            Pair("NABEL","SİS"),
            Pair("NEBEN","YANINDA"),
            Pair("NEGFE","YEĞEN"),
            Pair("NICHT","DEĞİL"),
            Pair("NOTIZ","NOT"),
            Pair("NUDEL","ŞEHRİYE"),
            Pair("OFFEN","AÇIK"),
            Pair("ONKEL","AMCA"),
            Pair("OPFER","KURBAN"),
            Pair("OZEAN","OKYANUS"),
            Pair("PAKET","PAKET"),
            Pair("PANNE","ARIZA"),
            Pair("PARTY","PARTİ"),
            Pair("PASSIV","PASİF"),
            Pair("PAUSE","MOLA"),
            Pair("PILLE","HAP"),
            Pair("PIZZA","PİZZA"),
            Pair("PLATZ","BOŞLUK"),
            Pair("PREIS","FİYAT"),
            Pair("PRIMA","İYİ"),
            Pair("PROFI","PROFESYONEL"),
            Pair("PROST","ŞEREFE"),
            Pair("PUNKT","NOKTA"),
            Pair("PUPPE","OYUNCAK BEBEK"),
            Pair("RADIO","RADYO"),
            Pair("RASEN","ÇİM"),
            Pair("RECHT","DOĞRU"),
            Pair("REGAL","RAF"),
            Pair("REGEL","KURAL"),
            Pair("REGION","BÖLGE"),
            Pair("REIHE","SERİ"),
            Pair("ROMAN","ROMAN"),
            Pair("RUNDE","YUVARLAK"),
            Pair("SACHE","ŞEY"),
            Pair("SALAT","SALATA"),
            Pair("SALBE","MERHEM"),
            Pair("SALON","SALON"),
            Pair("SAUER","EKŞİ"),
            Pair("SCHON","HENÜZ"),
            Pair("SCHÖN","GÜZEL"),
            Pair("SEIFE","SABUN"),
            Pair("SEITE","SAYFA"),
            Pair("SERIE","SERİ"),
            Pair("SOCKE","ÇORAP"),
            Pair("SOGAR","EŞİT"),
            Pair("SONNE","GÜNEŞ"),
            Pair("SPORT","SPOR"),
            Pair("STADT","ŞEHİR"),
            Pair("STAUB","TOZ"),
            Pair("STEIL","DİK"),
            Pair("STEIN","TAŞ"),
            Pair("STERN","YILDIZ"),
            Pair("STIFT","KALEM"),
            Pair("STILL","SESSİZLİK"),
            Pair("STOFF","KUMAŞ"),
            Pair("STOLZ","GURUR"),
            Pair("STROM","MEVCUT"),
            Pair("STUFE","SAHNE"),
            Pair("STUHL","SANDALYE"),
            Pair("STURM","FIRTINA"),
            Pair("SUMME","TOPLAM"),
            Pair("SUPPE","ÇORBA"),
            Pair("SUPER","SÜPER"),
            Pair("TAFEL","KARA TAHTA"),
            Pair("TANTE","TEYZE"),
            Pair("TASSE","FİNCAN"),
            Pair("TEMPO","TEMPO"),
            Pair("TENNIS","TENİS"),
            Pair("TEUER","MASRAFLI"),
            Pair("TISCH","MASA"),
            Pair("TORTE","TURTA"),
            Pair("TOTAL","TOPLAM"),
            Pair("TRÄNE","GÖZYAŞI"),
            Pair("UNTER","ALTINDA"),
            Pair("VATER","BABA"),
            Pair("VIDEO","VİDEO"),
            Pair("VIRUS","VİRÜS"),
            Pair("VISUM","VİZE"),
            Pair("WAGEN","ARABA"),
            Pair("WARUM","NEDEN"),
            Pair("WEICH","YUMUŞAK"),
            Pair("WIESE","ÇAYIR"),
            Pair("WOHER","NEREDEN"),
            Pair("WOHIN","NERESİ"),
            Pair("WOLKE","BULUT"),
            Pair("WOLLE","YÜN"),
            Pair("WURST","SOSİS"),
            Pair("ZEILE","ÇİZGİ"),
            Pair("ZEUGE","ŞAHİT"),
            Pair("ZWECK","AMAÇ"),
            Pair("ABEND","AKŞAM"),
            Pair("GEBEN","VERMEK"),
            Pair("LAMPE","LAMBA")
        )

    }

    //B2 kelimeleri
    private fun worterListB2()
    {
        listB2 = listOf(

        )
    }



    //==========Veri tabanı kayıt işlemleri==========//

    //A1 veri tabanı kaydı
    private fun listA1Kayit()
    {
        try
        {
            for (kelime in listA1){
                val kelimeKayit = "INSERT INTO A1 (worter,kelime) VALUES ('${kelime.first}','${kelime.second}')"
                database.execSQL(kelimeKayit)
            }
        }
        catch (e: SQLException)
        {
            e.printStackTrace()
        }
        finally
        {

        }
    }

    //A2 veri tabanı kaydı
    private fun listA2Kayit()
    {
        try
        {
            for (kelime in listA2){
                val kelimeKayit = "INSERT INTO A2 (worter,kelime) VALUES ('${kelime.first}','${kelime.second}')"
                database.execSQL(kelimeKayit)
            }
        }
        catch (e: SQLException)
        {
            e.printStackTrace()
        }
        finally {
        }
    }

    //B1 veri tabanı kaydı
    private fun listB1Kayit()
    {
        try
        {
            for (kelime in listB1){
                val kelimeKayit = "INSERT INTO B1 (worter,kelime) VALUES ('${kelime.first}','${kelime.second}')"
                database.execSQL(kelimeKayit)
            }
        }
        catch (e: SQLException)
        {
            e.printStackTrace()
        }
        finally {
        }
    }

    //B2 veri tabanı kaydı
    private fun listB2Kayit()
    {
        try
        {
            for (kelime in listB2){
                val kelimeKayit = "INSERT INTO B2 (worter,kelime) VALUES ('${kelime.first}','${kelime.second}')"
                database.execSQL(kelimeKayit)
            }
        }
        catch (e: SQLException)
        {
            e.printStackTrace()
        }
        finally {
        }
    }



    //==========Kelime çekme işlemleri==========//

    //A1 kelimelerini çek
    fun rastgeleAlmancaKelimeSec(level: String?): ArrayList<String>
    {

        val secilenKelimeDe = ArrayList<String>()
        var cursor: Cursor? = null

        try
        {
            cursor = database.rawQuery("SELECT * FROM $level ORDER BY RANDOM() LIMIT 1", null)

            cursor?.let {
                if (cursor.moveToFirst())
                {
                    val worterIndex =  cursor.getColumnIndex("worter")
                    val kelimeIndex = cursor.getColumnIndex("kelime")

                    secilenKelimeDe.add(cursor.getString(worterIndex))
                    secilenKelimeDe.add(cursor.getString(kelimeIndex))
                }
            }
        }catch (e: SQLException)
        {
            e.printStackTrace()
        }
        finally {
            cursor?.close()
        }
        return secilenKelimeDe
    }





    //==========Shared Preferances işlemleri=========//

    //Veri tabanı var mı?
    fun veriTabaniVarMi(): Boolean
    {
        return getInt() == 1
    }

    //Shared veri tabanını al
    private fun getInt(): Int
    {
        return sharedPreferences.getInt(kontrolAnahtarı, 0)
    }

    //Veri tabanı durumunu shared preferancec ile tut
    private fun saveInt()
    {
        sharedPreferences.edit().putInt(kontrolAnahtarı, 1).apply()
    }

}