package com.example.scantext

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition
import java.io.IOException

class ImageActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var imgUri: Uri
    private lateinit var selectedImage: ImageView
    private lateinit var scanBtn: Button
    private lateinit var image: InputImage
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image)
        scanBtn = findViewById(R.id.btn_scan)
        scanBtn.setOnClickListener {
            val recognizer = TextRecognition.getClient()
            var resultText = ""
            val result = recognizer.process(image)
                .addOnSuccessListener {
                    Log.d("TestScanResult", "Content is: " + it)
                    resultText = it.text
                    finishImageActivity(resultText)
                }
                .addOnFailureListener {
                    it.printStackTrace()
                    resultText = "Can find any text in the picture."
                    finishImageActivity(resultText)
                }
        }
        selectedImage = findViewById(R.id.selected_image)
        setSelectedImage()
    }

    private fun finishImageActivity(resultText:String){
        val resultIntent = Intent()
        resultIntent.putExtra("result_text", resultText)
        setResult(Activity.RESULT_OK, resultIntent)
        finish()
    }

    private fun setSelectedImage() {
        val bundle = intent.extras
        if (bundle != null) {
            val uriString = bundle.getString("image_uri")
            imgUri = Uri.parse(uriString)
            selectedImage.setImageURI(imgUri)
            try {
                image = InputImage.fromFilePath(this, imgUri)
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    override fun onClick(p0: View?) {
        TODO("Not yet implemented")
    }
}