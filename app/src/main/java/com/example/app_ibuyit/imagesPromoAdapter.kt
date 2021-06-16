package com.example.app_ibuyit

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import org.w3c.dom.Text

class imagesPromoAdapter (private val exampleList: List<produtoPromo>) : RecyclerView.Adapter<imagesPromoAdapter.ExampleViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExampleViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.recycler_promo, parent, false)

        return ExampleViewHolder(itemView)

    }

    override fun getItemCount(): Int = exampleList.size



    class ExampleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imagem: ImageView = itemView.findViewById(R.id.imagem)
        var nome: TextView = itemView.findViewById(R.id.nomePromo)
        var preco2 : TextView = itemView.findViewById(R.id.preco)


    }

    override fun onBindViewHolder(holder: imagesPromoAdapter.ExampleViewHolder, position: Int) {
        val currentItem = exampleList[position]

        Picasso.get()
            .load(currentItem.imagem)
            .into(holder.imagem)

        holder.nome.text = currentItem.nome

        holder.preco2.text = currentItem.precoPromo

    }


}