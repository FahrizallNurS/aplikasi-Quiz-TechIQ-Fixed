package com.techiq.aplikasquiztechiq

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment

class LevelFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.activity_level, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val btnLevel1 = view.findViewById<LinearLayout>(R.id.btnLevel1)
        val btnLevel2 = view.findViewById<LinearLayout>(R.id.btnLevel2)
        val btnLevel3 = view.findViewById<LinearLayout>(R.id.btnLevel3)

        btnLevel1.setOnClickListener {
        }

        btnLevel2.setOnClickListener {
        }

        btnLevel3.setOnClickListener {
        }
    }
}
