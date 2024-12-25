package com.aliosman.kelimeoyunuprojesi.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.aliosman.kelimeoyunuprojesi.R
import com.aliosman.kelimeoyunuprojesi.databinding.FragmentBilgiPanelBinding


class BilgiPanel : Fragment() {

    private var _binding: FragmentBilgiPanelBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentBilgiPanelBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //Fragman oluştuktan sonra çağır
        toastmessage()
    }


    fun toastmessage(){
        context?.let{
            val message = resources.getString(R.string.geri_don)
               Toast.makeText(requireContext(),message,Toast.LENGTH_SHORT).show()
        }

    }

}