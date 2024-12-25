package com.aliosman.kelimeoyunuprojesi.view

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.aliosman.kelimeoyunuprojesi.database.LanguageManager
import com.aliosman.kelimeoyunuprojesi.databinding.FragmentGirisPanelBinding

class GirisPanel : Fragment() {

    private var _binding: FragmentGirisPanelBinding? = null
    private val binding get() = _binding!!

    private lateinit var languageManager: LanguageManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }

        languageManager = LanguageManager(requireContext())

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentGirisPanelBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Butona tıklanınca ikinci fragmana git
        binding.btnBilgi.setOnClickListener {
            btn_bilgi(it)
        }

        binding.btnBasla.setOnClickListener {
            btn_basla()
        }

        binding.btnLanguageTr.setOnClickListener{
            tr()
        }

        binding.btnLanguageDe.setOnClickListener {
            de()
        }

    }

    //Dili Almanca olarak çevir
    private fun de()
    {
        val newLanguage = "de"
        languageManager.setLocalLanguge(newLanguage)

        val intent = Intent(requireContext(), MainActivity::class.java)
        startActivity(intent)
        requireActivity().finish()
    }

    private fun tr()
    {
        val newLanguage = "tr"
        languageManager.setLocalLanguge(newLanguage)

        val intent = Intent(requireContext(), MainActivity::class.java)
        startActivity(intent)
        requireActivity().finish()
    }


    //Bilgi butonu
    private fun btn_bilgi(it: View){

        val action = GirisPanelDirections.actionGirisPanelToBilgiPanel()
        Navigation.findNavController(it).navigate(action)
    }

    //Başla butonu
    private fun btn_basla() {
        val currentLanguage = languageManager.getLocalLanguage()
        if (currentLanguage == "de")
        {
            val intent = Intent(requireContext(), OyunPanel_De::class.java)
            startActivity(intent)
        }
        else
        {
            val intent = Intent(requireContext(), OyunPanel::class.java)
            startActivity(intent)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}





