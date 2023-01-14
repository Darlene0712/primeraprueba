package com.example.primeraprueba

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class UserAdapter : RecyclerView.Adapter<UserAdapter.UsrViewHolder>(){
    private var stdList: ArrayList<UsrModel> = ArrayList()
    private var onClickItem: ((UsrModel) -> Unit)? = null
    private var onClickDeleteItem: ((UsrModel) -> Unit)? = null


    fun addItems (items: ArrayList<UsrModel>){
        this.stdList = items
        notifyDataSetChanged()
    }
    fun setOnClickItem(callback: (UsrModel) -> Unit) {
        this.onClickItem = callback
    }
    fun setOnClickDeleteItem(callback: (UsrModel) -> Unit){
        this.onClickDeleteItem = callback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = UsrViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.card_items_user , parent, false)
    )

    override fun onBindViewHolder(holder: UsrViewHolder, position: Int) {
        val std = stdList[position]
        holder.bindView(std)
        holder.itemView.setOnClickListener { onClickItem?.invoke(std)
        holder.btneleminar.setOnClickListener { onClickDeleteItem?.invoke(std) }}
    }

    override fun getItemCount(): Int {
        return stdList.size
    }
    class UsrViewHolder(var view: View) : RecyclerView.ViewHolder(view){
        private var id = view.findViewById<TextView>(R.id.tvId)
        private var usuario = view.findViewById<TextView>(R.id.tvUsuario)
        private var email = view.findViewById<TextView>(R.id.tvEmail)
        var btneleminar = view.findViewById<Button>(R.id.btneleminar)!!

        fun bindView(std: UsrModel) {
            id.text = std.id.toString()
            usuario.text = std.usuario
            email.text = std.email
        }
    }

}