package com.fj.tpamobile.crud_kotlin.activities

import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.fj.tpamobile.crud_kotlin.R
import com.fj.tpamobile.crud_kotlin.data.Utils
import com.fj.tpamobile.crud_kotlin.models.News
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

import kotlinx.android.synthetic.main.activity_add_news.*
import kotlinx.android.synthetic.main.content_add_news.*
import org.jetbrains.anko.toast

class AddNewsActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var getId: String

    override fun onClick(v: View?) {
        when(v){
            fab -> {
                val title = edt_title.text.toString()
                val content = edt_content.text.toString()

                when{
                    title.isEmpty() -> edt_title.error = "Must be filled"
                    content.isEmpty() -> edt_content.error = "Must be filled"
                    else -> {
                        val dbRef = FirebaseDatabase
                            .getInstance()
                            .getReference("news")

                        if (intent.hasExtra(Utils.NewsId)){
                            dbRef.child(getId).child("title").setValue(title)
                            dbRef.child(getId).child("content").setValue(content)
                        }else{
                            val dbAdd = dbRef.push()
                            dbAdd.child("title").setValue(title)
                            dbAdd.child("content").setValue(content)
                        }

                        toast("Successfully!")
                        finish()
                    }
                }
            }

            img_delete -> {
                AlertDialog.Builder(this)
                    .setTitle("Are you sure?")
                    .setMessage("Delete this news?")
                    .setNegativeButton(android.R.string.no, null)
                    .setPositiveButton(android.R.string.yes) { _, _ ->
                        FirebaseDatabase
                            .getInstance()
                            .getReference("news")
                            .child(getId)
                            .removeValue()

                        toast("Successfully")
                        finish()
                    }
                    .show()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_news)
        setSupportActionBar(toolbar)

        if (intent.hasExtra(Utils.NewsId)){
            img_delete.visibility = View.VISIBLE

            getId = intent.getStringExtra(Utils.NewsId)
            val dbRef = FirebaseDatabase
                .getInstance()
                .getReference("news")
                .child(getId)

            dbRef.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onCancelled(p0: DatabaseError) {
                    println("InfoAddNews: ${p0.message}")
                }

                override fun onDataChange(p0: DataSnapshot) {
                    val getNews = p0.getValue(News::class.java)

                    edt_title.setText(getNews?.title)
                    edt_content.setText(getNews?.content)
                }
            })
        }

        fab.setOnClickListener(this)
        img_delete.setOnClickListener(this)
    }
}
