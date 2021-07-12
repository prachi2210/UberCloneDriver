package com.wizebrain.adebdriver.base

import android.content.Context
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment


abstract class BaseFragment : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        /* if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

             activity?.window?.statusBarColor  = ContextCompat.getColor(requireContext(), R.color.appColor)
             activity?.window?.decorView?.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR

         }*/
        // activity?.window?.statusBarColor = ContextCompat.getColor(requireContext(), R.color.appColor)
    }


    fun openDialogFragment(mFragment: DialogFragment, mString: String) {
        var fragmentManager = childFragmentManager
        mFragment.isCancelable = false
        mFragment.show(fragmentManager, mString)
    }

    private fun addFragment(fragment: Fragment) {
        (activity as BaseFragment).addFragment(fragment)
    }

    private fun removeTopFragment() {
        (activity as BaseFragment).removeTopFragment()
    }

    private fun setTitle(resId: Int) {
        activity?.setTitle(resId)
    }

    private fun setTitle(title: String) {
        activity?.setTitle(title)
    }










    fun disappearKeyboard() {
        val imm = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(requireView().windowToken, 0)
    }

}