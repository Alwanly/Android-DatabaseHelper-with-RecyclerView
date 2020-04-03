package com.example.mad.articlenews;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class ArticleAdapter extends RecyclerView.Adapter<ArticleAdapter.ArticleHolder> {

    Context context;
    SQLiteDatabase database;
    DatabaseHelper databaseHelper;
    List<ArticleDetails> articleDetailsList;

    public ArticleAdapter(List<ArticleDetails> articleDetailsList) {
        this.articleDetailsList = articleDetailsList;
    }

    @NonNull
    @Override
    public ArticleHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.card_article,parent,false);
        ArticleHolder articleHolder = new ArticleHolder(view);
        return articleHolder;
    }

    @Override
    public void onBindViewHolder(final ArticleHolder holder, final int position) {
        ArticleDetails articleDetails = articleDetailsList.get(position);

        holder.tvTitle.setText(articleDetails.getTitle());
        holder.tvAuthor.setText(articleDetails.getAuthor());
        holder.tvArticle.setText(articleDetails.getArticle());

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArticleDetails articleDetails = articleDetailsList.get(position);

                Intent detail = new Intent(context,ArticleDetailActivity.class);
                detail.putExtra("ID",articleDetails.getId());
                detail.putExtra("TITLE",articleDetails.getTitle());
                detail.putExtra("ARTICLE",articleDetails.getArticle());
                detail.putExtra("AUTHOR",articleDetails.getAuthor());

                Toast.makeText(context,articleDetails.getId(),Toast.LENGTH_SHORT).show();

                context.startActivity(detail);
            }
        });
    }


    @Override
    public int getItemCount() {
        return articleDetailsList.size();
    }

    public class ArticleHolder extends RecyclerView.ViewHolder {
        TextView tvTitle,tvAuthor,tvArticle;
        CardView cardView;
        public ArticleHolder(@NonNull View itemView) {
            super(itemView);
            tvArticle = itemView.findViewById(R.id.tv_article);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvAuthor = itemView.findViewById(R.id.tv_author);
            cardView = itemView.findViewById(R.id.card_article);
        }
    }
}

