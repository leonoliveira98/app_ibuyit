package com.example.app_ibuyit

import android.content.Intent
import android.content.Intent.getIntent
import android.graphics.Color
import android.provider.SyncStateContract.Helpers.update
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.squareup.picasso.Picasso
import org.w3c.dom.Text

class carrinhoAdapter (private val exampleList: List<carrinho>) : RecyclerView.Adapter<carrinhoAdapter.ExampleViewHolder>() {

    private val mAuth : FirebaseAuth = FirebaseAuth.getInstance()
    private val dbFirestore : FirebaseFirestore = FirebaseFirestore.getInstance()

    val uid = mAuth.uid.toString()
    private val fireStoreDoc = FirebaseFirestore.getInstance().collection(uid)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExampleViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.recycler_carrinho, parent, false)

        return ExampleViewHolder(itemView)

    }

    override fun getItemCount(): Int = exampleList.size


    class ExampleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imagem: ImageView = itemView.findViewById(R.id.imagem)
        var nome: TextView = itemView.findViewById(R.id.nomeProduto)
        var preco: TextView = itemView.findViewById(R.id.preco)
        var quantidade : TextView = itemView.findViewById(R.id.quantidade)


        var btn_apagar: ImageButton = itemView.findViewById(R.id.btn_apagar)

        var btn_minus : ImageButton = itemView.findViewById(R.id.btn_minus)
        var btn_plus : ImageButton = itemView.findViewById(R.id.btn_plus)

    }

    override fun onBindViewHolder(holder: carrinhoAdapter.ExampleViewHolder, position: Int) {
        val currentItem = exampleList[position]

        Picasso.get()
            .load(currentItem.imagem)
            .into(holder.imagem)

        holder.nome.text = currentItem.nome

        holder.preco.text = currentItem.preco

        holder.quantidade.text = currentItem.quantidade
        var a = currentItem.quantidade.toString().toInt()




        //+ ou - quantidade
        holder.btn_plus.setOnClickListener {
            a += 1
            holder.quantidade.text = a.toString()

            val produtoHash = HashMap<String, Any>()
            produtoHash["imagem"] = currentItem.imagem
            produtoHash["nome"] = currentItem.nome
            produtoHash["preco"] = currentItem.preco
            produtoHash["quantidade"] = holder.quantidade.text

            val userRef = fireStoreDoc
            val uid = mAuth.uid.toString()

            userRef.document(currentItem.nome).set(produtoHash).addOnCompleteListener { it2 ->
                when {
                    it2.isSuccessful -> {
                        //Toast.makeText(this, "Adicionado ao carrinho", Toast.LENGTH_LONG).show()
                        //executarOutraActivity(CarrinhoFragment::class.java, "Valores", arrayListOf("a"))
                    }
                    else -> {

                        //Toast.makeText(this, "Ups, algo correu mal!", Toast.LENGTH_LONG).show()
                    }
                }
            }

        }
        holder.btn_minus.setOnClickListener {
            if(a > 1){
                a -= 1
                holder.quantidade.text = a.toString()


                val produtoHash = HashMap<String, Any>()
                produtoHash["imagem"] = currentItem.imagem
                produtoHash["nome"] = currentItem.nome
                produtoHash["preco"] = currentItem.preco
                produtoHash["quantidade"] = holder.quantidade.text


                val userRef = fireStoreDoc
                val uid = mAuth.uid.toString()

                userRef.document(currentItem.nome).set(produtoHash).addOnCompleteListener { it2 ->
                    when {
                        it2.isSuccessful -> {
                            //Toast.makeText(this, "Adicionado ao carrinho", Toast.LENGTH_LONG).show()
                            //executarOutraActivity(CarrinhoFragment::class.java, "Valores", arrayListOf("a"))
                        }
                        else -> {

                            //Toast.makeText(this, "Ups, algo correu mal!", Toast.LENGTH_LONG).show()
                        }
                    }
                }
            }
        }

        //Apagar da collection o item
        holder.btn_apagar.setOnClickListener {

            dbFirestore.collection(uid).document(holder.nome.text.toString()).delete()

        }

    }



}