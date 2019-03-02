package com.fj.tpamobile.crud_kotlin.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.fj.tpamobile.crud_kotlin.R
import com.fj.tpamobile.crud_kotlin.adapters.NewsAdapter
import com.fj.tpamobile.crud_kotlin.data.Utils
import com.fj.tpamobile.crud_kotlin.models.News
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import org.jetbrains.anko.startActivity

class MainActivity : AppCompatActivity(), View.OnClickListener {

    override fun onClick(v: View?) {
        when(v){
            fab -> startActivity<AddNewsActivity>()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        fab.setOnClickListener(this)

        rv_data.layoutManager = LinearLayoutManager(this)

        val news: ArrayList<News> = ArrayList()
        val dbRef = FirebaseDatabase
            .getInstance()
            .getReference("news")

        dbRef.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                println("InfoKotlin: ${p0.message}")
            }

            override fun onDataChange(p0: DataSnapshot) {
                news.clear()
                for(dataSnapshot in p0.children){
                    val getValue = dataSnapshot.getValue(News::class.java)
                    getValue?.id = dataSnapshot.key

                    getValue?.let { news.add(it) }
                }

                rv_data.adapter = NewsAdapter(applicationContext, news){
                    startActivity<AddNewsActivity>(
                        Utils.NewsId to it.id
                    )
                }
            }
        })
    }
}
