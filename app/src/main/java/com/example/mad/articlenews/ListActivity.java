package com.example.mad.articlenews;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ListActivity extends AppCompatActivity {

    DatabaseHelper databaseHelper;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter articleadapter;
    private RecyclerView.LayoutManager layoutManager;
    List<ArticleDetails> articleDetailsList;
    private DatabaseReference myRef;
    FirebaseAuth mFirebaseAuth;
    FirebaseUser mFirebaseUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);


        databaseHelper = new DatabaseHelper(this);
        recyclerView = findViewById(R.id.recycler_view);
        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();
        String tokenEmail = mFirebaseUser.getUid();
        myRef = FirebaseDatabase.getInstance().getReference("Article").child(tokenEmail);

        articleDetailsList = new ArrayList<ArticleDetails>();
//
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                articleDetailsList.clear();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    ArticleDetails articleDetailsItem = new ArticleDetails(
                            ds.child("title").getValue().toString(),
                            ds.child("article").getValue().toString(),
                            ds.child("author").getValue().toString(),
                            ds.child("id").getValue().toString(),
                            ds.child("imagePost").getValue().toString()
                    );
                    articleDetailsList.add(articleDetailsItem);
                    layoutManager = new LinearLayoutManager(ListActivity.this);
                    articleadapter = new ArticleAdapter(articleDetailsList);
                    recyclerView.setLayoutManager(layoutManager);
                    recyclerView.setAdapter(articleadapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

}
