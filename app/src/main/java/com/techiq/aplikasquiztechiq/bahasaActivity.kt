package com.techiq.aplikasquiztechiq

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class bahasaActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_bahasa)

        
        val cardPython: CardView = findViewById(R.id.CardViewPython)
        val cardKotlin: CardView = findViewById(R.id.CardViewKotlin)
        val cardPhp: CardView = findViewById(R.id.CardViewPhp)

        cardPython.setOnClickListener{
            Toast.makeText(this, "python dipiliih", Toast.LENGTH_SHORT).show()
        }
        cardKotlin.setOnClickListener{
            Toast.makeText(this, "kotlin dipilih", Toast.LENGTH_SHORT).show()
        }
        cardPhp.setOnClickListener{
            Toast.makeText(this, "php dipilih", Toast.LENGTH_SHORT).show()
        }


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}