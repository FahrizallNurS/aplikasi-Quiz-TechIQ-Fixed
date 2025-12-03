package com.techiq.aplikasquiztechiq

import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.view.View
import androidx.appcompat.widget.AppCompatButton

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

        // Insets bawaan dari kode kamu
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        initViews()

        // Ambil bahasa dari Intent
        val bahasaDipilih = intent.getStringExtra("bahasa") ?: "Kotlin"

        // Ambil list soal sesuai bahasa
        listSoal = getSoalByBahasa(bahasaDipilih)

        jawabanPengguna.addAll(List(listSoal.size) { null })

        tampilkanSoal()

        // EVENT CLICK OPSI
        opsi1.setOnClickListener { pilihJawaban(0) }
        opsi2.setOnClickListener { pilihJawaban(1) }
        opsi3.setOnClickListener { pilihJawaban(2) }
        opsi4.setOnClickListener { pilihJawaban(3) }

        btnNext.setOnClickListener {
            if (indexSoal < listSoal.size - 1) {
                indexSoal++
                tampilkanSoal()
            }
        }

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

        txtOpsi1.text = soal.opsi1
        txtOpsi2.text = soal.opsi2
        txtOpsi3.text = soal.opsi3
        txtOpsi4.text = soal.opsi4

        jawabanPengguna[indexSoal]?.let {
            highlightJawaban(it)
        }
    }

    private fun getSoalByBahasa(bahasa: String): List<Soal> {
        return when (bahasa) {

            "Kotlin" -> listOf(
                Soal("Ekstensi file Kotlin?", ".kt", ".kot", ".kl", ".view", 0),
                Soal("Sintaks fungsi?", "fun", "def", "function", "fn", 0),
                Soal("Tipe data teks?", "text", "str", "String", "varchar", 2),
                Soal("Cara membuat variabel?", "var", "let", "make", "new", 0),
                Soal("File layout default Android menggunakan format?", ".view", ".xml", ".html", ".layout", 1)
            )

            "PHP" -> listOf(
                Soal("Ekstensi file PHP?", ".php", ".ph", ".php3", ".pp", 0),
                Soal("Output teks di PHP?", "write()", "print()", "echo", "show()", 2),
                Soal("Operator variabel PHP?", "@", "$", "%", "&", 1),
                Soal("Array di PHP?", "[]", "{}", "array()", "<>", 2),
                Soal("Koneksi database?", "mysqli_connect()", "db()", "connect()", "sql()", 0)
            )

            "Python" -> listOf(
                Soal("Ekstensi file Python?", ".py", ".pyt", ".pyc", ".pt", 0),
                Soal("Output di Python?", "say()", "out()", "print()", "echo()", 2),
                Soal("List Python ditandai dengan?", "[]", "()", "{}", "<>", 0),
                Soal("Komentar satu baris?", "//", "#", "--", "/* */", 1),
                Soal("Loop Python?", "for", "repeat", "foreach", "loop", 0)
            )

            else -> emptyList()
        }
    }
}

data class Soal(
    val pertanyaan: String,
    val opsi1: String,
    val opsi2: String,
    val opsi3: String,
    val opsi4: String,
    val jawabanBenar: Int
)