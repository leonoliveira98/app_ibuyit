package com.example.app_ibuyit.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.app_ibuyit.R
import com.example.app_ibuyit.imagesAdapter
import com.example.app_ibuyit.produto
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.view.*
import kotlinx.android.synthetic.main.fragment_store.view.*


class StoreFragment : Fragment() {

    private val mAuth : FirebaseAuth = FirebaseAuth.getInstance()
    private val dbFirestore : FirebaseFirestore = FirebaseFirestore.getInstance()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_store, container, false)

        val user = mAuth.currentUser
        var email = user?.email.toString()


        buscarProdutos {

            view.recyclerStore.adapter = imagesAdapter(it)
            //view.recyclerId.layoutManager = LinearLayoutManager(activity)
            view.recyclerStore.layoutManager = GridLayoutManager(activity, 3)


            //recyclerId.setLayoutManager(GridLayoutManager(activity, 3))
        }



        return view
    }


    private fun executarOutraActivity(outraActivity: Class<*>, chave: String, argsParaOutraActivity: ArrayList<String>) {
        val x = Intent(activity, outraActivity)
        x.putStringArrayListExtra(chave, argsParaOutraActivity)
        startActivity(x)
    }

    private fun buscarProdutos(myCallback: (ArrayList<produto>) -> Unit) {
        val arrayProducts = ArrayList<produto>()

        dbFirestore.collection("produtos")
            .get()
            .addOnCompleteListener {
                if(it.isSuccessful) {
                    for(document in it.result!!) {

                        val item = produto(document.data.getValue(("imagem")).toString(),
                            document.data.getValue(("nome")).toString())

                        arrayProducts.add(item)

                    }
                    myCallback(arrayProducts)
                }
            }
    }

}
