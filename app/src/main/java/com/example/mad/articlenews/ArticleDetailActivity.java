package com.example.mad.articlenews;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class ArticleDetailActivity extends AppCompatActivity {
    DatabaseHelper myDb;
    String title,article,author,id,image;
    FirebaseAuth mFirebaseAuth;
    FirebaseUser mFirebaseUser;
//    private DatabaseReference myRef;
FirebaseDatabase myRef ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_detail);

        Intent intent = getIntent();

        myDb = new DatabaseHelper(this);

        TextView tvTitle = findViewById(R.id.tv_title);
        TextView tvArticle = findViewById(R.id.tv_article);
        TextView tvAuthor = findViewById(R.id.tv_author);
        ImageView imageView = findViewById(R.id.img_post);
        id = intent.getStringExtra("ID");
        title = intent.getStringExtra("TITLE");
        article = intent.getStringExtra("ARTICLE");
        author = intent.getStringExtra("AUTHOR");
        image = intent.getStringExtra("IMAGE");
        mFirebaseAuth =  FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();
        tvTitle.setText(title);
        tvArticle.setText(article);
        tvAuthor.setText(author);
        Glide.with(this).load(image).into(imageView);

    }

    public void delete(View view) {
        Intent home = new Intent(this,ListActivity.class);
        startActivity(home);
//
        DatabaseReference db = FirebaseDatabase.getInstance().getReference("Article").child(mFirebaseUser.getUid());


        db.child(id).removeValue();

        finish();
        Toast.makeText(this,id,Toast.LENGTH_SHORT).show();
    }
}
