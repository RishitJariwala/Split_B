@file:Suppress("DEPRECATION")

package com.example.bill_split

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bill_split.model.UserData
import com.example.bill_split.view.UserAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton

class Admin : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        val DBHELPER = DBHelper(this)

        val BackBtn = findViewById<FloatingActionButton>(R.id.BackBtn)
        val Res_list = findViewById<RecyclerView>(R.id.List_of_user)

        val sharedPreferences = applicationContext.getSharedPreferences("my_pref", Context.MODE_PRIVATE)

        BackBtn.setOnClickListener {
            val editor = sharedPreferences.edit()
            editor.remove("username")
            editor.remove("password")
            editor.apply()
            val intent = Intent(this,LOGIN::class.java)
            startActivity(intent)
        }


        val res = DBHELPER.alldata
        val userlist:ArrayList<UserData> = ArrayList()
        StringBuffer()
        while (res.moveToNext()) {
            val row1 = res.getString(0)
            val row2 = res.getString(5)
            userlist.add(UserData(R.drawable.user_name_asset,row1,row2))
        }

        val userAdapter = UserAdapter(this,userlist)
        Res_list.adapter = userAdapter
        Res_list.layoutManager = LinearLayoutManager(this)
    }
}