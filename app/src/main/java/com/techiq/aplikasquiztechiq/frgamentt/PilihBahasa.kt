package com.techiq.aplikasquiztechiq.frgamentt

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import com.techiq.aplikasquiztechiq.QuizActivity
import com.techiq.aplikasquiztechiq.R

class PilihBahasa : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_pilih_bahasa, container, false)

        val cardPython = view.findViewById<CardView>(R.id.CardViewPython)
        val cardKotlin = view.findViewById<CardView>(R.id.CardViewKotlin)
        val cardPhp = view.findViewById<CardView>(R.id.CardViewPhp)

        cardPython.setOnClickListener { bukaQuiz("python") }
        cardKotlin.setOnClickListener { bukaQuiz("kotlin") }
        cardPhp.setOnClickListener { bukaQuiz("php") }

        return view
    }

    private fun bukaQuiz(materi: String) {
        val intent = Intent(requireContext(), QuizActivity::class.java)
        intent.putExtra("materi", materi)
        startActivity(intent)
    }
}