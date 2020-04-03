package com.example.mad.articlenews;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    DatabaseHelper myDb;
    EditText editAuthor,editArticle,editTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myDb = new DatabaseHelper(this);
        editAuthor = findViewById(R.id.et_author);
        editArticle =findViewById(R.id.et_article);
        editTitle = findViewById(R.id.et_title);
    }


    //fungsi menambah data
    public void add(View view) {
        myDb.insertData(editTitle.getText().toString(),editArticle.getText().toString(),editAuthor.getText().toString());
        Toast.makeText(this,"Success",Toast.LENGTH_SHORT).show();
        Intent list = new Intent(this,HomeActivity.class);
        startActivity(list);
    }


}
