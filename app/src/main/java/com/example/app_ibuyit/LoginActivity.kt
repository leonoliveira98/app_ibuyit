package com.example.app_ibuyit

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    private val mAuth : FirebaseAuth = FirebaseAuth.getInstance()
    private val dbFirestore : FirebaseFirestore = FirebaseFirestore.getInstance()

    lateinit var bruh : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val user = mAuth.currentUser
        if (user != null) {

            //Toast.makeText(this, "LOGADO",Toast.LENGTH_LONG).show()
            var email = user?.email
            bruh = email.toString()

            executarOutraActivity(MainActivity::class.java, "Valores", arrayListOf(bruh))
            this.finish()


        } else {
            // No user is signed in
            //Toast.makeText(this, "nao logado",Toast.LENGTH_LONG).show()

        }

        login.setOnClickListener{
            val email = email.text.toString()
            val password = password.text.toString()

            signIn(email,password)
        }

        updateUser()
    }


    private fun signIn(email: String, password: String){
        if(email.isNotEmpty() && password.isNotEmpty()){

            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener() {
                when {
                    it.isSuccessful -> {
                        Toast.makeText(this, "Bem-vindo, " + email, Toast.LENGTH_LONG).show()
                        updateUser()
                        clearInputs()
                        this.finish()
                        executarOutraActivity(
                            MainActivity::class.java,
                            "Valores",
                            arrayListOf(email)
                        )
                    }
                    else -> {
                        Toast.makeText(this, "Credenciais erradas.", Toast.LENGTH_LONG).show()
                    }
                }


            }
        } else {
            erros.text = "Credenciais erradas."
        }

    }

    private fun updateUser(){
        val user = mAuth.currentUser

        //utilizadorAtual.text = String.format("Utilizador Atual: %s", user?.email)
    }

    private fun logout(){
        mAuth.signOut()
        Toast.makeText(this,"Logout efetuado com sucesso.", Toast.LENGTH_LONG).show()
        updateUser()
    }

    private fun clearInputs(){
        email.text.clear()
        password.text.clear()
    }

    private fun executarOutraActivity(outraActivity: Class<*>, chave: String, argsParaOutraActivity: ArrayList<String>) {
        val x = Intent(this, outraActivity)
        x.putStringArrayListExtra(chave, argsParaOutraActivity)
        startActivity(x)
    }


}