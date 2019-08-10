package com.naufalprakoso.kotlinfirebasestorage

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_image.view.*

class ImageAdapter(
	private val context: Context,
	private val images: ArrayList<Image>
) : RecyclerView.Adapter<ImageAdapter.ViewHolder>() {
	
	override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ImageAdapter.ViewHolder =
		ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_image, p0, false))
	
	override fun getItemCount(): Int = images.size
	
	override fun onBindViewHolder(p0: ImageAdapter.ViewHolder, p1: Int) =
			p0.bindItem(images[p1])
	
	class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
		fun bindItem(image: Image){
			itemView.txt_img_name.text = image.name
			Glide.with(itemView.context).load(image.image).into(itemView.img_data)
		}
	}
}