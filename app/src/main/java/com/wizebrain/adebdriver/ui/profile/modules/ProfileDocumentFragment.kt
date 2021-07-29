package com.wizebrain.adebdriver.ui.profile.modules

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.wizebrain.adebdriver.R
import com.wizebrain.adebdriver.base.BaseFragment
import com.wizebrain.adebdriver.databinding.FragmentProfileBookingBinding
import com.wizebrain.adebdriver.databinding.FragmentProfileDocumentBinding
import kotlinx.android.synthetic.main.view_document_request.*


class ProfileDocumentFragment : BaseFragment() {

    private var _binding: FragmentProfileDocumentBinding? = null

    private val mBottomSheetDialog by lazy {
        BottomSheetDialog(requireActivity(), R.style.SheetDialog)
    }
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentProfileDocumentBinding.inflate(inflater, container, false)
        return binding.root


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.tvDocumentChange.setOnClickListener {
            bottomSheet()
        }


    }

    private fun bottomSheet() {
        val sheetView: View =
            layoutInflater.inflate(R.layout.view_document_request, null)
        mBottomSheetDialog.setContentView(sheetView)
        mBottomSheetDialog.show()
        mBottomSheetDialog.tv_confirm.setOnClickListener {
            mBottomSheetDialog.hide()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}