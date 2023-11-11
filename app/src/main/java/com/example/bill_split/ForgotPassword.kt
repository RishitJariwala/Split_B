package com.example.bill_split

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputEditText

@Suppress("DEPRECATION")
class ForgotPassword : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        val DBHELPER = DBHelper(this)

        setContentView(R.layout.activity_forgot_password)
        val backbtn = findViewById<FloatingActionButton>(R.id.BackBtn_fp)
        val email = findViewById<TextInputEditText>(R.id.et_email_fp)
        val password = findViewById<TextInputEditText>(R.id.et_password_fp)
        val conPassword = findViewById<TextInputEditText>(R.id.et_con_password_fp)
        val signup = findViewById<TextView>(R.id.tv_Signup_fp)
        val submitbutton = findViewById<Button>(R.id.btn_submit_fp)

        backbtn.setOnClickListener {
            val intent = Intent(this,LOGIN::class.java)
            startActivity(intent)
        }

        submitbutton.setOnClickListener {

            if (email.text!!.isEmpty())
            {
                Toast.makeText(this, "PLEASE ENTER THE EMAIL ID", Toast.LENGTH_SHORT).show()
            }
            else if (password.text!!.isEmpty())
            {
                Toast.makeText(this, "PLEASE ENTER THE PASSWORD", Toast.LENGTH_SHORT).show()
            }
            else if (conPassword.text!!.isEmpty())
            {
                Toast.makeText(this, "PLEASE ENTER THE CONFORM PASSWORD", Toast.LENGTH_SHORT).show()
            }
            else if (conPassword.text.toString() != password.text.toString())
            {
                Toast.makeText(this, "PASSWORD & CONFORM PASSWORD DOES NOT MATCH", Toast.LENGTH_SHORT).show()
            }
            else {
                try {
                    val cnt = DBHELPER.Chkfp(
                        email.text.toString(),
                    )
                    if (cnt>0){
                        val res = DBHELPER.Update_fp(
                            email.text.toString(),
                            password.text.toString()
                        )
                        if (res) {
                            val i = Intent(this, LOGIN::class.java)
                            startActivity(i)
                        }
                        else {
                            Toast.makeText(this, "Can't Update in Table", Toast.LENGTH_SHORT).show()
                        }
                    }
                    else{
                        Toast.makeText(this, "WRONG EMAIL ID PLEASE TRY AGAIN", Toast.LENGTH_SHORT).show()
                    }
                }catch (e:Exception){
                    Toast.makeText(this, "SOMETHING WENT WRONG PLEASE TRY AGAIN", Toast.LENGTH_SHORT).show()
                }
            }
        }

        signup.setOnClickListener {
            val i = Intent(this,Signup::class.java)
            startActivity(i)
        }
    }
}