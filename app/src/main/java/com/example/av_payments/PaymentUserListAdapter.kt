package com.example.av_payments

import android.content.Context
import android.content.Intent
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat.getDrawable
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_user_status.view.*
import kotlinx.android.synthetic.main.activity_selected_user_details.*
import kotlinx.android.synthetic.main.activity_selected_user_details.view.*
import kotlinx.android.synthetic.main.transfer_customdialogbox.view.*

class PaymentUserListAdapter(private val context: Context, private val items: ArrayList<UserModelClass>, private var etAmount: Long,
                             private var currentUser: String, private var currentUserProfile: ByteArray?, private var userBalance: Long,
                             private var currentUserID : Int) :
    RecyclerView.Adapter<PaymentUserListAdapter.ViewHolder>(){
    private val FADE_DURATION: Long = 1000

    class ViewHolder(view: View): RecyclerView.ViewHolder(view){

        val llUsers: LinearLayout = view.llUsers
        val ivProfile : ImageView = view.iv_profile
        var tvName: TextView = view.tv_name
        var userStatus: TextView = view.UserStatus
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return  ViewHolder(LayoutInflater.from(context).inflate
            (R.layout.item_user_status,parent,false)
        )
    }
    override fun onBindViewHolder(holder: PaymentUserListAdapter.ViewHolder, position: Int) {
        val model: UserModelClass = items[position]

        holder.userStatus.text = ""
        holder.ivProfile.setImageBitmap(DbBitmapUtility.getImage(model.profilePicture))
        holder.tvName.text = model.NameOfUser.toString()
        setFadeAnimation(holder.itemView)
        holder.llUsers.setOnClickListener {
            val intent = Intent(it.context,PaymentConfirmation::class.java)
            intent.putExtra("CurrentUserID",currentUserID)
            intent.putExtra("SelectedUserID",model.id)
            intent.putExtra("TransferAmount",etAmount)
            intent.putExtra("CurrentUser",currentUser)
            intent.putExtra("CurrentUserProfile",currentUserProfile)
            intent.putExtra("SelectedUser",holder.tvName.text.toString())
            intent.putExtra("SelectedUserProfile",model.profilePicture)
            intent.putExtra("CurrentUserBalance",userBalance)
            intent.putExtra("SelectedUserBalance",model.BalanceMoney)
            it.context.startActivity(intent)
        }

    }

    override fun getItemCount(): Int {
        return items.size
    }
    private fun setFadeAnimation(view: View) {
        val anim = AlphaAnimation(0.0f, 1.0f)
        anim.duration = FADE_DURATION
        view.startAnimation(anim)
    }

}