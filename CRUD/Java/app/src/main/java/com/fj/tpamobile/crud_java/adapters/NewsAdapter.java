package com.fj.tpamobile.crud_java.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fj.tpamobile.crud_java.R;
import com.fj.tpamobile.crud_java.activities.AddNewsActivity;
import com.fj.tpamobile.crud_java.data.Utils;
import com.fj.tpamobile.crud_java.models.News;

import java.util.ArrayList;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {

    private Context context;
    private ArrayList<News> news;

    public NewsAdapter(Context context, ArrayList<News> news) {
        this.context = context;
        this.news = news;
    }

    @NonNull
    @Override
    public NewsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_news, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsAdapter.ViewHolder viewHolder, final int i) {
        viewHolder.txtTitle.setText(news.get(i).getTitle());
        viewHolder.txtContent.setText(news.get(i).getContent());

        viewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, AddNewsActivity.class);
                intent.putExtra(Utils.NewsId, news.get(i).getId());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return news.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtTitle, txtContent;
        CardView cardView;

        ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtTitle = itemView.findViewById(R.id.txt_title);
            txtContent = itemView.findViewById(R.id.txt_content);
            cardView = itemView.findViewById(R.id.cv_news);
        }
    }
}
