package com.wizebrain.adebdriver.ui.auth.document_verification

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.adebuser.base.BaseActivity
import com.wizebrain.adebdriver.R
import com.wizebrain.adebdriver.databinding.ActivityDocumentBinding
import com.wizebrain.adebdriver.databinding.ActivityLoginBinding
import com.wizebrain.adebdriver.ui.auth.SignUpActivity

class DocumentActivity : BaseActivity(), View.OnClickListener {
    private lateinit var binding: ActivityDocumentBinding

    companion object {
        fun getStartIntent(context: Context): Intent {
            return Intent(context, DocumentActivity::class.java)
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDocumentBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onClick(v: View?) {

    }

}