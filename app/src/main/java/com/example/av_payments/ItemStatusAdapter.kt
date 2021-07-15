package com.example.av_payments

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils.loadAnimation
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.animation.AnimationUtils
import kotlinx.android.synthetic.main.activity_selected_user_details.*
import kotlinx.android.synthetic.main.activity_selected_user_details.view.*
import kotlinx.android.synthetic.main.item_user_status.view.*


class ItemStatusAdapter(private val context: Context, private val items: ArrayList<UserModelClass>) :
        RecyclerView.Adapter<ItemStatusAdapter.ViewHolder>(){
    private val FADE_DURATION: Long = 1000
    private var lastPosition: Int = -1

    class ViewHolder(view: View): RecyclerView.ViewHolder(view){

        val llUsers: LinearLayout = view.llUsers
        val ivProfile : ImageView = view.iv_profile
        val tvName: TextView = view.tv_name
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return  ViewHolder(LayoutInflater.from(context).inflate
            (R.layout.item_user_status,parent,false)

        )
    }
    override fun onBindViewHolder(holder: ItemStatusAdapter.ViewHolder, position: Int) {

        setAnimation(holder.itemView, position);
        val model: UserModelClass = items[position]

        holder.ivProfile.setImageBitmap(DbBitmapUtility.getImage(model.profilePicture))
        holder.tvName.text = model.NameOfUser.toString()
        holder.llUsers.setOnClickListener {
            val intent = Intent(it.context,SelectedUserDetails::class.java)
            intent.putExtra("ID",model.id)
            intent.putExtra("Name",model.NameOfUser)
            intent.putExtra("Mail",model.mail)
            intent.putExtra("Balance",model.BalanceMoney)
            intent.putExtra("Profile",model.profilePicture)


//            intent.putExtra("Trophy",holder.trophy)

            it.context.startActivity(intent)
//            Animatoo.animateSlideUp(it.context)
        }
//        setFadeAnimation(holder.itemView)
    }

    private fun setAnimation(viewToAnimate: View, position: Int) {
        // If the bound view wasn't previously displayed on screen, it's animated
        if (position > lastPosition) {
            val animation: Animation = loadAnimation(context, R.anim.slide_down)
            viewToAnimate.startAnimation(animation)
            lastPosition = position
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }
//    private fun setFadeAnimation(view: View) {
//        val anim = AlphaAnimation(0.0f, 1.0f)
//        anim.duration = FADE_DURATION
//        view.startAnimation(anim)
//    }

}