package com.example.practico1_dsm

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class LoginScreen : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var loginBtn: Button
    private lateinit var createBtn: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_screen)

        auth = FirebaseAuth.getInstance()

        loginBtn = findViewById<Button>(R.id.loginGoBtn)

        loginBtn.setOnClickListener {
            val email = findViewById<EditText>(R.id.nameRegisterTxt).text.toString()
            val password = findViewById<EditText>(R.id.passwordRegisterTxt).text.toString()
            this.login(email, password);
        }

        createBtn = findViewById<Button>(R.id.goSignBtn)
        createBtn.setOnClickListener {
            this.goToRegister()
        }
    }

    private fun login(email: String, password: String){
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener{ task ->
            if (task.isSuccessful){
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        }.addOnFailureListener{exception ->
            Toast.makeText(applicationContext, exception.localizedMessage, Toast.LENGTH_LONG).show()
        }
    }

    private fun goToRegister(){
        val intent = Intent(this, RegisterScreen::class.java)
        startActivity(intent)
    }

}