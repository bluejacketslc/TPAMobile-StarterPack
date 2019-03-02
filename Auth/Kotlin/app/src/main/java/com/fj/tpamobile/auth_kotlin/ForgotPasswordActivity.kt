package com.fj.tpamobile.auth_kotlin

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_forgot_password.*
import org.jetbrains.anko.toast

class ForgotPasswordActivity : AppCompatActivity(), View.OnClickListener {

    override fun onClick(v: View?) {
        when(v){
            btn_send -> {
                val email = edt_email.text.toString()
                when{
                    email.isEmpty() -> edt_email.error = "Must be filled"
                    else -> {
                        FirebaseAuth.getInstance().sendPasswordResetEmail(email)
                            .addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                    edt_email.setText("")
                                    toast("Link reset password sent to your email")
                                }
                            }
                            .addOnFailureListener {
                                toast("Email not registered")
                            }
                    }
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)

        btn_send.setOnClickListener(this)
    }
}
