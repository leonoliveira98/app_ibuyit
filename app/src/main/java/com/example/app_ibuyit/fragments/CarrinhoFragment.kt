package com.example.app_ibuyit.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.app_ibuyit.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_carrinho.*
import kotlinx.android.synthetic.main.fragment_carrinho.view.*
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.view.*
import kotlinx.android.synthetic.main.fragment_store.view.*
import org.w3c.dom.Text


class CarrinhoFragment : Fragment() {

    private val mAuth : FirebaseAuth = FirebaseAuth.getInstance()
    private val dbFirestore : FirebaseFirestore = FirebaseFirestore.getInstance()

    val uid = mAuth.uid.toString()
    private val fireStoreDoc = FirebaseFirestore.getInstance().collection(uid)
    lateinit var btn_pagar : Button
    lateinit var dinheiro: TextView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        var view = inflater.inflate(R.layout.fragment_carrinho, container, false)

        val user = mAuth.currentUser


        var email = user?.email.toString()


        buscarProdutos {

            view.recyclerCar.adapter = carrinhoAdapter(it)

            view.recyclerCar.layoutManager = LinearLayoutManager(activity)
            //view.recyclerCar.layoutManager = GridLayoutManager(activity, 3)

        }

        btn_pagar = view.findViewById(R.id.btn_pagar)

        // Apagar todos os documentos da collection
        btn_pagar.setOnClickListener {

            dbFirestore.collection(uid)
                .get()
                .addOnCompleteListener {
                    if(it.isSuccessful) {
                        for(document in it.result!!) {

                            dbFirestore.collection(uid).document(document.data.getValue(("nome")).toString()).delete()
                            executarOutraActivity(MainActivity::class.java, "Valores", arrayListOf("a"))

                        }

                    }
                }

        }

        dinheiro = view.findViewById(R.id.dinheiro)

        dinheiro.setOnClickListener {

        }




        return view
    }


    private fun executarOutraActivity(outraActivity: Class<*>, chave: String, argsParaOutraActivity: ArrayList<String>) {
        val x = Intent(activity, outraActivity)
        x.putStringArrayListExtra(chave, argsParaOutraActivity)
        startActivity(x)
    }

    private fun buscarProdutos(myCallback: (ArrayList<carrinho>) -> Unit) {
        val arrayProducts = ArrayList<carrinho>()

        dbFirestore.collection(uid)
            .get()
            .addOnCompleteListener {
                if(it.isSuccessful) {
                    for(document in it.result!!) {

                            val item = carrinho(
                                document.data.getValue(("imagem")).toString(),
                                document.data.getValue(("nome")).toString(),
                                document.data.getValue(("preco")).toString(),
                                document.data.getValue(("quantidade")).toString()
                            )

                            arrayProducts.add(item)

                    }
                    myCallback(arrayProducts)
                }
            }
    }








}
