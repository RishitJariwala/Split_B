package com.example.bill_split

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast

class DBHelper(context: Context) : SQLiteOpenHelper(context, "SplitDB.db", null, 1) {

    private var ctx:Context = context

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL("CREATE TABLE USER(UID INTEGER PRIMARY KEY AUTOINCREMENT,FIRSTNAME TEXT,LASTNAME TEXT,CONTACT TEXT,EMAIL TEXT UNIQUE,USERNAME TEXT UNIQUE,PASSWORD TEXT)")
        db?.execSQL("CREATE TABLE ADMIN(AID INTEGER PRIMARY KEY AUTOINCREMENT,ADMINNAME TEXT UNIQUE,PASSWORD TEXT)")
        db?.execSQL("CREATE TABLE SPLIT(TID INTEGER PRIMARY KEY AUTOINCREMENT,DESCRIPTION TEXT,TOTAL_AMT TEXT,TOTAL_PERSON TEXT,SPLIT_AMT TEXT,USERNAME_1 TEXT,USERNAME_2 TEXT)")

        db?.execSQL("INSERT INTO ADMIN VALUES(1,'RISHITJARI','Rishit123')")

        db?.execSQL("INSERT INTO USER VALUES(1,'PREM','ARI','9897675767','prem@gmail.com','PremAri','123')")
        db?.execSQL("INSERT INTO USER VALUES(2,'UNNATI','JARIWALA','9997675767','unnati@gmail.com','UnnatiJari','123')")
        db?.execSQL("INSERT INTO USER VALUES(3,'NANCY','JARIWALA','9897675767','nancy@gmail.com','NancyJari','123')")
        db?.execSQL("INSERT INTO USER VALUES(4,'NAWAZ','ZUDA','9897675767','nawaz@gmail.com','NawazZuda','123')")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS USER")
        db?.execSQL("DROP TABLE IF EXISTS ADMIN")
        db?.execSQL("DROP TABLE IF EXISTS SPLIT")

