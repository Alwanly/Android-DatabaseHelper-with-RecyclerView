package com.example.mad.articlenews;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Button btn_create_list = findViewById(R.id.btn_create_list);
        Button btn_article_list = findViewById(R.id.btn_article_list);

        btn_create_list.setOnClickListener(this);
        btn_article_list.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_create_list:
                Intent create = new Intent(this,MainActivity.class);
                startActivity(create);
                break;
            case R.id.btn_article_list:
                Intent list = new Intent(this,ListActivity.class);
                startActivity(list);
        }
    }

}