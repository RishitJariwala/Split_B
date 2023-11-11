package com.example.bill_split

import android.annotation.SuppressLint
import android.content.Intent
import android.media.MediaCodec
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.textfield.TextInputEditText
import java.util.regex.Pattern

@Suppress("DEPRECATION")
class Signup : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        val DBHELPER = DBHelper(this)

        val firstname = findViewById<TextInputEditText>(R.id.et_first_name)
        val lastname = findViewById<TextInputEditText>(R.id.et_lastname)
        val number = findViewById<TextInputEditText>(R.id.et_contactnumber)
        val email = findViewById<TextInputEditText>(R.id.et_email)
        val username = findViewById<TextInputEditText>(R.id.et_username)
        val password = findViewById<TextInputEditText>(R.id.et_password)
        val conpassword = findViewById<TextInputEditText>(R.id.et_con_password)

        val signup = findViewById<Button>(R.id.btn_sign_in)
        val login = findViewById<TextView>(R.id.tv_Login)

        signup.setOnClickListener {

            if (firstname.text.toString().isEmpty())
            {
                Toast.makeText(this, "PLEASE ENTER THE FIRST NAME", Toast.LENGTH_SHORT).show()
            }
            else if (lastname.text.toString().isEmpty())
            {
                Toast.makeText(this, "PLEASE ENTER THE LASE NAME", Toast.LENGTH_SHORT).show()
            }
            else if (number.text.toString().isEmpty())
            {
                Toast.makeText(this, "PLEASE ENTER THE CONTECT NUMBER", Toast.LENGTH_SHORT).show()
            }
            else if (number.text.toString().length < 10)
            {
                Toast.makeText(this, "PLEASE ENTER THE CONTECT NUMBER", Toast.LENGTH_SHORT).show()
            }
            else if (email.text.toString().isEmpty())
            {
                Toast.makeText(this, "PLEASE ENTER THE EMAIL", Toast.LENGTH_SHORT).show()
            }
            else if (!isVatidEmait(email.text.toString()))
            {
                Toast.makeText(this, "PLEASE ENTER THE VALID EMAIL", Toast.LENGTH_SHORT).show()
            }
            else if (username.text.toString().isEmpty())
            {
                Toast.makeText(this, "PLEASE ENTER THE USER NAME", Toast.LENGTH_SHORT).show()
            }
            else if (password.text.toString().isEmpty())
            {
                Toast.makeText(this, "PLEASE ENTER THE PASSWORD", Toast.LENGTH_SHORT).show()
            }
            else if (conpassword.text.toString().isEmpty())
            {
                Toast.makeText(this, "PLEASE ENTER THE CONFORM PASSWORD", Toast.LENGTH_SHORT).show()
            }
            else if (conpassword.text.toString() != password.text.toString())
            {
                Toast.makeText(this, "PASSWORD & CONFORM PASSWORD DOES NOT MATCH", Toast.LENGTH_SHORT).show()
            }
            else
            {
                try {
                    DBHELPER.insertUser(
                        firstname.text.toString(),
                        lastname.text.toString(),
                        number.text.toString(),
                        email.text.toString(),
                        username.text.toString(),
                        password.text.toString(),
                    )
                    val i = Intent(this,LOGIN::class.java)
                    startActivity(i)
                }catch (e: Exception){
                    Toast.makeText(this, "DATA IS NOT INSERTED", Toast.LENGTH_SHORT).show()
                }
            }
        }

        login.setOnClickListener {
            val i2 = Intent(this,LOGIN::class.java)
            startActivity(i2)
        }
    }

    fun isVatidEmait(email: String): Boolean {
        val pattern = Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}\$")
        val matcher = pattern.matcher(email)
        return matcher.matches()
    }
}