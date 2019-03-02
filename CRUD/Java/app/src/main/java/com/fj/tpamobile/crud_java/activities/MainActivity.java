package com.fj.tpamobile.crud_java.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.fj.tpamobile.crud_java.R;
import com.fj.tpamobile.crud_java.adapters.NewsAdapter;
import com.fj.tpamobile.crud_java.models.News;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ArrayList<News> news;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        recyclerView = findViewById(R.id.rv_data);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        news = new ArrayList<>();
        DatabaseReference dbRef = FirebaseDatabase
                .getInstance()
                .getReference("news");

        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                news.clear();
                for (DataSnapshot snapShot : dataSnapshot.getChildren()){
                    News getNews = snapShot.getValue(News.class);
                    getNews.setId(snapShot.getKey());

                    news.add(getNews);
                }

                recyclerView.setAdapter(new NewsAdapter(getApplicationContext(), news));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d("InfoMainActivity", databaseError.getMessage());
            }
        });

        fab.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.fab:
                Intent intent = new Intent(this, AddNewsActivity.class);
                startActivity(intent);
                break;
        }
    }
}
