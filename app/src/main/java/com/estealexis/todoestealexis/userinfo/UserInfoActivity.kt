package com.estealexis.todoestealexis.userinfo

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.launch
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import androidx.lifecycle.lifecycleScope
import com.estealexis.todoestealexis.R
import com.estealexis.todoestealexis.network.Api
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File


class UserInfoActivity: AppCompatActivity() {

    private fun openCamera() = takePicture.launch()
    val userInfo = Api.userService;

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            if (isGranted) openCamera()
            else showExplanationDialog()
        }

    private fun requestCameraPermission() =
        requestPermissionLauncher.launch(Manifest.permission.CAMERA)

    private fun askCameraPermissionAndOpenCamera() {
        when {
            ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                    == PackageManager.PERMISSION_GRANTED -> openCamera()
            shouldShowRequestPermissionRationale(Manifest.permission.CAMERA) -> showExplanationDialog()
            else -> requestCameraPermission()
        }
    }

    private fun showExplanationDialog() {
        AlertDialog.Builder(this).apply {
            setMessage("On a besoin de la caméra sivouplé ! 🥺")
            setPositiveButton("Bon, ok") { _, _ ->
                requestCameraPermission()
            }
            setCancelable(true)
            show()
        }
    }

    private fun handleImage(toUri: Uri) {

       lifecycleScope.launch {
            userInfo.updateAvatar(convert(toUri))
        }
    }

    private fun convert(uri: Uri) =
        MultipartBody.Part.createFormData(
            name = "avatar",
            filename = "temp.jpeg",
            body = contentResolver.openInputStream(uri)!!.readBytes().toRequestBody()
        )

    private val takePicture = registerForActivityResult(ActivityResultContracts.TakePicturePreview()) { bitmap ->
        val tmpFile = File.createTempFile("avatar", "jpeg")
        tmpFile.outputStream().use {
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, it)
        }
        handleImage(tmpFile.toUri())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.user_info)
        val takePicture =findViewById<Button>(R.id.take_picture_button)
        takePicture.setOnClickListener({
            askCameraPermissionAndOpenCamera()
        })


    }

}


