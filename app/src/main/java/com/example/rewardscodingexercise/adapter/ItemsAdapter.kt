package com.example.rewardscodingexercise.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

import com.example.rewardscodingexercise.R
import com.example.rewardscodingexercise.model.Items


class ItemsAdapter(
    // on below line we are passing variables
    // as course list and context
    private var itemsList: ArrayList<Items>,
) : RecyclerView.Adapter<ItemsAdapter.ItemsViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ItemsViewHolder {
        // this method is use to inflate the layout file which
        // we have created for our recycler view.
        // on below line we are inflating our layout file.
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.layout_item,
            parent, false
        )
        // at last we are returning our view
        // holder class with our item View File.
        return ItemsViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ItemsViewHolder, position: Int) {
        // on below line we are setting data to our text view.
        holder.itemID.text= "ID: "+itemsList[position].id.toString()
        holder.listID.text="List ID: "+itemsList[position].listId.toString()
        holder.itemName.text="Name: "+ itemsList[position].name

    }

    override fun getItemCount(): Int {
        // on below line we are
        // returning our size of our list
        return itemsList.size
    }

    class ItemsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // on below line we are initializing our
        // text views with Item ID, List ID and List Name.

        val itemID:TextView=itemView.findViewById(R.id.itemId)
        val listID:TextView=itemView.findViewById(R.id.listId)
        val itemName:TextView=itemView.findViewById(R.id.itemName)
    }
}