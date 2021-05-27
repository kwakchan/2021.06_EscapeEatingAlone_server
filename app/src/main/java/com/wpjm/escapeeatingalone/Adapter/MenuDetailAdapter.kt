package com.wpjm.escapeeatingalone.Activity

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.wpjm.escapeeatingalone.Model.MenuDetailModel
import com.wpjm.escapeeatingalone.R


class MenuDetailAdapter(val menuDetailList:ArrayList<MenuDetailModel>) :RecyclerView.Adapter<MenuDetailAdapter.CustomViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuDetailAdapter.CustomViewHolder {
        val view= LayoutInflater.from(parent.context).inflate(R.layout.item_menu_detail,parent,false)
        return CustomViewHolder(view)
    }

    override fun getItemCount(): Int {
        return menuDetailList.size
    }

    override fun onBindViewHolder(holder: MenuDetailAdapter.CustomViewHolder, position: Int) {
        holder.logo.setImageResource(menuDetailList.get(position).logo)
        holder.name.text=menuDetailList.get(position).name
        holder.explanation.text=menuDetailList.get(position).explanation

        // 리스트 눌렀을 때
        holder.itemView.setOnClickListener {
            var intent = Intent(holder.itemView?.context, PartyActivity::class.java)
            intent.putExtra("storeName", "${holder.name.text}")
            ContextCompat.startActivity(holder.itemView.context, intent, null)
        }
    }

    class CustomViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val logo=itemView.findViewById<ImageView>(R.id.menuDetail_imageView_logo)
        val name=itemView.findViewById<TextView>(R.id.menuDetail_textView_menuName)
        val explanation=itemView.findViewById<TextView>(R.id.menuDetail_textView_explanation)
    }
}
