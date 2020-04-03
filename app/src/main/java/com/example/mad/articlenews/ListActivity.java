package com.example.mad.articlenews;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class ListActivity extends AppCompatActivity {

    DatabaseHelper databaseHelper;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter articleadapter;
    private RecyclerView.LayoutManager layoutManager;
    List<ArticleDetails> articleDetailsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        databaseHelper= new DatabaseHelper(this);
        recyclerView= findViewById(R.id.recycler_view);


        articleDetailsList = new ArrayList<ArticleDetails>();
        Cursor cur = databaseHelper.getAllData();
        if (cur != null ){
            while (cur.moveToNext()){
                ArticleDetails articleDetailsItem = new ArticleDetails();
                articleDetailsItem.setId(cur.getString(0));
                articleDetailsItem.setTitle(cur.getString(1));
                articleDetailsItem.setArticle(cur.getString(2));
                articleDetailsItem.setAuthor(cur.getString(3));
                articleDetailsList.add(articleDetailsItem);
            }
        }

        cur.close();

        layoutManager = new LinearLayoutManager(this);
        articleadapter = new ArticleAdapter(articleDetailsList);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(articleadapter);
    }
}
