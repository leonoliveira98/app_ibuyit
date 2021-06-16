package com.example.app_ibuyit.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.example.app_ibuyit.LoginActivity
import com.example.app_ibuyit.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.fragment_perfil.*


class PerfilFragment : Fragment() {

    private val mAuth : FirebaseAuth = FirebaseAuth.getInstance()
    private val dbFirestore : FirebaseFirestore = FirebaseFirestore.getInstance()

    lateinit var logout : Button
    lateinit var show_email : TextView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_perfil, container, false)

        val user = mAuth.currentUser
        val uid = user?.uid // FUNFA

        var email = user?.email.toString() //Esta a dar

        // Definir a base de dados
        //val db = FirebaseFirestore.getInstance()

        //Textviews
        show_email = view.findViewById(R.id.show_email)
        show_email.text = email


        logout = view.findViewById(R.id.btn_logout);
        logout.setOnClickListener {
            logout()
            activity?.finish()
        }

        return view
    }



    private fun logout(){
        Toast.makeText(activity,"User Logout Success", Toast.LENGTH_LONG).show()
        mAuth.signOut()
        executarOutraActivity(LoginActivity::class.java, "Valores", arrayListOf())

    }

    private fun executarOutraActivity(outraActivity: Class<*>, chave: String, argsParaOutraActivity: ArrayList<String>) {
        val x = Intent(activity, outraActivity)
        x.putStringArrayListExtra(chave, argsParaOutraActivity)
        startActivity(x)
    }
}


