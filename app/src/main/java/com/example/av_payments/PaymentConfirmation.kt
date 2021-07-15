package com.example.av_payments

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_payment_confirmation.*
import kotlinx.android.synthetic.main.activity_payment_status.*
import kotlinx.android.synthetic.main.transfer_customdialogbox.*
import kotlinx.android.synthetic.main.activity_selected_user_details.*

class PaymentConfirmation : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        var paymentPage: Int = 0
        //var creditScore: Int = 0
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment_confirmation)


        val name1 = intent.getStringExtra("CurrentUser")
        currentUserName.text = name1.toString()

        val name2 = intent.getStringExtra("SelectedUser")
        selectedUserName.text = name2.toString()
        val userID = intent.getIntExtra("CurrentUserID",100)

        val selectedUserID = intent.getIntExtra("SelectedUserID",100)

        var balance1 = intent.getLongExtra("CurrentUserBalance",1000)

        var balance2 = intent.getLongExtra("SelectedUserBalance",1000)
        val TransferAmount: Long = intent.getLongExtra("TransferAmount",1000)
        AmountToTransfer.text = "$ " + TransferAmount.toString()
        AmountToTransferSelectedUser.text = "$ " + TransferAmount.toString()

        val currentuserimage = intent.getByteArrayExtra("CurrentUserProfile")
        userProfile.setImageBitmap(DbBitmapUtility.getImage(currentuserimage))

        val selecteduserimage = intent.getByteArrayExtra("SelectedUserProfile")
        selectedUserProfile.setImageBitmap(DbBitmapUtility.getImage(selecteduserimage))

        var senderAmount: Long = balance1 - TransferAmount
        var receiverAmount: Long = balance2 + TransferAmount

        btnpay.setOnClickListener {
            paymentPage = 1
            var senderstatus: Int = 0
            var receiverstatus: Int = 0
            val db = DatabaseHandler(this)
            senderstatus = db.UpdateDetailsDB(senderAmount,name1.toString())
            if(senderstatus>=-1){
                balance1 -= TransferAmount
//                Toast.makeText(this,"Sender Transaction Success",Toast.LENGTH_SHORT).show()
                receiverstatus = db.UpdateDetailsDB(receiverAmount,name2.toString())
                if(receiverstatus>=-1){
                    balance2 += TransferAmount
//                    Toast.makeText(this,"Receiver Transaction Success",Toast.LENGTH_SHORT).show()
                    //creditScore ++
                }
                else{
//                    Toast.makeText(this,"Receiver Transaction Failed",Toast.LENGTH_SHORT).show()
                }
            }
            else{
//                Toast.makeText(this,"Sender Transaction Failed",Toast.LENGTH_SHORT).show()
            }
            val intent = Intent(this,payment_status::class.java)
            intent.putExtra("PaymentPage",paymentPage.toString())
            startActivity(intent)
            finish()
        }

        btnCancel.setOnClickListener {
            paymentPage = 0
            val intent = Intent(this,payment_status::class.java)
            intent.putExtra("PaymentPage",paymentPage.toString())
            startActivity(intent)
        }
    }
}