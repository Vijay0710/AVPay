package com.example.av_payments

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_payment_status.*

class payment_status : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment_status)
        val page = intent.getStringExtra("PaymentPage")


        if(page.toString().toInt() == 0){
            Congratulations.visibility = View.GONE
            PaymentSuccess.visibility = View.GONE
            PaymentFailure.visibility = View.VISIBLE
            PaymentStatus.text = "Transaction Failed"
        }
        else {
            Congratulations.visibility = View.VISIBLE
            PaymentSuccess.visibility = View.VISIBLE
            PaymentFailure.visibility = View.GONE
            PaymentStatus.text = "Amount paid Successfully"
        }

        val handler = Handler()
        handler.postDelayed(
            Runnable {
                finish()
                val intent = Intent(this,MainActivity::class.java)
                startActivity(intent)
                },
            2500
        )

//        PaymentStatus.text = "Amount Paid Successfully"
//
//        val intent = Intent(this,MainActivity::class.java)
//        startActivity(intent)
    }
}
//24000
//75500