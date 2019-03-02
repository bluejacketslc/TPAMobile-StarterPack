package com.fj.tpamobile.crud_java.activities;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.fj.tpamobile.crud_java.R;
import com.fj.tpamobile.crud_java.data.Utils;
import com.fj.tpamobile.crud_java.models.News;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AddNewsActivity extends AppCompatActivity implements View.OnClickListener {

    private String getId;

    private EditText edtTitle;
    private EditText edtContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_news);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        ImageView imgDelete = findViewById(R.id.img_delete);
        edtTitle = findViewById(R.id.edt_title);
        edtContent = findViewById(R.id.edt_content);

        if (getIntent().hasExtra(Utils.NewsId)){
            imgDelete.setVisibility(View.VISIBLE);

            getId = getIntent().getStringExtra(Utils.NewsId);
            DatabaseReference dbRef = FirebaseDatabase
                    .getInstance()
                    .getReference("news")
                    .child(getId);

            dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    News news = dataSnapshot.getValue(News.class);

                    edtTitle.setText(news.getTitle());
                    edtContent.setText(news.getContent());
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Log.d("InfoAddNews", databaseError.getMessage());
                }
            });
        }

        fab.setOnClickListener(this);
        imgDelete.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.fab:
                String title = edtTitle.getText().toString();
                String content = edtContent.getText().toString();

                if (title.isEmpty()){
                    edtTitle.setError("Must be filled");
                }else if (content.isEmpty()){
                    edtContent.setError("Must be filled");
                }else{
                    DatabaseReference dbRef = FirebaseDatabase
                            .getInstance()
                            .getReference("news");

                    if (getIntent().hasExtra(Utils.NewsId)){
                        dbRef.child(getId).child("title").setValue(title);
                        dbRef.child(getId).child("content").setValue(content);
                    }else{
                        DatabaseReference dbAdd = dbRef.push();
                        dbAdd.child("title").setValue(title);
                        dbAdd.child("content").setValue(content);
                    }

                    Toast.makeText(this, "Successfully", Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;
            case R.id.img_delete:
                new AlertDialog.Builder(this)
                        .setTitle("Are you sure?")
                        .setMessage("Delete this news?")
                        .setNegativeButton(android.R.string.no, null)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                FirebaseDatabase
                                        .getInstance()
                                        .getReference("news")
                                        .child(getId)
                                        .removeValue();

                                Toast.makeText(AddNewsActivity.this, "Successfully", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        })
                    .show();
                break;
        }
    }
}
