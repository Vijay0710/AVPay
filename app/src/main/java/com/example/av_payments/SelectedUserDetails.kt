package com.example.av_payments

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_selected_user_details.*
import kotlinx.android.synthetic.main.payment_send_list.*
import kotlinx.android.synthetic.main.transfer_customdialogbox.*


class SelectedUserDetails : AppCompatActivity() {

    var customerList2: ArrayList<UserModelClass>? = null
    private var PaymentAdapter: PaymentUserListAdapter? = null
    private var customDialog : Dialog? = null
    private var customDialog2 : Dialog? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_selected_user_details)
        val db = DatabaseHandler(this)

        setSupportActionBar(toolbar_user_activity)
        val actionBar = supportActionBar
        if(actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.title = ""
        }

        toolbar_user_activity.setNavigationOnClickListener {
            onBackPressed()
        }

        val id = intent.getIntExtra("ID",1)
        db.viewUserList(id)

        val name = intent.getStringExtra("Name")
        tv_userName.text = "Welcome " + name.toString()

        val mail = intent.getStringExtra("Mail")
        tv_UserMail.text = mail.toString()

        val balance: Long = intent.getLongExtra("Balance",1000)
        balance_user.text = "$ " + balance.toString()

        val image = intent.getByteArrayExtra("Profile")
        selected_user_image.setImageBitmap(DbBitmapUtility.getImage(image))

        if(balance <= 5000){
            trophies.background = resources.getDrawable(R.drawable.ic_silver_round)
            trophies.text = "SILVER"
            trophies.textSize = 20F
        }
        else{
            trophies.background = resources.getDrawable(R.drawable.ic_gold_round)
        }
        send_money.setOnClickListener {
            customDialogForBackButton(balance,id,name.toString(),image)
        }

    }
    private fun customDialogPaymentList(etAmount: Long, name: String, image: ByteArray?, balance: Long,userID: Int){
        customDialog2 = Dialog(this@SelectedUserDetails)
        customDialog2!!.window?.setBackgroundDrawableResource(android.R.color.transparent)
        customDialog2!!.setContentView(R.layout.payment_send_list)
        var rvPaymentList = customDialog2!!.findViewById<RecyclerView>(R.id.rvPaymentList)

        rvPaymentList.layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.VERTICAL,
            false
        )
        PaymentAdapter = PaymentUserListAdapter(this, customerList2!!,etAmount, name, image, balance,userID)
        rvPaymentList.adapter = PaymentAdapter
        customDialog2!!.show()

    }


    private fun customDialogForBackButton(balance_user: Long, id: Int, name: String,image: ByteArray?){

        customDialog = Dialog(this@SelectedUserDetails)
        customDialog!!.window?.setBackgroundDrawableResource(android.R.color.transparent)
        customDialog!!.setContentView(R.layout.transfer_customdialogbox)
        customDialog!!.btYes.setOnClickListener {
            if(customDialog!!.etAmount.text.toString().isEmpty() || customDialog!!.etAmount.text.toString().toLong()<=0 ){
                Toast.makeText(it.context,"Please enter Valid Amount",Toast.LENGTH_SHORT).show()
            }
            else if(customDialog!!.etAmount.text.toString().toLong() > balance_user){
                Toast.makeText(it.context,"You don't have sufficient Balance",Toast.LENGTH_SHORT).show()
            }
            else{
                customDialog!!.dismiss()
                val db = DatabaseHandler(this)
                customerList2 = db.viewUserList(id)
                customDialogPaymentList(customDialog!!.etAmount.text.toString().toLong(),name,image,balance_user,id)
            }
        }
        customDialog!!.show()
    }
}