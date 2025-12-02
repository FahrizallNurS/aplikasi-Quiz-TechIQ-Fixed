package com.techiq.aplikasquiztechiq

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlin.collections.get
import kotlin.compareTo
import kotlin.dec
import kotlin.inc
import kotlin.text.compareTo
import kotlin.text.get

class quizActivity : AppCompatActivity() {

    private val listSoal = listOf(
        Soal(
            "Ekstensi file mana yang digunakan untuk menyimpan file Kotlin?",
            listOf(".java", ".kot", ".kt", ".android"),
            2
        ),
        Soal(
            "Siapakah pencipta bahasa pemrograman Kotlin?",
            listOf("Microsoft", "JetBrains", "Meta", "Oracle"),
            1
        ),
        Soal(
            "Tipe data untuk angka tanpa desimal dalam Kotlin?",
            listOf("Double", "Float", "Int", "String"),
            2
        ),
        Soal(
            "Fungsi println() digunakan untuk?",
            listOf("Membaca input", "Menampilkan output", "Menghapus file", "Melakukan looping"),
            1
        ),
        Soal(
            "File layout default Android menggunakan format?",
            listOf(".view", ".xml", ".html", ".layout"),
            1
        )
    )

    private var nomorSoal = 0
    private var jawabanDipilih = -1


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_quiz)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        tampilkanSoal()
    }

    private fun tampilkanSoal() {

        val txtNomor = findViewById<TextView>(R.id.txtNomorSoal)
        val txtPertanyaan = findViewById<TextView>(R.id.txtPertanyaan)

        val opsi1 = findViewById<LinearLayout>(R.id.opsi1)
        val opsi2 = findViewById<LinearLayout>(R.id.opsi2)
        val opsi3 = findViewById<LinearLayout>(R.id.opsi3)
        val opsi4 = findViewById<LinearLayout>(R.id.opsi4)

        val daftarOpsi = listOf(opsi1, opsi2, opsi3, opsi4)
        val btnNext = findViewById<Button>(R.id.btnNext)
        val btnBack =  findViewById<Button>(R.id.btnBack)
        val btnSelesai = findViewById<Button>(R.id.btnSelesai)

        val daftarText = listOf(
            findViewById<TextView>(R.id.txtOpsi1),
            findViewById<TextView>(R.id.txtOpsi2),
            findViewById<TextView>(R.id.txtOpsi3),
            findViewById<TextView>(R.id.txtOpsi4)
        )

        val soal = listSoal[nomorSoal]

        // Set text soal
        txtNomor.text = "${nomorSoal + 1}/${listSoal.size}"
        txtPertanyaan.text = soal.pertanyaan

        // Atur tombol Next & Selesai
        if (nomorSoal == listSoal.size - 1) {
            btnNext.visibility = View.GONE
            btnSelesai.visibility = View.VISIBLE
        } else {
            btnNext.visibility = View.VISIBLE
            btnSelesai.visibility = View.GONE
        }


        // Reset tampilan opsi
        resetWarnaOpsi()

        // Set teks ke setiap opsi
        daftarText.forEachIndexed { index, textView ->
            textView.text = soal.opsi[index]
        }

        // Listener klik opsi
        daftarOpsi.forEachIndexed { index, linearLayout ->
            linearLayout.setOnClickListener {
                jawabanDipilih = index
                resetWarnaOpsi()
                linearLayout.setBackgroundResource(R.drawable.background_opsi)
            }
        }

        // Tombol next
        btnNext.setOnClickListener {
            if (nomorSoal < listSoal.size - 1) {
                nomorSoal++
                jawabanDipilih = -1
                tampilkanSoal()
            }
        }
        //tombol back
        btnBack.setOnClickListener {
            if (nomorSoal > 0){
                nomorSoal--
                jawabanDipilih = -1
                tampilkanSoal()
            }
        }
        btnSelesai.setOnClickListener {

        }
    }

    private fun resetWarnaOpsi() {
        val daftarOpsi = listOf(
            findViewById<LinearLayout>(R.id.opsi1),
            findViewById<LinearLayout>(R.id.opsi2),
            findViewById<LinearLayout>(R.id.opsi3),
            findViewById<LinearLayout>(R.id.opsi4)
        )

        daftarOpsi.forEach {
            it.setBackgroundResource(R.drawable.background_opsi)
        }
    }
}

// Model data soal
data class Soal(
    val pertanyaan: String,
    val opsi: List<String>,
    val jawabanBenar: Int
)