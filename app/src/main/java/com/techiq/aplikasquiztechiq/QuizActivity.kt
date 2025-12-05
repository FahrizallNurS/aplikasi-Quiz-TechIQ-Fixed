package com.techiq.aplikasquiztechiq

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class QuizActivity : AppCompatActivity() {

    // VIEW
    private lateinit var txtNomorSoal: TextView
    private lateinit var txtPertanyaan: TextView

    private lateinit var opsi1: LinearLayout
    private lateinit var opsi2: LinearLayout
    private lateinit var opsi3: LinearLayout
    private lateinit var opsi4: LinearLayout

    private lateinit var txtOpsi1: TextView
    private lateinit var txtOpsi2: TextView
    private lateinit var txtOpsi3: TextView
    private lateinit var txtOpsi4: TextView

    private lateinit var btnNext: AppCompatButton
    private lateinit var btnBack: AppCompatButton
    private lateinit var btnSelesai: AppCompatButton

    // LOGIC
    private var indexSoal = 0
    private lateinit var listSoal: List<Soal>
    private val jawabanPengguna = mutableListOf<Int?>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz)

        // Insets
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        initViews()

        // Ambil bahasa dari Intent — (WAJIB sama dengan yang dikirim Fragment)
        val bahasaDipilih = intent.getStringExtra("bahasa") ?: ""

        // Ambil soal sesuai bahasa
        listSoal = getSoalByBahasa(bahasaDipilih)

        jawabanPengguna.addAll(List(listSoal.size) { null })

        tampilkanSoal()

        // Klik opsi
        opsi1.setOnClickListener { pilihJawaban(0) }
        opsi2.setOnClickListener { pilihJawaban(1) }
        opsi3.setOnClickListener { pilihJawaban(2) }
        opsi4.setOnClickListener { pilihJawaban(3) }

        // Next Soal
        btnNext.setOnClickListener {
            if (indexSoal < listSoal.size - 1) {
                indexSoal++
                tampilkanSoal()
            }
        }

        // Back Soal
        btnBack.setOnClickListener {
            if (indexSoal > 0) {
                indexSoal--
                tampilkanSoal()
            }
        }

        btnSelesai.setOnClickListener {
            val skorAkhir = hitungSkor()

            val prefs = getSharedPreferences("quiz_prefs", MODE_PRIVATE)
            val skorLama = prefs.getInt("total_score", 0)
            val skorBaru = skorLama + skorAkhir

            prefs.edit().putInt("total_score", skorBaru).apply()


            // Arahkan ke MainActivity supaya auto buka halaman Leaderboard
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("menu", "leaderboard")
            startActivity(intent)

            // pindah ke LeaderboardActivity atau Fragment
            finish() // biar quiz ditutup
        }

    }

    private fun initViews() {
        txtNomorSoal = findViewById(R.id.txtNomorSoal)
        txtPertanyaan = findViewById(R.id.txtPertanyaan)

        opsi1 = findViewById(R.id.opsi1)
        opsi2 = findViewById(R.id.opsi2)
        opsi3 = findViewById(R.id.opsi3)
        opsi4 = findViewById(R.id.opsi4)

        txtOpsi1 = findViewById(R.id.txtOpsi1)
        txtOpsi2 = findViewById(R.id.txtOpsi2)
        txtOpsi3 = findViewById(R.id.txtOpsi3)
        txtOpsi4 = findViewById(R.id.txtOpsi4)

        btnNext = findViewById(R.id.btnNext)
        btnBack = findViewById(R.id.btnBack)
        btnSelesai = findViewById(R.id.btnSelesai)
    }

    private fun pilihJawaban(jawaban: Int) {
        jawabanPengguna[indexSoal] = jawaban
        highlightJawaban(jawaban)

        if (indexSoal == listSoal.size - 1) {
            btnSelesai.visibility = View.VISIBLE
        }
    }

    private fun highlightJawaban(jawabanIndex: Int) {
        opsi1.setBackgroundResource(R.drawable.background_opsi)
        opsi2.setBackgroundResource(R.drawable.background_opsi)
        opsi3.setBackgroundResource(R.drawable.background_opsi)
        opsi4.setBackgroundResource(R.drawable.background_opsi)

        when (jawabanIndex) {
            0 -> opsi1.setBackgroundResource(R.drawable.rounded_background)
            1 -> opsi2.setBackgroundResource(R.drawable.rounded_background)
            2 -> opsi3.setBackgroundResource(R.drawable.rounded_background)
            3 -> opsi4.setBackgroundResource(R.drawable.rounded_background)
        }
    }

    private fun resetBackground() {
        opsi1.setBackgroundResource(R.drawable.background_opsi)
        opsi2.setBackgroundResource(R.drawable.background_opsi)
        opsi3.setBackgroundResource(R.drawable.background_opsi)
        opsi4.setBackgroundResource(R.drawable.background_opsi)
    }

    private fun tampilkanSoal() {

        // Reset warna tombol dulu sebelum tampilkan soal baru
        resetBackground()

        val soal = listSoal[indexSoal]

        txtNomorSoal.text = "${indexSoal + 1}/${listSoal.size}"
        txtPertanyaan.text = soal.pertanyaan

        txtOpsi1.text = soal.opsi[0]
        txtOpsi2.text = soal.opsi[1]
        txtOpsi3.text = soal.opsi[2]
        txtOpsi4.text = soal.opsi[3]

        // Highlight jawaban sebelumnya
        jawabanPengguna[indexSoal]?.let {
            highlightJawaban(it)
        }

        // === Atur Visibilitas Next & Selesai ===
        if (indexSoal == listSoal.size - 1) {
            btnNext.visibility = View.GONE    // sembunyikan next
            btnSelesai.visibility = View.VISIBLE
        } else {
            btnNext.visibility = View.VISIBLE
            btnSelesai.visibility = View.GONE
        }
    }

    private fun getSoalByBahasa(bahasa: String): List<Soal> {
        return when (bahasa) {

            "python" -> listOf(
                Soal("Apa ekstensi file Python?", listOf(".py", ".java", ".pyt", ".ph"), 0),
                Soal("Perintah untuk mencetak teks?", listOf("print()", "echo()", "cout", "System.out"), 0),
                Soal("Tipe data list di Python?", listOf("[]", "{}", "<>", "()"), 0),
                Soal("Siapa pencipta Python?", listOf("Guido van Rossum", "James Gosling", "Linus Torvalds", "Rasmus"), 0),
                Soal("Operator pembanding sama dengan?", listOf("==", "=", "===", ":="), 0)
            )

            "kotlin" -> listOf(
                Soal("Ekstensi file Kotlin?", listOf(".kt", ".kotlin", ".kot", ".android"), 0),
                Soal("Keyword untuk membuat fungsi?", listOf("fun", "function", "def", "proc"), 0),
                Soal("Framework Android resmi?", listOf("Android Studio", "Flutter", "React", "XCode"), 0),
                Soal("Tipe data tidak null?", listOf("String", "String?", "var", "null"), 0),
                Soal("Kotlin dibuat oleh?", listOf("JetBrains", "Google", "Oracle", "Microsoft"), 0)
            )

            "php" -> listOf(
                Soal("Ekstensi file PHP?", listOf(".php", ".ph", ".pht", ".pp"), 0),
                Soal("Cara mencetak teks?", listOf("echo", "cout", "print()", "println"), 0),
                Soal("Tanda membuka kode PHP?", listOf("<?php", "<?=", "<php>", "<!--"), 0),
                Soal("PHP adalah bahasa?", listOf("Server-side", "Client-side", "Database", "Markup"), 0),
                Soal("Pembuat PHP?", listOf("Rasmus Lerdorf", "Guido", "Gosling", "Brendan"), 0)
            )

            else -> emptyList()
        }
    }

    private fun hitungSkor(): Int {
        var skor = 0
        for (i in listSoal.indices) {
            if (jawabanPengguna[i] == listSoal[i].jawabanBenar) {
                skor += 20  // misal tiap soal benar dapat 20 poin
            }
        }
        return skor
    }


}

data class Soal(
    val pertanyaan: String,
    val opsi: List<String>,
    val jawabanBenar: Int
)
