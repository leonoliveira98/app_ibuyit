package com.example.app_ibuyit

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_registo.*

class RegistoActivity : AppCompatActivity() {

    private val mAuth : FirebaseAuth = FirebaseAuth.getInstance()
    private val dbFirestore : FirebaseFirestore = FirebaseFirestore.getInstance()

    private val fireStoreDoc = FirebaseFirestore.getInstance().collection("utilizadores")

    lateinit var bruh : String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registo)


        //Botao registo
        registo.setOnClickListener{

            val emailUser = email2.text.toString()
            val passwordUser = password2.text.toString()
            val confPassword = conf_password.text.toString()
            val nomeUser = nome.text.toString()

            //Registo auth
            if(confPassword == passwordUser){
                //Toast.makeText(this, "passes iguais", Toast.LENGTH_LONG).show()

                registo(emailUser, passwordUser, nomeUser)
                //Log.d("mariatchi","MATDADEA" +emailUser + passwordUser + nomeUser)
            }

        }


        btn_login.setOnClickListener{

            executarOutraActivity(LoginActivity::class.java, "Valores", arrayListOf(bruh))

        }
    }


    private fun registo(email: String, password: String, nome: String) {
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
            when {
                it.isSuccessful -> {

                    Toast.makeText(this, "Registo com sucesso", Toast.LENGTH_LONG).show()
                    insertUser(email, nome)
                    //startActivity(Intent(this, LoginActivity::class.java))
                }
                else -> {
                    Toast.makeText(this, "Registo falhado", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun insertUser(email: String, nome:String) {
        val userHash = HashMap<String, Any>()
        userHash["email"] = email
        userHash["nome"] = nome

        val userRef = fireStoreDoc
        val uid = mAuth.uid.toString()

        userRef.document(uid).set(userHash).addOnCompleteListener { it ->
            when {
                it.isSuccessful -> {
                    //Toast.makeText(this, "Sign Up Successful", Toast.LENGTH_LONG).show()
                    mAuth.signOut()
                    executarOutraActivity(LoginActivity::class.java, "Valores", arrayListOf("a"))
                }
                else -> {
                    //Toast.makeText(this, "Sign Up Unsuccessful", Toast.LENGTH_LONG).show()
                }
            }
        }
    }


    private fun executarOutraActivity(outraActivity: Class<*>, chave: String, argsParaOutraActivity: ArrayList<String>) {
        val x = Intent(this, outraActivity)
        x.putStringArrayListExtra(chave, argsParaOutraActivity)
        startActivity(x)
    }

}