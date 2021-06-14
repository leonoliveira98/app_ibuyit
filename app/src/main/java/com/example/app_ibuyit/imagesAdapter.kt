package com.example.app_ibuyit

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class imagesAdapter (private val exampleList: List<produto>) : RecyclerView.Adapter<imagesAdapter.ExampleViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExampleViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.recycler_images, parent, false)

        return ExampleViewHolder(itemView)

    }

    override fun getItemCount(): Int = exampleList.size



    class ExampleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imagem: ImageView = itemView.findViewById(R.id.imagem)
        var nome: TextView = itemView.findViewById(R.id.nomeProduto)


    }

    override fun onBindViewHolder(holder: imagesAdapter.ExampleViewHolder, position: Int) {
        val currentItem = exampleList[position]

        Picasso.get()
            .load(currentItem.imagem)
            .into(holder.imagem)

        holder.nome.text = currentItem.nome

    }


}