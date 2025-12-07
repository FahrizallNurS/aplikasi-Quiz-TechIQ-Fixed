package com.techiq.aplikasquiztechiq

import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val username = intent.getStringExtra(LoginActivity.KEY_USERNAME)

        //ambil navhost dari layout
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        //ambil controler dari host
        val navController = findNavController(R.id.nav_host_fragment)
        //nampilkan bottomNavigation
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        //menghubungkan bottom nav ke nav kontroler
        bottomNavigationView.setupWithNavController(navController)

        // Cek apakah datang dari QuizActivity
        val menuDipilih = intent.getStringExtra("menu")

        if (menuDipilih == "leaderboard") {
            bottomNavigationView.selectedItemId = R.id.leaderboardFragment
        }
    }
}