package com.example.app_ibuyit

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_login.*
import org.w3c.dom.Text

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


        //val esquecerPass = findViewById(R.id.btn_forgot_password)
        esquecerPass.setOnClickListener{
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Esqueceu a sua Password?")
            val view = layoutInflater.inflate(R.layout.dialog_forgot_password, null)
            val email = view.findViewById<EditText>(R.id.et_username)
            builder.setView(view)
            builder.setNegativeButton("Enviar", DialogInterface.OnClickListener{ _, _ ->
                forgotPassword(email)
            })
            builder.setPositiveButton("Fechar", DialogInterface.OnClickListener{ _, _ ->  })
            builder.show()
        }


        login.setOnClickListener{
            val email = email.text.toString()
            val password = password.text.toString()

            signIn(email,password)
        }

        btn_registo.setOnClickListener{

            var email = user?.email
            bruh = email.toString()
            executarOutraActivity(RegistoActivity::class.java, "Valores", arrayListOf(bruh))

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

    private fun forgotPassword(email : EditText){
        if(email.text.toString().isEmpty()){
            Toast.makeText(this,"Introduza um email valido",Toast.LENGTH_LONG).show()
            return
        }


        mAuth.sendPasswordResetEmail(email.text.toString())
            .addOnCompleteListener { task->
                if(task.isSuccessful){
                    Toast.makeText(this,"Email Enviado",Toast.LENGTH_LONG).show()
                }
            }
    }

}