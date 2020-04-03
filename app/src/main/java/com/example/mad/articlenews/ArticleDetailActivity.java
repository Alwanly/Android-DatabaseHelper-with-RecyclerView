package com.example.mad.articlenews;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class ArticleDetailActivity extends AppCompatActivity {
    DatabaseHelper myDb;
    String title,article,author,id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_detail);

        Intent intent = getIntent();

        myDb = new DatabaseHelper(this);

        TextView tvTitle = findViewById(R.id.tv_title);
        TextView tvArticle = findViewById(R.id.tv_article);
        TextView tvAuthor = findViewById(R.id.tv_author);

        id = intent.getStringExtra("ID");
        title = intent.getStringExtra("TITLE");
        article = intent.getStringExtra("ARTICLE");
        author = intent.getStringExtra("AUTHOR");

        tvTitle.setText(title);
        tvArticle.setText(article);
        tvAuthor.setText(author);

    }

    public void delete(View view) {
        Intent home = new Intent(this,ListActivity.class);
        startActivity(home);
        myDb.deleteData(id);
        finish();
        Toast.makeText(this,"Delete Success",Toast.LENGTH_SHORT).show();
    }
}