        onCreate(db)
    }

    fun insertUser(firstname:String,lastname:String,contact:String,email:String,uname:String,pass:String){
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put("FIRSTNAME",firstname)
        contentValues.put("LASTNAME",lastname)
        contentValues.put("CONTACT",contact)
        contentValues.put("EMAIL",email)
        contentValues.put("USERNAME",uname)
        contentValues.put("PASSWORD",pass)
        val result = db.insert("USER",null,contentValues)
        if (result>0) {
            Toast.makeText(ctx, "$uname SIGNUP SUCCESSFULLY", Toast.LENGTH_SHORT).show()
        }
        else{
            Toast.makeText(ctx, "USERNAME OR EMAIL ALREADY TAKEN PLEASE TRY AGAIN", Toast.LENGTH_SHORT).show()
        }
    }

    fun insertSplit(dis:String,totamt:String,totper:String,spamt:String,uname_1:String,uname_2:String) :Boolean{
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put("DESCRIPTION",dis)
        contentValues.put("TOTAL_AMT",totamt)
        contentValues.put("TOTAL_PERSON",totper)
        contentValues.put("SPLIT_AMT",spamt)
        contentValues.put("USERNAME_1",uname_1)
        contentValues.put("USERNAME_2",uname_2)
        val result = db.insert("SPLIT",null,contentValues)
        return if (result>0) {
            Toast.makeText(ctx, "Bill Inserted Successfully", Toast.LENGTH_SHORT).show()
            true
        } else{
            Toast.makeText(ctx, "PROBLEM IN BILLING PLEASE TRY AGAIN", Toast.LENGTH_SHORT).show()
            false
        }
    }

    @SuppressLint("Recycle")
    fun AdminChklogin(aname: String, password: String): Int {
        val db = this.writableDatabase
        val res = db.rawQuery("SELECT * FROM ADMIN WHERE ADMINNAME = '$aname' and PASSWORD ='$password'",null)
        return res.count
    }

    @SuppressLint("Recycle")
    fun AdminChklogin_sp(aname: String): Int {
        val db = this.writableDatabase
        val res = db.rawQuery("SELECT * FROM ADMIN WHERE ADMINNAME = '$aname'",null)
        return res.count
    }

    @SuppressLint("Recycle")
    fun Chklogin_sp(username: String): Int {
        val db = this.writableDatabase
        val res = db.rawQuery("SELECT * FROM USER WHERE USERNAME = '$username'",null)
        return res.count
    }

    @SuppressLint("Recycle")
    fun Chklogin(username: String, password: String): Int {
        val db = this.writableDatabase
        val res = db.rawQuery("SELECT * FROM USER WHERE USERNAME = '$username' and PASSWORD ='$password'",null)
        return res.count
    }

    @SuppressLint("Recycle")
    fun Chkfp(email: String): Int {
        val db = this.writableDatabase
        val res = db.rawQuery("SELECT * FROM USER WHERE EMAIL = '$email'",null)
        return res.count
    }

    fun Update_fp(email: String,password: String): Boolean{
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put("EMAIL",email)
        contentValues.put("PASSWORD",password)
        val result = db.update("USER",contentValues,"EMAIL = ?", arrayOf(email))
        if (result>0) {
            Toast.makeText(ctx, "USER PASSWORD UPDATED SUCCESSFULLY", Toast.LENGTH_SHORT).show()
        }
        else{
            Toast.makeText(ctx, "PROBLEM IN FORGOT PASSWORD PLEASE TRY AGAIN", Toast.LENGTH_SHORT).show()
        }
        return true
    }

    fun Update_cp(uname: String?,password: String): Boolean{
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put("PASSWORD",password)
        val result = db.update("USER",contentValues,"USERNAME = ?", arrayOf(uname))
        if (result>0) {
            Toast.makeText(ctx, "$uname's PASSWORD UPDATED SUCCESSFULLY", Toast.LENGTH_SHORT).show()
        }
        else{
            Toast.makeText(ctx, "PROBLEM IN FORGOT PASSWORD PLEASE TRY AGAIN", Toast.LENGTH_SHORT).show()
        }
        return true
    }

    fun Update_Ac(firstname:String, lastname:String, contact:String, email:String, uname: String?): Boolean{
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put("FIRSTNAME",firstname)
        contentValues.put("LASTNAME",lastname)
        contentValues.put("CONTACT",contact)
        contentValues.put("EMAIL",email)
        contentValues.put("USERNAME",uname)

        val result = db.update("USER",contentValues,"USERNAME = ?", arrayOf(uname))
        if (result>0) {
            Toast.makeText(ctx, "$uname's DATA UPDATED SUCCESSFULLY", Toast.LENGTH_SHORT).show()
        }
        else{
            Toast.makeText(ctx, "PROBLEM IN UPDATE PLEASE TRY AGAIN", Toast.LENGTH_SHORT).show()
        }
        return true
    }

    fun Update_Ad(id:Int,firstname:String,lastname:String,contact:String,email:String,uname:String,pass:String): Boolean{
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put("FIRSTNAME",firstname)
        contentValues.put("LASTNAME",lastname)
        contentValues.put("CONTACT",contact)
        contentValues.put("EMAIL",email)
        contentValues.put("USERNAME",uname)
        contentValues.put("PASSWORD",pass)
        val result = db.update("USER",contentValues,"UID = ?", arrayOf(id.toString()))
        if (result>0) {
            Toast.makeText(ctx, "$uname's DATA UPDATED SUCCESSFULLY", Toast.LENGTH_SHORT).show()
        }
        else{
            Toast.makeText(ctx, "PROBLEM IN UPDATE PLEASE TRY AGAIN", Toast.LENGTH_SHORT).show()
        }
        return true
    }

    fun Delete_Ad(uid:String): Boolean{
        val db = this.writableDatabase
        val result = db.delete("USER","UID = ?", arrayOf(uid))
        if (result>0) {
            Toast.makeText(ctx, "USER DATA IS DELETED", Toast.LENGTH_SHORT).show()
        }
        else{
            Toast.makeText(ctx, "PROBLEM IN DELETING PLEASE TRY AGAIN", Toast.LENGTH_SHORT).show()
        }
        return true
    }
    fun Delete_Ac(uname:String): Boolean{
        val db = this.writableDatabase
        val result1 = db.delete("SPLIT","USERNAME_1 = ?", arrayOf(uname))
        val result = db.delete("USER","USERNAME = ?", arrayOf(uname))
        if (result>0 && result1>0) {
            Toast.makeText(ctx, "$uname's ACCOUNT IS DELETED", Toast.LENGTH_SHORT).show()
        }
        else{
            Toast.makeText(ctx, "PROBLEM IN DELETING PLEASE TRY AGAIN", Toast.LENGTH_SHORT).show()
        }
        return true
    }

    fun allAccountdata(uname: String):  Cursor{
            val db = this.readableDatabase
            return db.rawQuery("select * from USER WHERE USERNAME = '$uname'", null)
        }

    fun split_check() : Cursor {

        val db = this.readableDatabase
        val res = db.rawQuery("select * from SPLIT", null)

        return res
    }

    fun split_check_owes(uname: String?) : Cursor {

        val db = this.readableDatabase
        val res = db.rawQuery("select * from SPLIT WHERE USERNAME_1 = '$uname'", null)

        return res
    }
    fun split_check_borrowed(uname: String?) : Cursor {

        val db = this.readableDatabase
        val res = db.rawQuery("select * from SPLIT WHERE USERNAME_2 = '$uname'", null)

        return res
    }

    fun pass_check(uname: String?,pass: String?) : Cursor {

        val db = this.readableDatabase
        val res = db.rawQuery("select * from USER WHERE USERNAME = '$uname' AND PASSWORD = '$pass'", null)

        return res
    }

    val alldata : Cursor
        get() {
            val db = this.readableDatabase
            return db.rawQuery("select * from USER", null)
        }
}
