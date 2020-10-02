package com.example.simpleplayer.ui.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.simpleplayer.R
import com.example.simpleplayer.model.Film

class FilmListAdapter(
    private val itemClickListener: ItemFilmHolder.ItemFilmClickListener
) : RecyclerView.Adapter<ItemFilmHolder>() {

    private val currentFilmList = mutableListOf<Film>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemFilmHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.film_item,
            parent,
            false
        )
        return ItemFilmHolder(view, itemClickListener)
    }

    override fun onBindViewHolder(holder: ItemFilmHolder, position: Int) {
        holder.bind(currentFilmList[position])
    }

    override fun getItemCount() = currentFilmList.size

    fun update(filmList: List<Film>){
        currentFilmList.apply {
            clear()
            addAll(filmList)
        }
        notifyDataSetChanged()
    }
}