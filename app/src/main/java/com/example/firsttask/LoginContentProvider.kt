package com.example.firsttask

import android.content.ContentProvider
import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.net.Uri

class LoginContentProvider : ContentProvider() {

    companion object
    {
        val PROVIDER_NAME="com.example.firsttask/LoginContentProvider"
        val URL="content://$PROVIDER_NAME/loginDB"
        val URI=Uri.parse(URL)

        val MAIL="Email"
        val NAME="NAME"
        val PASSWORD="Password"
        lateinit var dbLite: SQLiteDatabase

    }
    lateinit var db:SQLiteDatabase

    override fun onCreate(): Boolean {
        var helper=LoginDB(context)
        db=helper.writableDatabase
        dbLite = db;
        return  if(db==null) false else true

    }

    fun checkLogin(un : String, pass: String): Boolean
    {
         var cursor = db.rawQuery("select * from loginDB where Email=$un and Password=$pass", null)
        return cursor.count > 0


    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        db.insert("loginDB",null ,values)
        context?.contentResolver?.notifyChange(uri,null)
        return  uri
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?): Int {
        var count= db.delete("loginDB",selection,selectionArgs)
        context?.contentResolver?.notifyChange(uri,null)
        return count
    }

    override fun update(uri: Uri, values: ContentValues?, selection: String?, selectionArgs: Array<out String>?): Int {
      var count= db.update("loginDB",values,selection,selectionArgs)
        context?.contentResolver?.notifyChange(uri,null)
        return count
    }


    override fun query(uri: Uri, projection: Array<out String>?, selection: String?, selectionArgs: Array<out String>?, sortOrder: String?): Cursor? {
        return db.query("loginDB",projection,selection,selectionArgs,null,null,sortOrder)
    }

    override fun getType(uri: Uri): String? {
        return "vnd.android.cursor.dir/vnd.example.loginDB"
    }

    fun checkLoginDB() {

    }
}