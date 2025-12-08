package com.techiq.aplikasquiztechiq.frgamentt

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import com.techiq.aplikasquiztechiq.R

class HomeFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val btnMulai = view.findViewById<Button>(R.id.btn_mulai_kuis)
        val totalSkorView = view.findViewById<TextView>(R.id.tv_total_skor_number)

        val sharedPref = requireActivity().getSharedPreferences("quiz_prefs", android.content.Context.MODE_PRIVATE)
        val totalSkor = sharedPref.getInt("total_score", 0)
        totalSkorView.text = totalSkor.toString()

        btnMulai.setOnClickListener {
            findNavController().navigate(R.id.PilihBahasa)
        }
    }

}