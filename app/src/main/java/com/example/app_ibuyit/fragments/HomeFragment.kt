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


class HomeFragment : Fragment() {

    private val mAuth : FirebaseAuth = FirebaseAuth.getInstance()
    private val dbFirestore : FirebaseFirestore = FirebaseFirestore.getInstance()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_home, container, false)

        val user = mAuth.currentUser
        var email = user?.email.toString()




        return view
    }




}
