package com.fj.tpamobile.auth_kotlin

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_register.*
import org.jetbrains.anko.toast

class RegisterActivity : AppCompatActivity(), View.OnClickListener {
    override fun onClick(v: View?) {
        when(v){
            btn_register -> {
                val email = edt_email.text.toString()
                val password = edt_password.text.toString()

                when {
                    email.isEmpty() -> edt_email.error = "Must be filled"
                    password.isEmpty() -> edt_password.error = "Must be filled"
                    else -> {
                        val auth = FirebaseAuth.getInstance()

                        auth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener(this){
                                if (it.isSuccessful){
                                    toast("Successfully")

                                    finish()
                                }else{
                                    toast("Email already registered!")
                                }
                            }
                            .addOnFailureListener(this){
                                println("InfoRegister: ${it.message}")
                            }
                    }
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        btn_register.setOnClickListener(this)
    }
}
