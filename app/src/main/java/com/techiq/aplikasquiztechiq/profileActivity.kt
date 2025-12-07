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
import android.widget.ImageView
import android.widget.EditText
import android.net.Uri
import android.content.Context
import android.content.Intent
import android.app.Activity

class profileActivity : AppCompatActivity() {

    private lateinit var imgProfile: ImageView
    private lateinit var edtNama: EditText
    private lateinit var edtUmur: EditText
    private lateinit var edtGender: EditText
    private lateinit var btnContinue: Button
    private val PICK_IMAGE = 100
    private var imageUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.fragment_profil)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        imgProfile = findViewById(R.id.imgProfile)
        edtNama = findViewById(R.id.edtNama)
        edtUmur = findViewById(R.id.edtUmur)
        edtGender = findViewById(R.id.edtGender)

        loadProfile()

        // Klik foto untuk mengganti dari galeri
        imgProfile.setOnClickListener {
            val galleryIntent = Intent(Intent.ACTION_PICK)
            galleryIntent.type = "image/*"
            startActivityForResult(galleryIntent, PICK_IMAGE)
        }

        //  Klik tombol continue untuk menyimpan data
        btnContinue.setOnClickListener {
            saveProfile()
        }
    }
//untuk menyimpan profil
    private fun saveProfile() {
        val sharedPref = getSharedPreferences("user_profile", Context.MODE_PRIVATE)
        val editor = sharedPref.edit()

        editor.putString("nama", edtNama.text.toString())
        editor.putString("umur", edtUmur.text.toString())
        editor.putString("gender", edtGender.text.toString())

        // Simpan foto (URI sebagai string)
        editor.putString("fotoUri", imageUri?.toString())

        editor.apply()
    }

    // Fungsi Load Profil
    private fun loadProfile() {
        val sharedPref = getSharedPreferences("user_profile", Context.MODE_PRIVATE)

        edtNama.setText(sharedPref.getString("nama", ""))
        edtUmur.setText(sharedPref.getString("umur", ""))
        edtGender.setText(sharedPref.getString("gender", ""))

        val savedUri = sharedPref.getString("fotoUri", null)
        if (savedUri != null) {
            imgProfile.setImageURI(Uri.parse(savedUri))
            imageUri = Uri.parse(savedUri)
        }
    }

    // Mengambil Foto dari Galeri
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_IMAGE && resultCode == Activity.RESULT_OK) {
            imageUri = data?.data
            imgProfile.setImageURI(imageUri)
        }
    }
}
