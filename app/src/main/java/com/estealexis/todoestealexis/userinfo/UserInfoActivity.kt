package com.estealexis.todoestealexis.userinfo

import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.lifecycle.lifecycleScope
import coil.load
import coil.transform.CircleCropTransformation
import com.estealexis.todoestealexis.BuildConfig
import com.estealexis.todoestealexis.databinding.UserInfoBinding
import com.estealexis.todoestealexis.network.UserInfo
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import java.net.URI


class UserInfoActivity: AppCompatActivity() {

    private lateinit var binding: UserInfoBinding;
    private fun openCamera() = takePicture.launch(photoUri)
    private fun pickPicture() = pickInGallery.launch("image/*")

    private val userViewModel: UserInfoViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = UserInfoBinding.inflate(layoutInflater);
        setContentView(binding.root)
        binding.takePictureButton.setOnClickListener {
            takePictures()
        }
        binding.uploadImageButton.setOnClickListener {
            pickInGallery()
        }
        userViewModel.user.observe(this, { userInfo ->
            binding.editFirstname.setText(userInfo.firstName)
            binding.editLastname.setText(userInfo.lastName)
            binding.editEmail.setText(userInfo.email)
            binding.imageView.load(userInfo.avatar)
        })

        binding.btnEditInfo.setOnClickListener {
            val firstName = binding.editFirstname.text.toString()
            val lastName = binding.editFirstname.text.toString()
            val email = binding.editEmail.text.toString()
            userViewModel.updateUser(
                UserInfo(
                    firstName = firstName,
                    lastName = lastName,
                    email = email
                )
            )
        }
    }

    override fun onResume() {
        super.onResume()
        userViewModel.loadInfo()
    }

    fun takePictures() {
        askCameraPermissionAndOpenCamera()
    }

    fun pickInGallery() {
        askGalleryPermissionAndPickInGallery()
    }

    private val photoUri by lazy {
        FileProvider.getUriForFile(
            this,
            BuildConfig.APPLICATION_ID +".fileprovider",
            File.createTempFile("avatar", ".jpeg", externalCacheDir)

        )
    }

    private val takePicture = registerForActivityResult(ActivityResultContracts.TakePicture()) { success ->
        if (success) handleImage(photoUri)
        else Toast.makeText(this, "Erreur ! 😢", Toast.LENGTH_LONG).show()
    }

    private val pickInGallery =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            if (uri != null)
                userViewModel.updateAvatar(convert(uri));
        }

    private fun convert(uri: Uri) =
        MultipartBody.Part.createFormData(
            name = "avatar",
            filename = "temp.jpeg",
            body = contentResolver.openInputStream(uri)!!.readBytes().toRequestBody()
        )

    private fun handleImage(toUri: Uri) {
        lifecycleScope.launch {
            binding.imageView.setImageURI(toUri)
            userViewModel.updateAvatar(convert(toUri))
        }
    }

    private val requestCameraPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) openCamera()
            else showCameraExplanationDialog()
        }

    private val requestGalleyPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) pickPicture()
            else showGalleryExplanationDialog()
        }

    private fun requestCameraPermission() =
        requestCameraPermissionLauncher.launch(Manifest.permission.CAMERA)

    private fun requestGalleryPermission() =
        requestGalleyPermissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)

    private fun askCameraPermissionAndOpenCamera() {
        when {
            ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                    == PackageManager.PERMISSION_GRANTED -> openCamera()
            shouldShowRequestPermissionRationale(Manifest.permission.CAMERA) -> showCameraExplanationDialog()
            else -> requestCameraPermission()
        }
    }

    private fun askGalleryPermissionAndPickInGallery() {
        when {
            ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED -> pickPicture()
            shouldShowRequestPermissionRationale(Manifest.permission.READ_EXTERNAL_STORAGE) -> showGalleryExplanationDialog()
            else -> requestGalleryPermission()
        }
    }

    private fun showGalleryExplanationDialog() {
        AlertDialog.Builder(this).apply {
            setMessage("On a besoin de la galerie sivouplé ! 🥺")
            setPositiveButton("Bon, ok") { _, _ ->
                requestGalleryPermission()
            }
            setCancelable(true)
            show()
        }
    }

    private fun showCameraExplanationDialog() {
        AlertDialog.Builder(this).apply {
            setMessage("On a besoin de la caméra sivouplé ! 🥺")
            setPositiveButton("Bon, ok") { _, _ ->
                requestCameraPermission()
            }
            setCancelable(true)
            show()
        }
    }
}


