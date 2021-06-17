package com.example.app_ibuyit

import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.app_ibuyit.fragments.CarrinhoFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.squareup.picasso.Picasso
import org.w3c.dom.Text

class imagesPromoAdapter (private val exampleList: List<produtoPromo>) : RecyclerView.Adapter<imagesPromoAdapter.ExampleViewHolder>() {


    private val mAuth : FirebaseAuth = FirebaseAuth.getInstance()
    private val dbFirestore : FirebaseFirestore = FirebaseFirestore.getInstance()

    val uid = mAuth.uid.toString()
    private val fireStoreDoc = FirebaseFirestore.getInstance().collection(uid)


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
        var card_view :CardView = itemView.findViewById(R.id.card_view)

    }

    override fun onBindViewHolder(holder: imagesPromoAdapter.ExampleViewHolder, position: Int) {
        val currentItem = exampleList[position]

        Picasso.get()
            .load(currentItem.imagem)
            .into(holder.imagem)

        holder.nome.text = currentItem.nome

        holder.preco2.text = currentItem.precoPromo

        holder.card_view.setOnClickListener{

            holder.card_view.setCardBackgroundColor(Color.GRAY)
            addProduto(currentItem.precoPromo, currentItem.imagem, currentItem.nome)


        }

    }


    //passar cenas dos produtos
    private fun addProduto(precoPromo: String, imagem: String, nome: String) {
        val produtoHash = HashMap<String, Any>()
        produtoHash["imagem"] = imagem
        produtoHash["nome"] = nome
        produtoHash["preco"] = precoPromo
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