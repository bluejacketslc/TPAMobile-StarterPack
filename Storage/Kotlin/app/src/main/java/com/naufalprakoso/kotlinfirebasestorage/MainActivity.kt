package com.naufalprakoso.kotlinfirebasestorage

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import org.jetbrains.anko.startActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        fab.setOnClickListener {
            startActivity<AddImage>()
        }
        
        val databaseRef = FirebaseDatabase.getInstance().getReference("images")
	    val images: ArrayList<Image> = ArrayList()
	    rv_image.layoutManager = GridLayoutManager(this, 2)
	    
	    databaseRef.addValueEventListener(object : ValueEventListener {
		    override fun onCancelled(p0: DatabaseError) {
			    println("Info: ${p0.message}")
		    }
		
		    override fun onDataChange(p0: DataSnapshot) {
			    images.clear()
			    for (dataSnapshot in p0.children){
				    val getValue = dataSnapshot.getValue(Image::class.java)
				    getValue?.let { images.add(it) }
			    }
			    
			    rv_image.adapter = ImageAdapter(applicationContext, images)
		    }
	    })
    }
}
