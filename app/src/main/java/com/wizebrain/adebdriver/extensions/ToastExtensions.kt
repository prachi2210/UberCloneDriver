package com.wizebrain.adebdriver.extensions

import android.content.Context
import android.widget.Toast
import androidx.annotation.StringRes

fun Context.showToast(text: CharSequence, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, text, duration).show()
}


fun Context.toast(@StringRes messageRes: Int) {
    Toast.makeText(this, messageRes, Toast.LENGTH_SHORT).show()
}

fun Context.toastLong(@StringRes messageRes: String) {
    Toast.makeText(this, messageRes, Toast.LENGTH_LONG).show()
}


