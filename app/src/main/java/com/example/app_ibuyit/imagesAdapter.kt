package com.example.app_ibuyit

import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.squareup.picasso.Picasso

class imagesAdapter (private val exampleList: List<produto>) : RecyclerView.Adapter<imagesAdapter.ExampleViewHolder>() {

    private val mAuth : FirebaseAuth = FirebaseAuth.getInstance()
    private val dbFirestore : FirebaseFirestore = FirebaseFirestore.getInstance()

    val uid = mAuth.uid.toString()
    private val fireStoreDoc = FirebaseFirestore.getInstance().collection(uid)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExampleViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.recycler_images, parent, false)

        return ExampleViewHolder(itemView)

    }

    override fun getItemCount(): Int = exampleList.size



    class ExampleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imagem: ImageView = itemView.findViewById(R.id.imagem)
        var nome: TextView = itemView.findViewById(R.id.nomeProduto)
        var preco: TextView = itemView.findViewById(R.id.preco)
        var card_view : CardView = itemView.findViewById(R.id.card_view)


    }

    override fun onBindViewHolder(holder: imagesAdapter.ExampleViewHolder, position: Int) {
        val currentItem = exampleList[position]

        Picasso.get()
            .load(currentItem.imagem)
            .into(holder.imagem)

        holder.nome.text = currentItem.nome
        if(currentItem.promocao == "true"){

            holder.preco.text = currentItem.preco
            holder.preco.setTextColor(Color.RED)

        } else {
            holder.preco.text = currentItem.preco
            holder.preco.setTextColor(Color.BLACK)

        }

        holder.card_view.setOnClickListener{

            holder.card_view.setCardBackgroundColor(Color.GRAY)
            addProduto(currentItem.preco, currentItem.imagem, currentItem.nome)


        }

    }

    //passar cenas dos produtos
    private fun addProduto(preco: String, imagem: String, nome: String) {
        val produtoHash = HashMap<String, Any>()
        produtoHash["imagem"] = imagem
        produtoHash["nome"] = nome
        produtoHash["preco"] = preco
        produtoHash["quantidade"] = "1"


        val userRef = fireStoreDoc
        val uid = mAuth.uid.toString()

        userRef.document(nome).set(produtoHash).addOnCompleteListener { it2 ->
            when {
                it2.isSuccessful -> {


                    //Toast.makeText(this, "Adicionado ao carrinho", Toast.LENGTH_LONG).show()
                    //executarOutraActivity(CarrinhoFragment::class.java, "Valores", arrayListOf("a"))

                }
                else -> {
                    Log.d("matata", nome)
                    //Toast.makeText(this, "Ups, algo correu mal!", Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}