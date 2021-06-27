package com.rathoreapps.mpchartexample

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_camera_images.*
import java.io.ByteArrayOutputStream


class CameraImages : AppCompatActivity() {

    val CAMERA_REQUEST_CODE = 1000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_camera_images)
        open_camera_button.setOnClickListener { openDefaultCamera() }

    }

    fun openDefaultCamera() {
        if (hasCameraPermission()) {
            openDefaultCameraAction()
        } else {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE),
                CAMERA_REQUEST_CODE
            )
        }

    }

    private fun openDefaultCameraAction() {
        startActivityForResult(Intent(MediaStore.ACTION_IMAGE_CAPTURE), CAMERA_REQUEST_CODE)
    }

    fun hasCameraPermission(): Boolean {
        return ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.CAMERA
        ) == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK) {
            val extras: Bundle? = data?.extras
            val mImageBitmap = extras?.get("data") as Bitmap?

            capturedImage.setImageBitmap(mImageBitmap)

            mImageBitmap?.let {
                getImageUri(this, it).let { imageUri ->
                    capturedImage.setImageURI(imageUri)
                }
            }
        }
    }


    private fun getImageUri(context: Context, inImage: Bitmap): Uri? {
        val bytes = ByteArrayOutputStream()
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
        val path = MediaStore.Images.Media.insertImage(
            context.contentResolver,
            inImage,
            System.currentTimeMillis().toString(),
            "Camera Images"
        )
        return Uri.parse(path)
    }
}