package com.example.app_ibuyit.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.view.*
import kotlinx.android.synthetic.main.fragment_store.view.*


class HomeFragment : Fragment() {

    private val mAuth : FirebaseAuth = FirebaseAuth.getInstance()
    private val dbFirestore : FirebaseFirestore = FirebaseFirestore.getInstance()

    val uid = mAuth.uid.toString()
    private val fireStoreDoc = FirebaseFirestore.getInstance().collection(uid)

    lateinit var logout : ImageButton

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        var view = inflater.inflate(R.layout.fragment_home, container, false)

        val user = mAuth.currentUser


        var email = user?.email.toString()


        buscarProdutos {

            view.recyclerPromo.adapter = imagesPromoAdapter(it)
            //view.recyclerId.layoutManager = LinearLayoutManager(activity)
            view.recyclerPromo.layoutManager = GridLayoutManager(activity, 3)

        }

        //fazer logout
        logout = view.findViewById(R.id.btn_logout)
        logout.setOnClickListener{
            logout()
            activity?.finish()
        }





        return view
    }


    private fun executarOutraActivity(outraActivity: Class<*>, chave: String, argsParaOutraActivity: ArrayList<String>) {
        val x = Intent(activity, outraActivity)
        x.putStringArrayListExtra(chave, argsParaOutraActivity)
        startActivity(x)
    }

    private fun buscarProdutos(myCallback: (ArrayList<produtoPromo>) -> Unit) {
        val arrayProducts = ArrayList<produtoPromo>()

        dbFirestore.collection("produtos")
            .get()
            .addOnCompleteListener {
                if(it.isSuccessful) {
                    for(document in it.result!!) {
                        if (document.getString("promocao").toString() == "true") {
                            val item = produtoPromo(
                                document.data.getValue(("imagem")).toString(),
                                document.data.getValue(("nome")).toString(),
                                document.data.getValue(("precoPromo")).toString())

                            arrayProducts.add(item)
                        }
                    }
                    myCallback(arrayProducts)
                }
            }
    }

    private fun logout(){
        Toast.makeText(activity,"User Logout Successfull", Toast.LENGTH_LONG).show()
        mAuth.signOut()
        executarOutraActivity(LoginActivity::class.java, "Valores", arrayListOf())

    }


}
