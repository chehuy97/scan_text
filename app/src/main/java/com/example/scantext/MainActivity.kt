package com.example.scantext

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import com.google.gson.Gson

class MainActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var btnPickerImage:ImageButton
    private lateinit var tvResult:TextView
    private lateinit var imgUri:Uri
    val PICK_IMAGE = 100
    val STATIC_INTEGER_VALUE = 97
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btnPickerImage = findViewById(R.id.btn_img_picker)
        btnPickerImage.setOnClickListener(this)
        tvResult = findViewById(R.id.tv_result)
    }

    fun openGallery(){
        val gallery:Intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
        startActivityForResult(gallery, PICK_IMAGE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == PICK_IMAGE){
            imgUri = data?.data!!
            val intent = Intent(this, ImageActivity::class.java)
            val bundle = Bundle()
            bundle.putString("image_uri",imgUri.toString())
            intent.putExtras(bundle)
           // startActivity(intent)
            startActivityForResult(intent,STATIC_INTEGER_VALUE)
        } else if (requestCode == STATIC_INTEGER_VALUE && resultCode == Activity.RESULT_OK){
            val resultText = data?.getStringExtra("result_text")
            tvResult.text = resultText

        }
    }

    override fun onClick(view: View?) {
        when (view!!.id){
            R.id.btn_img_picker -> {
                openGallery()
            }
        }
    }
}