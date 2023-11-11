package com.example.bill_split.view

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.example.bill_split.DBHelper
import com.example.bill_split.R
import com.example.bill_split.model.UserData

@Suppress("NAME_SHADOWING")
class UserAdapter(val c:Context, val userList: ArrayList<UserData>):RecyclerView.Adapter<UserAdapter.UserViewHolder>() {
    val DBHELPER = DBHelper(c)
    inner class UserViewHolder(v:View):RecyclerView.ViewHolder(v){
        //val v = LayoutInflater.from(c).inflate(R.layout.add_item,null)


        var fname:TextView
        var lname:TextView
     //   var del = v.findViewById<>()
        private var menus:ImageView
        init {
            fname = v.findViewById<TextView>(R.id.txt_fname)
            lname = v.findViewById<TextView>(R.id.txt_lname)
            menus = v.findViewById(R.id.Menu_ll)
            menus.setOnClickListener { popupMenu(it) }
        }

        @SuppressLint("NotifyDataSetChanged", "DiscouragedPrivateApi")
        private fun popupMenu(v: View) {
            val position = userList[adapterPosition]
          Log.d ("delete pos",adapterPosition.toString())
            val popupMenus = PopupMenu(c,v)
            popupMenus.inflate(R.menu.show_menu)
            popupMenus.setOnMenuItemClickListener {
                val res = DBHELPER.alldata

                when(it.itemId){

                    R.id.edittext->{

                        val v = LayoutInflater.from(c).inflate(R.layout.add_item,null)

                        val fname = v.findViewById<EditText>(R.id.et_Fname_AI)
                        val lname = v.findViewById<EditText>(R.id.et_Lname_AI)
                        val cno = v.findViewById<EditText>(R.id.et_Contect_AI)
                        val email = v.findViewById<EditText>(R.id.et_email_AI)
                        val uname = v.findViewById<EditText>(R.id.et_username_AI)
                        val pass = v.findViewById<EditText>(R.id.et_password_AI)

                        while (res.moveToNext()) {

                            if (res.getString(0) == position.userid) {
                                fname.setText(res.getString(1))
                                lname.setText(res.getString(2))
                                cno.setText(res.getString(3))
                                email.setText(res.getString(4))
                                uname.setText(res.getString(5))
                                pass.setText(res.getString(6))
                                break
                            }
                        }

                        AlertDialog.Builder(c)
                            .setView(v)
                            .setPositiveButton("Ok"){
                                dialog,_->
                                val data = DBHELPER.Update_Ad(
                                        position.userid.toInt(),
                                        fname.text.toString(),
                                        lname.text.toString(),
                                        cno.text.toString(),
                                        email.text.toString(),
                                        uname.text.toString(),
                                        pass.text.toString()
                                        )
                                position.username = fname.text.toString()

                                notifyDataSetChanged()
                                if (data) {
                                    Toast.makeText(c, "UPDATED", Toast.LENGTH_SHORT).show()
                                }
                            }
                            .setNegativeButton("cancel"){
                                dialog,_->
                                dialog.dismiss()
                            }
                            .create()
                            .show()
                        true
                    }
                    R.id.delete->{
                        AlertDialog.Builder(c)
                            .setTitle("Delete")
                            .setIcon(R.drawable.ic_warning)
                            .setMessage("Are you sure Delete this Information")
                            .setPositiveButton("Yes"){
                                dialog,_->
                                val data = DBHELPER.Delete_Ad(position.userid)
                                if (data) {
                                userList.remove(position)

                                notifyDataSetChanged()
                                }
                                else{
                                    Toast.makeText(c, "NOT DELETED", Toast.LENGTH_SHORT).show()
                                }
                                dialog.dismiss()
                            }
                            .setNegativeButton("No"){
                                dialog,_->
                                dialog.dismiss()
                            }
                            .create()
                            .show()
                        true
                    }
                    else-> true
                }
            }
            popupMenus.show()
            val popup = PopupMenu::class.java.getDeclaredField("mPopup")
            popup.isAccessible = true
            val  menu = popup.get(popupMenus)
            menu.javaClass.getDeclaredMethod("setForceShowIcon",Boolean::class.java).invoke(menu,true)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val v = inflater.inflate(R.layout.listitem,parent,false)
        return UserViewHolder(v)
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val newList = userList[position]
        holder.fname.text = newList.userid
        holder.lname.text = newList.username
    }
}

