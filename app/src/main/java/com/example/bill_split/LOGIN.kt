package com.example.bill_split

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.textfield.TextInputEditText

@Suppress("DEPRECATION")
class LOGIN : AppCompatActivity() {

    @SuppressLint("ApplySharedPref")
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        val DBHELPER = DBHelper(this)

        val username = findViewById<TextInputEditText>(R.id.et_username_login)
        val password = findViewById<TextInputEditText>(R.id.et_password_login)
        val forgotPassword = findViewById<TextView>(R.id.tv_forgot_password_login)
        val signup = findViewById<TextView>(R.id.tv_signup_login)
        val loginbutton = findViewById<TextView>(R.id.btn_Log_in)

        val sharedPreferences = applicationContext.getSharedPreferences("my_pref", Context.MODE_PRIVATE)
        val user = sharedPreferences.getString("username",null)
        val pass = sharedPreferences.getString("password",null)

        if (user != null && pass != null){
            val cntx = DBHELPER.Chklogin_sp(
                user
            )
            val cntx1 = DBHELPER.AdminChklogin_sp(
                user
            )
            if (cntx>0){
                val i = Intent(this, Home_page::class.java)
                startActivity(i)
            }
            else if (cntx1>0){
                val i = Intent(this,Admin::class.java)
                startActivity(i)
            }
            else if(cntx==0 && cntx1==0){
                Toast.makeText(this, "WRONG ID OR PASSWORD PLEASE TRY AGAIN", Toast.LENGTH_SHORT).show()
            }
        }




        loginbutton.setOnClickListener {
            if (username.text!!.isEmpty())
            {
                Toast.makeText(this, "PLEASE ENTER USER NAME", Toast.LENGTH_SHORT).show()
            }
            else if (password.text!!.isEmpty())
            {
                Toast.makeText(this, "PLEASE ENTER PASSWORD", Toast.LENGTH_SHORT).show()
            }
            else
            {
                try {

                    val cnt = DBHELPER.Chklogin(
                        username.text.toString(),
                        password.text.toString()
                    )
                    val cnt1 = DBHELPER.AdminChklogin(
                        username.text.toString(),
                        password.text.toString()
                    )
                    if (cnt>0){
                        val editor = sharedPreferences.edit()
                        editor.putString("username", username.text.toString())
                        editor.putString("password", password.text.toString())
                        editor.apply()
                        val i = Intent(this, Home_page::class.java)
                        startActivity(i)
                    }
                    else if (cnt1>0){
                        val editor = sharedPreferences.edit()
                        editor.putString("username", username.text.toString())
                        editor.putString("password", password.text.toString())
                        editor.apply()
                        val i = Intent(this,Admin::class.java)
                        startActivity(i)

                    }
                    else if(cnt==0 && cnt1==0){

                        Toast.makeText(this, "WRONG ID OR PASSWORD PLEASE TRY AGAIN", Toast.LENGTH_SHORT).show()

                    }

                }catch (e: Exception){

                    Toast.makeText(this, "SOMETHING WENT WRONG TRY AGAIN", Toast.LENGTH_SHORT).show()

                }
            }
        }

        forgotPassword.setOnClickListener {
            val i = Intent(this,ForgotPassword::class.java)
            startActivity(i)
        }

        signup.setOnClickListener {
            val i2 = Intent(this,Signup::class.java)
            startActivity(i2)
        }
    }
}