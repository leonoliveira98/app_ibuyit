package com.example.app_ibuyit.fragments

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.text.method.PasswordTransformationMethod
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import com.example.app_ibuyit.LoginActivity
import com.example.app_ibuyit.R
import com.example.app_ibuyit.produtoPromo
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_registo.*
import kotlinx.android.synthetic.main.email_costum_view.view.*
import kotlinx.android.synthetic.main.fragment_perfil.*
import kotlinx.android.synthetic.main.pass_costum_view.view.*


class PerfilFragment : Fragment() {

    private val mAuth : FirebaseAuth = FirebaseAuth.getInstance()
    private val dbFirestore : FirebaseFirestore = FirebaseFirestore.getInstance()

    lateinit var logout : ImageButton


    lateinit var editarEmail:Button
    lateinit var editarPassord:Button
    lateinit var textViewnome:TextView
    lateinit var textViewemail:TextView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view= inflater.inflate(R.layout.fragment_perfil, container, false)


        textViewnome = view.findViewById(R.id.textViewNome)
        textViewemail = view.findViewById(R.id.textViewEmail)
        editarEmail =view.findViewById(R.id.btnEditarEmail)
        editarPassord =view.findViewById(R.id.btnEditarPassword)



        editarEmail.setOnClickListener {
            showAlertEmail()
        }

        editarPassord.setOnClickListener {
            showAlertPass()
        }
        old()

        logout = view.findViewById(R.id.btn_logout)
        logout.setOnClickListener{
            logout()
            activity?.finish()
        }



        // Inflate the layout for this fragment
        return view


    }

    private fun old() {

        val shown = textViewnome
        val uuid = mAuth.currentUser?.uid
        dbFirestore.collection("utilizadores").document("$uuid").get().addOnSuccessListener { document ->
            if (document!= null){
                val nome = document.data?.get("nome")
                shown.text = "Nome: $nome"
            }

        }

        val show = textViewemail
        val user = mAuth.currentUser
        val userEmail = mAuth.currentUser?.email


        // buscar nome ao firestore do user
        if (user != null) {

            show.text = "Email: $userEmail"
        }
    }

    private fun showAlertEmail() {
        val inflater = layoutInflater
        val inflate_view = inflater.inflate(R.layout.email_costum_view, null)

        val userEmailEdt = inflate_view.userNewEmail


        val alertDialog = AlertDialog.Builder(activity)
        alertDialog.setTitle("Novo Email")
        alertDialog.setView(inflate_view)
        alertDialog.setCancelable(false)

        alertDialog.setNegativeButton("Cancel") { dialog, which ->
            Toast.makeText(activity, "Cancel", Toast.LENGTH_LONG).show()
        }

        alertDialog.setPositiveButton("Done") { dialog, which ->

            val user = FirebaseAuth.getInstance().currentUser
            val userEmail = userEmailEdt.text.toString()


            if (user != null) {
                if (!userEmail.isEmpty()) {

                    user.updateEmail(userEmail).addOnCompleteListener { task2 ->
                        if (task2.isSuccessful) {
                            val pessoa = HashMap<String, Any>()
                            pessoa["email"] = userEmail
                            dbFirestore.collection("Users").document(user.uid).update(pessoa)
                            Toast.makeText(activity, "Update email Success", Toast.LENGTH_LONG)
                                .show()
                            Log.d("Profile", "email update auth")
                            old()


                        } else {
                            Toast.makeText(
                                activity,
                                "Error email Update re-loggin try aggain",
                                Toast.LENGTH_LONG
                            ).show()
                            Log.d("Profile", "email erro auth")

                        }

                    }
                }
            }

            Log.d("Profile", "done botao")
        }

        val dialog = alertDialog.create()
        dialog.show()
    }

    private fun showAlertPass() {
        val inflater = layoutInflater
        val inflate_view = inflater.inflate(R.layout.pass_costum_view, null)

        val userPassEdt = inflate_view.userNewPass
        val userConfPassEdt = inflate_view.userConfPass


        val checkBoxTooggle = inflate_view.showPass

        checkBoxTooggle.setOnCheckedChangeListener { buttonView, isChecked ->
         if (!isChecked) {
              userPassEdt.transformationMethod = PasswordTransformationMethod.getInstance()
              userConfPassEdt.transformationMethod = PasswordTransformationMethod.getInstance()
         } else {
             userPassEdt.transformationMethod = null
              userConfPassEdt.transformationMethod = null
          }
         }

        val alertDialog = AlertDialog.Builder(activity)
        alertDialog.setTitle("Nova Password")
        alertDialog.setView(inflate_view)
        alertDialog.setCancelable(false)

        alertDialog.setNegativeButton("Cancel") { dialog, which ->
            Toast.makeText(activity, "Cancel", Toast.LENGTH_LONG).show()
        }

        alertDialog.setPositiveButton("Done") { dialog, which ->

            val user = FirebaseAuth.getInstance().currentUser
            val userPassword = userPassEdt.text.toString()
            val userConf = userConfPassEdt.text.toString()

            if (!userPassword.isEmpty() && !userConf.isEmpty()) {
                if (userConf == userPassword) {
                    user?.updatePassword(userPassword)?.addOnCompleteListener { task3 ->
                        if (task3.isSuccessful) {
                            Toast.makeText(activity, "Password atualizada com sucesso", Toast.LENGTH_LONG)
                                .show()
                            Log.d("Profile", "password auth")


                        } else {
                            Toast.makeText(activity, "Erro a atualizar Password", Toast.LENGTH_LONG)
                                .show()

                        }
                    }
                } else {
                    Toast.makeText(activity, "Passwords nao coincidem", Toast.LENGTH_LONG).show()
                    showAlertPass()
                }
            } else {
                Toast.makeText(activity, "Campos nao preenchidos", Toast.LENGTH_LONG).show()
                showAlertPass()
            }

            Log.d("Profile", "done botao")
        }

        val dialog = alertDialog.create()
        dialog.show()
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


