package com.wizebrain.adebdriver.extensions

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle



/**
 * Kotlin Extensions for simpler, easier and funw way
 * of launching of Activities
 */
inline fun <reified T : Any> Activity.launchActivity(
    requestCode: Int = -1,
    options: Bundle? = null,
    noinline init: Intent.() -> Unit = {}
) {
    val intent = newIntent<T>(this)
    intent.init()
    startActivityForResult(intent, requestCode, options)
   // overridePendingTransition(R.anim.right_in, R.anim.left_out)
}

inline fun <reified T : Any> Context.launchActivity(
    options: Bundle? = null,
    noinline init: Intent.() -> Unit = {}
) {
    val intent = newIntent<T>(this)
    intent.init()
    startActivity(intent, options)
}

inline fun <reified T : Any> newIntent(context: Context): Intent =
    Intent(context, T::class.java)