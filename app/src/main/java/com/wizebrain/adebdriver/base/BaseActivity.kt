package com.example.adebuser.base

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.text.TextUtils
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

import com.wizebrain.adebdriver.utils.PreferenceManager

import com.google.android.material.snackbar.Snackbar
import com.kaopiz.kprogresshud.KProgressHUD
import com.wizebrain.adebdriver.R

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

abstract class BaseActivity : AppCompatActivity() {
    lateinit var activity: Activity
    private val TAG = BaseActivity::class.java.simpleName
/*    private var kProgress: KProgressHUD? = null

    }*/

    val userPreferences: PreferenceManager by lazy {
        PreferenceManager(this)
    }
    fun getEpochTime(): Long {
        val millis = System.currentTimeMillis()
        val seconds = millis / 1000
        return seconds
    }

    fun addEpochTime(): Long {
        var time = getEpochTime() + 19800
        return time
    }

    fun getEpochDate(time: Long): String {
        var format = "yyyy-MM-dd hh:mm:ss";
        var sdf = SimpleDateFormat(format, Locale.getDefault());
        sdf.timeZone = TimeZone.getDefault();
        return sdf.format(Date(time * 1000));

    }


    fun convertDateFormat(time: String?, inputType: String, outputType: String): String {
        val inputPattern = inputType
        val outputPattern = outputType
        val inputFormat = SimpleDateFormat(inputPattern, Locale.ENGLISH)
        val outputFormat = SimpleDateFormat(outputPattern, Locale.ENGLISH)
        var date: Date? = null
        var str: String? = null
        try {
            date = inputFormat.parse(time)
            str = outputFormat.format(date)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return str.toString()
    }


    private val mDialog: KProgressHUD by lazy {
        KProgressHUD.create(this).apply {
            setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
            setCancellable(true)
            setAnimationSpeed(2)
            setDimAmount(0.5f)
        }
    }

    fun getDateFromTimeStamp(timeStamp: String): Date {
        var cal = Calendar.getInstance();
        var timezone = cal.timeZone;//get your local time zone.
        var sdf = SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        sdf.setTimeZone(timezone);//set time zone.
        var localTime = sdf.format(Date(timeStamp.toLong() * 1000));
        var date = Date();
        try {
            date = sdf.parse(localTime);//get local date
        } catch (e: ParseException) {
            e.printStackTrace();
        }
        return date;
    }


/*private val mDialog: Dialog by lazy {
    Dialog(this).apply {
        window?.requestFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.layout_progress_wheel)
        window?.setBackgroundDrawable(ColorDrawable(0))
    }

}*/


/*

fun setDefaultLatLng() {
    if (userPreferences.getLatitude().isEmpty() || userPreferences.getLongitude().isEmpty()) {
        userPreferences.saveCurrentLatitude("0")
        userPreferences.saveCurrentLongitude("0")
    }
}
*/


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity = this
        supportActionBar?.hide()


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            window.statusBarColor = ContextCompat.getColor(this, R.color.white)
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR

        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.statusBarColor = ContextCompat.getColor(this, R.color.white)
        }


    }


/*
private fun generateFcmToken() {
    FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
        if (!task.isSuccessful) {
            return@OnCompleteListener
        }
        // Get new FCM registration token
        val token = task.result
        userPreferences.saveDeviceToken(token)

    })
}
*/
/*open fun openFragment(fragment: Fragment, tag: String) {
    addSlidingFragment(
     supportFragmentManager,
        fragment,
        tag,
        R.id.flContainerSlide
    )
}*/

    open fun addSlidingFragment(
        fragmentManager: FragmentManager,
        fragment: Fragment,
        tag: String, @IdRes container: Int
    ) {
        fragmentManager.beginTransaction().setCustomAnimations(
            R.anim.slide_up_dialog, 0, 0,
            R.anim.slide_down_dialog
        ).add(container, fragment, tag).addToBackStack(null).commit()
    }




