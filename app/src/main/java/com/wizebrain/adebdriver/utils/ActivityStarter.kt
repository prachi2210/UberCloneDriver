package com.wizebrain.adebdriver.utils

import android.app.Activity
import android.content.Intent
import androidx.annotation.AnimRes
import androidx.fragment.app.Fragment

class ActivityStarter private constructor(private val startIntent: Intent) {
    private var shouldFinishCurrentActivity = false
    private var shouldFinishAffinity = false
    private var requestCode = -1

/*    @AnimRes
 //   private var enterAnimation: Int = R.anim.enter_from_right

    @AnimRes
 //   private var exitAnimation: Int =  R.anim.exit_to_left*/


    companion object {

        fun of(startIntent: Intent): ActivityStarter {
            startIntent.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_CLEAR_TOP
            return ActivityStarter(startIntent)
        }
    }


    fun animate(@AnimRes enterAnimation: Int, @AnimRes exitAnimation: Int): ActivityStarter {
      //  this.enterAnimation = enterAnimation
      //  this.exitAnimation = exitAnimation
        return this
    }

    fun requestCode(requestCode: Int): ActivityStarter {
        require(requestCode > 0) { "Request Code can't be 0 or less than 0 when starting $startIntent" }
        this.requestCode = requestCode
        return this
    }


    fun finishCurrentActivity(): ActivityStarter {
        this.shouldFinishCurrentActivity = true
        return this
    }

    fun finishAffinity(): ActivityStarter {
        this.shouldFinishAffinity = true
        return this
    }


    fun startFrom(activity: Activity) {

        if (requestCode > 0) {

            activity.startActivityForResult(startIntent, requestCode)
        } else {
            activity.startActivity(startIntent)
        }

        /*if (enterAnimation != -1 && exitAnimation != -1) {
            activity.overridePendingTransition(enterAnimation, exitAnimation)
        }*/


      //  activity.overridePendingTransition(enterAnimation, exitAnimation)

        if (shouldFinishCurrentActivity) {
            activity.finish()
        } else if (shouldFinishAffinity) {
            activity.finishAffinity()
        }
    }

    fun startFrom(fragment: Fragment) {

        if (requestCode > 0) {
            fragment.startActivityForResult(startIntent, requestCode)
        } else {
            fragment.startActivity(startIntent)
        }

      /*  if (enterAnimation != -1 && exitAnimation != -1) {
            fragment.activity?.overridePendingTransition(enterAnimation, exitAnimation)
        }*/

        if (shouldFinishCurrentActivity) {
            fragment.activity?.finish()
        } else if (shouldFinishAffinity) {
            fragment.activity?.finishAffinity()
        }
    }
}