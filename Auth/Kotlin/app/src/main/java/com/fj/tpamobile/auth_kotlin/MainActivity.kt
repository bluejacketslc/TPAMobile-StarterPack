package com.fj.tpamobile.auth_kotlin

import android.annotation.SuppressLint
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

class MainActivity : AppCompatActivity(),
    View.OnClickListener, GoogleApiClient.OnConnectionFailedListener {

    private var mGoogleApiClient: GoogleApiClient? = null
    private val REQUEST_CODE_SIGN_IN = 1234

    override fun onClick(v: View?) {
        when(v){
            txt_register -> startActivity<RegisterActivity>()

            btn_google_sign_in -> signInGoogle()

            btn_login -> {
                val email = edt_email.text.toString()
                val password = edt_password.text.toString()

                when{
                    email.isEmpty() -> edt_email.error = "Must be filled"
                    password.isEmpty() -> edt_password.error = "Must be filled"
                    else -> {
                        val auth = FirebaseAuth.getInstance()

                        auth.signInWithEmailAndPassword(email, password)
                            .addOnCompleteListener(this){
                                if (it.isSuccessful){
                                    toast("Successfully")

                                    startActivity<HomeActivity>()
                                    finish()
                                }else{
                                    toast("Email or Password Invalid")
                                }
                            }
                            .addOnFailureListener(this){
                                println("InfoLogin: ${it.message}")
                            }
                    }
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()

        val currentUser = FirebaseAuth.getInstance().currentUser
        if (currentUser != null){
            startActivity<HomeActivity>()
            finish()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(this.resources.getString(R.string.google_sign_in_key))
            .requestEmail().build()

        mGoogleApiClient = GoogleApiClient.Builder(this)
            .enableAutoManage(this, this)
            .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
            .build()

        btn_login.setOnClickListener(this)
        btn_google_sign_in.setOnClickListener(this)
        txt_register.setOnClickListener(this)
    }

    private fun signInGoogle(){
        val intent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient)
        startActivityForResult(intent, REQUEST_CODE_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_SIGN_IN) {
            val result = Auth.GoogleSignInApi.getSignInResultFromIntent(data)
            if (result.isSuccess) {
                val account = result.signInAccount
                firebaseAuthWithGoogle(account!!)
            } else {
                toast("Login failed")
                println("InfoGoogleSignIn: ${result.status}")
            }
        }
    }

    @SuppressLint("ApplySharedPref", "ResourceAsColor")
    private fun firebaseAuthWithGoogle(acct: GoogleSignInAccount) {
        val credential = GoogleAuthProvider.getCredential(acct.idToken, null)

        val mAuth = FirebaseAuth.getInstance()

        mAuth.signInWithCredential(credential)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    toast("Login successful")

                    startActivity<HomeActivity>()
                    finish()
                } else {
                    toast("Login failed")
                }
            }
            .addOnFailureListener {
                println("InfoGoogleSignIn: ${it.message}")
            }
    }

    override fun onConnectionFailed(p0: ConnectionResult) {
        toast("Google Play Services error")
    }
}
