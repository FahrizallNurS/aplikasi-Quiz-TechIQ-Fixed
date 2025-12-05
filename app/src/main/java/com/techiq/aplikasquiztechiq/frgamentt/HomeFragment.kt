package com.techiq.aplikasquiztechiq.frgamentt

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController
import com.techiq.aplikasquiztechiq.R

class HomeFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }
        // Inflate the layout for this fragment
        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            super.onViewCreated(view, savedInstanceState)

            val btnMulai = view.findViewById<Button>(R.id.btn_mulai_kuis)

            btnMulai.setOnClickListener {
                findNavController().navigate(R.id.PilihBahasa)
            }
    }
}