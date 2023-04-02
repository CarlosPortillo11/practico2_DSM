package com.example.practico1_dsm

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import org.w3c.dom.Text
import kotlin.math.sign

class RegisterScreen : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var registerBtn:Button
    private lateinit var signBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_screen)

        auth = FirebaseAuth.getInstance()

        registerBtn = findViewById<Button>(R.id.registerBtn)

        registerBtn.setOnClickListener {
            val email = findViewById<EditText>(R.id.nameRegisterTxt).text.toString()
            val password = findViewById<EditText>(R.id.passwordRegisterTxt).text.toString()
            this.register(email, password)
        }

        signBtn = findViewById<Button>(R.id.goSignBtn)
        signBtn.setOnClickListener {
            this.goToLogin()
        }
    }

    private fun register(email: String, password: String){

        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener{ task ->
            if(task.isSuccessful){
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        }.addOnFailureListener{exception ->
            Toast.makeText(applicationContext, exception.localizedMessage, Toast.LENGTH_LONG).show()
        }
    }

    private fun goToLogin(){
        val intent = Intent(this, LoginScreen::class.java)
        startActivity(intent)
    }
}