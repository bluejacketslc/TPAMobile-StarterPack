package com.fj.tpamobile.auth_kotlin

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_home.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

class HomeActivity : AppCompatActivity(), View.OnClickListener {
    override fun onClick(v: View?) {
        when(v){
            btn_logout -> {
                val auth = FirebaseAuth.getInstance()
                auth.signOut()

                toast("Logout success")
                startActivity<MainActivity>()
                finish()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        btn_logout.setOnClickListener(this)
    }
}