/*private fun createNotificationChannel() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val channel = NotificationChannel(
                Constants.CHANNE_ID,
                Constants.CHANNEL_NAME,
                NotificationManager.IMPORTANCE_DEFAULT
        )
        channel.description = Constants.CHANNEL_DESC
        val notificationManager = getSystemService(NotificationManager::class.java)
        notificationManager.createNotificationChannel(channel)
    }
}*/


    fun showToast(context: Activity?, message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }


    fun showDialogBox(mFragment: DialogFragment, mString: String) {
        var fragmentManager = supportFragmentManager
        mFragment.isCancelable = false
        mFragment.show(fragmentManager, mString)
    }


    fun checkEmpty(editText: EditText): Boolean {
        return TextUtils.isEmpty(editText.text.trim())

    }


    fun showDialog() {
        if (!mDialog.isShowing) {
            mDialog.show()
        }
    }

    fun dismissDialog() {
        if (mDialog.isShowing) {
            mDialog.dismiss()
        }
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        val view = currentFocus
        val ret = super.dispatchTouchEvent(ev)

        if (view is EditText) {
            val w = currentFocus
            val scrcoords = IntArray(2)
            w!!.getLocationOnScreen(scrcoords)
            val x = ev!!.rawX + w.left - scrcoords[0]
            val y = ev.rawY + w.top - scrcoords[1]
            if (ev.action == MotionEvent.ACTION_UP
                && (x < w.left || x >= w.right || y < w.top || y > w.bottom)
            ) {
                disappearKeyboard()
                view.clearFocus()
            }
        }
        return ret
    }
    fun disappearKeyboard() {
        val imm =
            getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        if (currentFocus != null) imm.hideSoftInputFromWindow(
            this.currentFocus!!.windowToken,
            InputMethodManager.HIDE_NOT_ALWAYS
        )
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }
    open fun backBtnPressed(view: View?) {
        finish()
    }

    fun addFragment(
        containerId: Int,
        fragment: Fragment,
        name: String,
        fragmentManager: FragmentManager
    ) {
        fragmentManager.beginTransaction()
            .add(containerId, fragment, name)
            /*    .setCustomAnimations(
                    R.anim.enter_from_left,
                    R.anim.exit_to_right,
                    R.anim.enter_from_right,
                    R.anim.exit_to_left
                )*/
            .addToBackStack(name)
            .commitAllowingStateLoss()
    }

    fun addFragmentWithoutBackstack(
        containerId: Int,
        fragment: Fragment,
        name: String,
        fragmentManager: FragmentManager
    ) {
        fragmentManager.beginTransaction()
            /*  .setCustomAnimations(
                  R.anim.enter_from_left,
                  R.anim.exit_to_right,
                  R.anim.enter_from_right,
                  R.anim.exit_to_left
              )*/
            .add(containerId, fragment, name)
            .commitAllowingStateLoss()
    }

    fun replaceFragment(
        containerId: Int,
        fragment: Fragment,
        name: String,
        fragmentManager: FragmentManager
    ) {
        fragmentManager.beginTransaction()  //            .setCustomAnimations(R.anim.fade_in, 0, 0, R.anim.fade_out)
            .replace(containerId, fragment, name)
            .addToBackStack(name).commitAllowingStateLoss()
    }

    fun clearBackStack(fragmentManager: FragmentManager) {
        for (i in 0 until fragmentManager.backStackEntryCount) {
            fragmentManager.popBackStack()
        }
    }


    fun getAppCachePath(context: Context?): String {
        var path = ""
        if (context != null) {
            val dir = context.cacheDir
            path = dir.absolutePath
        }
        return path
    }

    fun setError(string: String) {
        val snackBar: Snackbar =
            Snackbar.make(findViewById(android.R.id.content), string, Snackbar.LENGTH_SHORT)
        val snackBarView = snackBar.view
        snackBarView.setBackgroundColor(Color.parseColor("#C2272D"))
        snackBar.show()
        Snackbar.make(
            findViewById(android.R.id.content),
            string,
            Snackbar.LENGTH_SHORT
        ).show()





    }


    fun capitalizeString(name: String): String {
        val words = name.split(" ")
        var newStr = ""
        words.forEach {
            newStr += it.capitalize() + " "
        }
        return newStr.trimEnd()
    }


    /*  private fun logoutConfirmDialog() {
        val factory = LayoutInflater.from(this)
        val deleteDialogView: View = factory.inflate(R.layout.custom_logout_dialog, null)
        val deleteDialog: AlertDialog = AlertDialog.Builder(this).create()
        deleteDialog.setView(deleteDialogView)
        deleteDialogView.findViewById<View>(R.id.relative_confirm_button)
                .setOnClickListener(object : DialogInterface.OnClickListener,
                        View.OnClickListener {


                    override fun onClick(dialog: DialogInterface?, which: Int) {
                        deleteDialog.dismiss()
                    }

                    override fun onClick(v: View?) {


                        logoutUser()
                        deleteDialog.dismiss()
                    }
                })
        deleteDialogView.findViewById<View>(R.id.relative_cancel_button)
                .setOnClickListener(object : DialogInterface.OnClickListener,
                        View.OnClickListener {


                    override fun onClick(dialog: DialogInterface?, which: Int) {
                        deleteDialog.dismiss()
                    }

                    override fun onClick(v: View?) {
                        deleteDialog.dismiss()
                    }
                })

        deleteDialog.show()
    }
*/

}