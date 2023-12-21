package com.example.uas_kelompok4

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.uas_kelompok4.model.User
import com.google.zxing.integration.android.IntentIntegrator
import com.journeyapps.barcodescanner.ScanContract
import com.journeyapps.barcodescanner.ScanIntentResult
import com.journeyapps.barcodescanner.ScanOptions

class QrScan : AppCompatActivity() {

    private var currUser: User? = null

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            if (isGranted) {
                showCamera()
            } else {
                Toast.makeText(this, "Camera permission denied", Toast.LENGTH_SHORT).show()
            }
        }

    private fun showCamera() {
        val options = ScanOptions()
        options.setDesiredBarcodeFormats(ScanOptions.QR_CODE)
        options.setPrompt("Scan QR code")
        options.setCameraId(0)
        options.setBeepEnabled(false)
        options.setBarcodeImageEnabled(true)
        options.setOrientationLocked(false)

        scanLauncher.launch(options)
    }

    private val scanLauncher =
        registerForActivityResult((ScanContract())){result: ScanIntentResult ->
            run{
                if(result.contents == null) {
                    Toast.makeText(this, "Cancelled", Toast.LENGTH_SHORT).show()
                } else {
                    setResult(result.contents)
                }
            }
        }

    private fun setResult(result: String) {
        if (result == "https://me-qr.com/riYpdt1j") {
            val intent = Intent(this, OrderActivity::class.java)
            intent.putExtra("USR2", currUser)
            startActivity(intent)
            // Finish the current activity to prevent going back to the QR scan after navigating to OrderActivity
            finish()
        } else {
            // Handle other scanned results if needed
            Toast.makeText(this, "Scanned result: $result", Toast.LENGTH_SHORT).show()
        }
    }

    @Suppress("DEPRECATION")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_qrscan)
        val extras: Bundle? = intent.extras
        if(extras != null) {
            currUser = extras.getParcelable("USR1")
        }
        currUser?.id?.let { Log.d("currUser", it) }
        initViews()
    }

    private fun initViews() {
        findViewById<ImageButton>(R.id.qrbutton).setOnClickListener {
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
}
