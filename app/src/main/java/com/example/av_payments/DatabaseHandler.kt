package com.example.av_payments

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.DatabaseUtils
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHandler(context: Context):
      SQLiteOpenHelper(context,DATABASE_NAME,null,DATABASE_VERSION){

          companion object{
              private const val DATABASE_VERSION = 1
              private const val DATABASE_NAME = "UserDatabase"
              private const val TABLE_CONTACTS = "UserTable"
              private const val KEY_ID = "_id"
              private const val KEY_NAME = "name"
              private const val KEY_EMAIL = "email"
              private const val KEY_BALANCE = "balance"
              private const val KEY_IMAGE = "image"
          }


    override fun onCreate(db: SQLiteDatabase?) {
        val CREATE_CONTACTS_TABLE = ("CREATE TABLE "+ TABLE_CONTACTS + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT,"
                + KEY_EMAIL + " TEXT," + KEY_BALANCE + " REAL," + KEY_IMAGE +
                  " BLOB" + ")")
        db?.execSQL(CREATE_CONTACTS_TABLE)
    }
    fun addCustomer(customer: ArrayList<UserModelClass>) : Long{
        val db = this.writableDatabase
        var status : Long = -1

        for (i in 0..customer.size-1) {
            val content = ContentValues()
            content.put(KEY_ID, customer[i].id)
            content.put(KEY_NAME, customer[i].NameOfUser)

            content.put(KEY_EMAIL, customer[i].mail)
            content.put(KEY_BALANCE, customer[i].BalanceMoney)
            content.put(KEY_IMAGE, customer[i].profilePicture)
            status = db.insert(TABLE_CONTACTS,null,content)
            if(status <= -1){
                break
            }
        }
        db.close()
        return status
    }
    fun UpdateDetailsDB(Amount: Long, Name: String): Int {

        val DB = this.writableDatabase
        val contentvalues = ContentValues()
        contentvalues.put(KEY_BALANCE, Amount)
        val username = "\"$Name\""
        return DB.update(TABLE_CONTACTS, contentvalues, "$KEY_NAME = $username", null)
    }
    fun GetRowCount():Long{
        val db = this.readableDatabase
        var countRow = DatabaseUtils.queryNumEntries(db, TABLE_CONTACTS)
        db.close()
        return countRow
    }

    fun viewUserList(exceptionUserID : Int = 0): ArrayList<UserModelClass>{
        val userList : ArrayList<UserModelClass> = ArrayList<UserModelClass>()
        val userList2: ArrayList<UserModelClass> = userList
        val selectQuery = "SELECT * FROM $TABLE_CONTACTS"
        val db  = this.writableDatabase
        var cursor: Cursor? = null

        try{
            cursor = db.rawQuery(selectQuery,null)
        } catch (e: SQLiteException) {
            db.execSQL(selectQuery)
            return ArrayList()
        }

        var id: Int
        var name: String
        var email: String
        var balance: Long
        var profile: ByteArray


        if(cursor.moveToFirst()){
            do{
                id = cursor.getInt(cursor.getColumnIndex(KEY_ID))
                name = cursor.getString(cursor.getColumnIndex(KEY_NAME))
                email = cursor.getString(cursor.getColumnIndex(KEY_EMAIL))
                balance = cursor.getLong(cursor.getColumnIndex(KEY_BALANCE))
                profile = cursor.getBlob(cursor.getColumnIndex(KEY_IMAGE))
                if(id!=exceptionUserID){
                    val usertopay = UserModelClass(id, name, email, balance, profile)
                    userList2.add(usertopay)
                }
//                val user = UserModelClass(id = id, NameOfUser = name, mail = email, BalanceMoney = balance, profilePicture = profile)
//                userList.add(user)
            } while (cursor.moveToNext())
        }
        return userList2
    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        val upgradeUserTable = ( "DROP TABLE IF EXISTS $TABLE_CONTACTS")
        db?.execSQL(upgradeUserTable)
    }
}