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
import android.widget.Toast
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
import com.wizebrain.adebdriver.databinding.ActivityHealthReportBinding
import com.wizebrain.adebdriver.extensions.loadImage
import com.wizebrain.adebdriver.ui.auth.AuthViewModel
import com.wizebrain.adebdriver.ui.auth.adapter.CustomSpinnerAdapter
import com.wizebrain.adebdriver.utils.Constants
import com.wizebrain.adebdriver.utils.Status
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class HealthReportActivity : BaseActivity(), View.OnClickListener {
    private lateinit var binding: ActivityHealthReportBinding
    private lateinit var viewModel: AuthViewModel
    private lateinit var bloodGroup: String
    private lateinit var surgery: String
    var mPhotoFile: File? = null
    val REQUEST_TAKE_PHOTO = 101
    val REQUEST_GALLERY_PHOTO = 201

    private var bloodGroupList = arrayOf<String>()

    private var anySurgeryList = arrayOf<String>()


    companion object {
        fun getStartIntent(context: Context): Intent {
            return Intent(context, HealthReportActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHealthReportBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupViewModel()

        bloodGroupList = resources.getStringArray(R.array.blood_group)
        val spinnerAdapter1 = CustomSpinnerAdapter(
            this,  // Use our custom adapter
            R.layout.spinner_item, bloodGroupList
        )
        anySurgeryList = resources.getStringArray(R.array.any_surgery)
        val spinnerAdapter = CustomSpinnerAdapter(
            this,  // Use our custom adapter
            R.layout.spinner_item, anySurgeryList
        )






        binding.surgerySpinner.adapter = spinnerAdapter
        binding.surgerySpinner.setOnItemSelectedListener(object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(adapterView: AdapterView<*>?, view: View, i: Int, l: Long) {
                surgery = anySurgeryList[i]

            }

            override fun onNothingSelected(adapterView: AdapterView<*>?) {}
        })
        binding.bloodGpSpinner.adapter = spinnerAdapter1
        binding.bloodGpSpinner.setOnItemSelectedListener(object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(adapterView: AdapterView<*>?, view: View, i: Int, l: Long) {
                bloodGroup = bloodGroupList[i]

            }

            override fun onNothingSelected(adapterView: AdapterView<*>?) {}
        })
    }


    private fun setupViewModel() {
        viewModel = ViewModelProvider(
            this,
            ViewModelProviderFactory(ApiHelper(RetrofitBuilder.apiService))
        ).get(AuthViewModel::class.java)
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
                mPhotoFile = photoFile
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
//            binding.icCamera.hide()
            binding.medicalImage.loadImage(mPhotoFile)
        } else if (requestCode == REQUEST_GALLERY_PHOTO) {
            val selectedImage = data!!.data
            try {
                mPhotoFile = File(getRealPathFromUri(selectedImage)!!)
//                binding.icCamera.hide()
                binding.medicalImage.loadImage(mPhotoFile)


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

    private fun uploadHealthReport() {
        viewModel.uploadHealthReport(
            userPreferences.getUserREf().toString(),
            bloodGroup, surgery, mPhotoFile
        ).observe(this, Observer {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        dismissDialog()
                        resource.data?.let { user ->
                            if (user.body()?.status.equals("success")) {
                                userPreferences.saveHealthReport("health")
                                val returnIntent = Intent()
                                returnIntent.putExtra(Constants.TYPE, "health")
                                setResult(RESULT_OK, returnIntent)
                                finish()



                       /*         ActivityStarter.of(DocumentActivity.getStartIntent(this,"health"))
                                    .finishCurrentActivity()
                                    .startFrom(this)*/
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

    override fun onClick(v: View?) {
        when (v) {
            binding.linearMedicalImage -> {
                checkExternalPermission()
            }
            binding.uploadBtn ->
                if (bloodGroup == "Select")
                    Toast.makeText(this, "please select blood group", Toast.LENGTH_SHORT).show()
                else if (surgery == "Select")
                    Toast.makeText(this, "please select surgery", Toast.LENGTH_SHORT).show()
                else if (mPhotoFile == null)
                    showToast(this, getString(R.string.health_report_image_validation))
                else uploadHealthReport()
        }
    }
}


