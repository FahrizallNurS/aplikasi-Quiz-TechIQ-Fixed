package com.techiq.aplikasquiztechiq

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

    private fun tampilkanSoal() {
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
    }

    private fun getSoalByBahasa(bahasa: String): List<Soal> {
        return when (bahasa) {

            "python" -> listOf(
                Soal("Apa ekstensi file Python?", listOf(".java", ".py", ".pyt", ".ph"), 1),
                Soal("Perintah untuk mencetak teks?", listOf("cout", "echo()", "print()", "System.out"), 3),
                Soal("Tipe data list di Python?", listOf("[]", "{}", "<>", "()"), 0),
                Soal("Siapa pencipta Python?", listOf("Rasmus", "James Gosling", "Linus Torvalds", "Guido van Rossum"), 3),
                Soal("Operator pembanding sama dengan?", listOf(":=", "=", "===", "=="), 3)
            )

            "kotlin" -> listOf(
                Soal("Ekstensi file Kotlin?", listOf(".kot", ".kotlin", ".kt", ".android"), 2),
                Soal("Keyword untuk membuat fungsi?", listOf("def", "function", "fun", "proc"), 2),
                Soal("Framework Android resmi?", listOf("Xcode", "Flutter", "React", "Android Studio"), 3),
                Soal("Tipe data tidak null?", listOf("null", "String?", "var", "val"), 1),
                Soal("Kotlin dibuat oleh?", listOf("Google", "JetBrains", "Oracle", "Microsoft"), 1)
            )

            "php" -> listOf(
                Soal("Ekstensi file PHP?", listOf(".php", ".pp", ".pht", ".ph"), 0),
                Soal("Cara mencetak teks?", listOf("cout", "echo", "print()", "println"), 1),
                Soal("Tanda membuka kode PHP?", listOf("<php>", "<?=", "<?php", "<!--"), 2),
                Soal("PHP adalah bahasa?", listOf("Database", "Client-side", "Server-side", "Markup"), 2),
                Soal("Pembuat PHP?", listOf("Brendan", "Guido", "Gosling", "Rasmus Lerdorf"), 3)
            )

            else -> emptyList()
        }
    }
}

data class Soal(
    val pertanyaan: String,
    val opsi: List<String>,
    val jawabanBenar: Int
)
