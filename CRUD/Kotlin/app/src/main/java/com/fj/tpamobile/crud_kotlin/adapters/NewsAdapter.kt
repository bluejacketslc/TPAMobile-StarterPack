package com.fj.tpamobile.crud_kotlin.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.fj.tpamobile.crud_kotlin.R
import com.fj.tpamobile.crud_kotlin.models.News
import kotlinx.android.synthetic.main.item_news.view.*

class NewsAdapter(
    private val context: Context,
    private val news: ArrayList<News>,
    private val listener: (News) -> Unit
) : RecyclerView.Adapter<NewsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): NewsAdapter.ViewHolder =
        ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_news, p0, false))

    override fun getItemCount(): Int = news.size

    override fun onBindViewHolder(p0: NewsAdapter.ViewHolder, p1: Int) =
            p0.bindNews(news[p1], listener)

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bindNews(news: News, listener: (News) -> Unit){
            itemView.txt_title.text = news.title
            itemView.txt_content.text = news.content

            itemView.cv_news.setOnClickListener {
                listener(news)
            }
        }
    }
}