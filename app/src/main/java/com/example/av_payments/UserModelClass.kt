package com.example.av_payments

import android.widget.ImageView
import android.widget.TextView
import java.sql.Blob

class UserModelClass(var id: Int,
                     var NameOfUser: String,
                     var mail: String,
                     var BalanceMoney: Long,
                     var profilePicture: ByteArray){
    fun setID(){
        this.id = id
    }
    fun setImage(){
        this.profilePicture = profilePicture
    }
    fun setEmail(){
        this.mail = mail
    }
    fun setName(){
        this.NameOfUser = NameOfUser
    }
    fun setBalance(){
        this.BalanceMoney = BalanceMoney
    }
    fun getID(): Int{
        return id
    }
    fun getEmail(): String{
        return mail
    }
    fun getName(): String{
        return NameOfUser
    }
    fun getBalance(): Long{
        return BalanceMoney

    }
    fun getImage(): ByteArray {
        return profilePicture
    }

}