package com.techiq.aplikasquiztechiq.frgamentt

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.techiq.aplikasquiztechiq.R

class ProfilFragment : Fragment() {

    private lateinit var imgProfile: ImageView
    private lateinit var edtNama: EditText
    private lateinit var edtUmur: EditText
    private lateinit var edtGender: EditText
    private lateinit var btnEdit: Button
    private lateinit var btnSimpan: Button

    private val PICK_IMAGE = 100
    private var imageUri: Uri? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_profil, container, false)

        // Bind View
        imgProfile = view.findViewById(R.id.imgProfile)
        edtNama = view.findViewById(R.id.edtNama)
        edtUmur = view.findViewById(R.id.edtUmur)
        edtGender = view.findViewById(R.id.edtGender)
        btnEdit = view.findViewById(R.id.btnEdit)
        btnSimpan = view.findViewById(R.id.btnSimpan)

        // Input disabled di awal
        setEditable(false)

        // Load data
        loadProfile()

        // Pilih foto
        imgProfile.setOnClickListener {
            val pick = Intent(Intent.ACTION_PICK)
            pick.type = "image/*"
            startActivityForResult(pick, PICK_IMAGE)
        }

        // Tombol edit
        btnEdit.setOnClickListener {
            setEditable(true)
        }

        // Tombol simpan
        btnSimpan.setOnClickListener {
            saveProfile()
            setEditable(false)
        }
        return view
    }

    private fun setEditable(enable: Boolean) {
        edtNama.isEnabled = enable
        edtUmur.isEnabled = enable
        edtGender.isEnabled = enable
    }

    private fun saveProfile() {
        val shared = requireContext().getSharedPreferences("user_profile", Context.MODE_PRIVATE)
        val ed = shared.edit()

        ed.putString("nama", edtNama.text.toString())
        ed.putString("umur", edtUmur.text.toString())
        ed.putString("gender", edtGender.text.toString())
        ed.putString("fotoUri", imageUri?.toString())
        ed.apply()
    }

    private fun loadProfile() {
        val shared = requireContext().getSharedPreferences("user_profile", Context.MODE_PRIVATE)

        edtNama.setText(shared.getString("nama", ""))
        edtUmur.setText(shared.getString("umur", ""))
        edtGender.setText(shared.getString("gender", ""))

        val savedUri = shared.getString("fotoUri", null)
        if (savedUri != null) {
            imageUri = Uri.parse(savedUri)
            imgProfile.setImageURI(imageUri)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_IMAGE && resultCode == Activity.RESULT_OK) {
            imageUri = data?.data
            imgProfile.setImageURI(imageUri)
        }
    }
}
