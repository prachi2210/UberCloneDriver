package com.wizebrain.adebdriver.ui.auth.document_verification

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.View
import android.widget.AdapterView
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.wizebrain.adebdriver.base.BaseActivity
import com.example.adebuser.data.api.RetrofitBuilder
import com.wizebrain.adebdriver.BuildConfig
import com.wizebrain.adebdriver.R
import com.wizebrain.adebdriver.base.ViewModelProviderFactory
import com.wizebrain.adebdriver.data.api.ApiHelper
import com.wizebrain.adebdriver.databinding.ActivityPersonalIdBinding
import com.wizebrain.adebdriver.extensions.loadImage
import com.wizebrain.adebdriver.ui.auth.AuthViewModel
import com.wizebrain.adebdriver.ui.auth.adapter.CustomSpinnerAdapter
import com.wizebrain.adebdriver.utils.Constants
import com.wizebrain.adebdriver.utils.Status
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class PersonalIdActivity : BaseActivity(), View.OnClickListener {
    private lateinit var binding: ActivityPersonalIdBinding
    private lateinit var viewModel: AuthViewModel


    var mYourPicFile: File? = null
    var mFrontSideFile: File? = null
    var mBackSideFile: File? = null


    val REQUEST_TAKE_PHOTO = 101
    val REQUEST_GALLERY_PHOTO = 201
    var counter = -1
    private var idProofList = arrayOf<String>()


    companion object {
        fun getStartIntent(context: Context): Intent {
            return Intent(context, PersonalIdActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPersonalIdBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupViewModel()

        /*2004-09-08*/

        idProofList = resources.getStringArray(R.array.id_proof)


        val spinnerIdProof = CustomSpinnerAdapter(
            this,  // Use our custom adapter
            R.layout.spinner_item, idProofList
        )
        spinnerIdProof.setDropDownViewResource(android.R.layout.simple_list_item_1)
        binding.spinnerIdProof.adapter = spinnerIdProof
        binding.spinnerIdProof.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    val selectedItemText = parent.getItemAtPosition(position) as String
                    // If user change the default selection
                    // First item is disable and it is used for hint
                    if (position > 0) {
                        // Notify the selected item text

                    }
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {}
            }


    }


    private fun setupViewModel() {
        viewModel = ViewModelProvider(
            this,
            ViewModelProviderFactory(ApiHelper(RetrofitBuilder.apiService))
        ).get(AuthViewModel::class.java)
    }


    private fun uploadPersonalId() {

        when {

            binding.spinnerIdProof.selectedItem.toString()
                .equals(getString(R.string.select)) -> {
                setError(getString(R.string.id_proof))
            }

            checkEmpty(binding.etProofIdNumber) -> {
                setError(getString(R.string.id_number_empty_error))
            }

            checkEmpty(binding.etDob) -> {
                setError(getString(R.string.dob_validation))
            }


            mYourPicFile == null -> {
                setError(getString(R.string.user_add_pic_validation))
            }


            mFrontSideFile == null -> {
                setError(getString(R.string.front_side_image_validation))
            }

            mBackSideFile == null -> {
                setError(getString(R.string.back_side_image_validation))
            }


            else -> {
                viewModel.uploadPersonalId(
                    userPreferences.getUserREf(),
                    binding.spinnerIdProof.selectedItem.toString().trim(),
                    binding.etProofIdNumber.text.toString().trim(),
                    binding.etDob.text.toString().trim(),
                    mYourPicFile,
                    mFrontSideFile,
                    mBackSideFile
                ).observe(this, Observer {
                    it?.let { resource ->
                        when (resource.status) {
                            Status.SUCCESS -> {
                                dismissDialog()
                                resource.data?.let { user ->
                                    if (user.body()?.status.equals("success")) {
                                        userPreferences.savePersonalID("personal")
                                        val returnIntent = Intent()
                                        returnIntent.putExtra(Constants.TYPE, "personal")
                                        setResult(RESULT_OK, returnIntent)
                                        finish()


                                    } else {
                                        setError(user.body()?.msg.toString())
                                    }
                                }
                            }
                            Status.ERROR -> {
                                dismissDialog()
                                setError(it.message.toString())

                            }
                            Status.LOADING -> {
                                showDialog()
                            }
                        }
                    }
                })
            }
        }


    }

    override fun onClick(v: View?) {


        when (v) {
            binding.userPic -> {
                counter = 0
                checkExternalPermission()
            }

            binding.ivFrontUpload -> {
                counter = 1
                checkExternalPermission()
            }
            binding.ivBackUpload -> {
                counter = 2
                checkExternalPermission()
            }

            binding.tvSave -> {
                uploadPersonalId()
            }


        }
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            showPictureDialog()

        }
    }

    private fun showPictureDialog() {

        val pictureDialog: AlertDialog.Builder = AlertDialog.Builder(this)
        val title = pictureDialog.setTitle(getString(R.string.select_action))
        val pictureDialogItems = arrayOf(
            getString(R.string.select_image_from_gallery),
            getString(R.string.select_image_from_camera)
        )
        pictureDialog.setItems(
            pictureDialogItems
        ) { _, which ->
            when (which) {
                0 -> dispatchGalleryIntent()
                1 -> dispatchTakePictureIntent()
            }
        }
        pictureDialog.show()
    }

    private fun dispatchGalleryIntent() {
        val pickPhoto = Intent(
            Intent.ACTION_PICK,
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        )
        pickPhoto.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        startActivityForResult(
            pickPhoto, REQUEST_GALLERY_PHOTO
        )
    }


    private fun dispatchTakePictureIntent() {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (takePictureIntent.resolveActivity(packageManager) != null) {
            // Create the File where the photo should go
            var photoFile: File? = null
            try {
                photoFile = createImageFile()
            } catch (ex: IOException) {
                ex.printStackTrace()
                // Error occurred while creating the File
            }
            if (photoFile != null) {
                val photoURI = FileProvider.getUriForFile(
                    this, "${BuildConfig.APPLICATION_ID}.provider",
                    photoFile
                )

                if (counter == 0)
                    mYourPicFile = photoFile
                else if (counter == 1)
                    mFrontSideFile = photoFile
                else if (counter == 2)
                    mBackSideFile = photoFile

                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                startActivityForResult(
                    takePictureIntent,
                    REQUEST_TAKE_PHOTO
                )
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_TAKE_PHOTO) {
            /*  binding.icCamera.hide()
              binding.ivProfile.loadImage(mPhotoFile)*/

            when (counter) {
                0 -> {
                    binding.userPic.loadImage(mYourPicFile)
                }
                1 -> {
                    binding.ivFrontUpload.loadImage(mFrontSideFile)
                }
                2 -> {
                    binding.ivBackUpload.loadImage(mBackSideFile)
                }
            }


        } else if (requestCode == REQUEST_GALLERY_PHOTO) {
            val selectedImage = data!!.data
            try {


                when (counter) {

                    0 -> {
                        mYourPicFile = File(getRealPathFromUri(selectedImage)!!)
                        binding.userPic.loadImage(mYourPicFile)
                    }

                    1 -> {
                        mFrontSideFile = File(getRealPathFromUri(selectedImage)!!)
                        binding.ivFrontUpload.loadImage(mFrontSideFile)
                    }

                    2 -> {
                        mBackSideFile = File(getRealPathFromUri(selectedImage)!!)
                        binding.ivBackUpload.loadImage(mBackSideFile)
                    }
                }


            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }


    private fun getRealPathFromUri(contentUri: Uri?): String? {
        var cursor: Cursor? = null
        return try {
            val proj =
                arrayOf(MediaStore.Images.Media.DATA)
            cursor = contentResolver.query(contentUri!!, proj, null, null, null)
            if (BuildConfig.DEBUG && cursor == null) {
                error("Assertion failed")
            }
            val column_index = cursor!!.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
            cursor.moveToFirst()
            cursor.getString(column_index)
        } finally {
            cursor?.close()
        }
    }


    private fun createImageFile(): File? {
        // Create an image file name
        val timeStamp = SimpleDateFormat("yyyyMMddHHmmss").format(Date())
        val mFileName = "JPEG_" + timeStamp + "_"
        val storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(mFileName, ".jpg", storageDir)
    }


    private fun checkExternalPermission() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.CAMERA
            ) != PackageManager.PERMISSION_GRANTED
        ) {

            ActivityCompat.requestPermissions(
                this,
                arrayOf(
                    Manifest.permission.CAMERA,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_EXTERNAL_STORAGE
                ),
                0
            )


        } else {
            showPictureDialog()
        }

    }


}