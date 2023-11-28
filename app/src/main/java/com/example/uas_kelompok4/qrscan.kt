package com.example.uas_kelompok4

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.zxing.integration.android.IntentIntegrator

/*
*  dari kemaren masalah di zxing
*  xml udah aku buat, tapi backend masih gagal selesai karena zxing
*  ini aku udah ikutin tutorial zxing kotlin tapi masih error
*
* */


class qrscan : AppCompatActivity() {

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            if (isGranted) {
                showCamera()
            } else {
                Toast.makeText(this, "Camera permission denied", Toast.LENGTH_SHORT).show()
            }
        }

    private fun showCamera() {
        IntentIntegrator(this)
            .setOrientationLocked(false)
            .setPrompt("Scan QR code")
            .setCameraId(0)
            .setBeepEnabled(false)
            .initiateScan()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_qrscan)
        initViews()

    }

    private fun initViews() {
        findViewById<Button>(R.id.qrbutton).setOnClickListener {
            checkPermissionCamera(this)
        }
    }

    private fun checkPermissionCamera(context: Context) {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            showCamera()
        } else if (shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)) {
            Toast.makeText(context, "CAMERA PERMISSION REQUIRED", Toast.LENGTH_SHORT).show()
        } else {
            requestPermissionLauncher.launch(Manifest.permission.CAMERA)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        if (result != null) {
            if (result.contents == null) {

                Toast.makeText(this, "Scan cancelled", Toast.LENGTH_SHORT).show()
            } else {

                setResult(result.contents)
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    private fun setResult(result: String) {
       // link to next action after scan
        Toast.makeText(this, "Scanned result: $result", Toast.LENGTH_SHORT).show()
    }
}


