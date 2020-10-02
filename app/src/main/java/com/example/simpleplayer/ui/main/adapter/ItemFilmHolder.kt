package com.example.simpleplayer.ui.main.adapter

import android.annotation.SuppressLint
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.simpleplayer.model.Film
import kotlinx.android.synthetic.main.film_item.view.*

class ItemFilmHolder(
    itemView: View,
    private val itemClickListener: ItemFilmClickListener
) : RecyclerView.ViewHolder(itemView) {

    @SuppressLint("SetTextI18n")
    fun bind(film: Film) {

        itemView.root_item.setOnClickListener {
            itemClickListener.onItemFilmClick(film)
        }

        film.apply {
            itemView.title.text = title
            itemView.rating.text = "%.1f".format(rating)
            Glide.with(itemView.context)
                .load(posterUri)
                .into(itemView.poster)
        }
    }

    interface ItemFilmClickListener {
        fun onItemFilmClick(film: Film)
    }
}