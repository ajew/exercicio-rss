package br.ufpe.cin.if710.rss

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.RecyclerView.*
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import kotlinx.android.synthetic.main.itemlista.view.*


class CustomArrayAdapter(var items: List<ItemRSS>, val context: Context) :
        RecyclerView.Adapter<CustomArrayAdapter.ViewHolder>(){

    class ViewHolder (itemView: View): RecyclerView.ViewHolder(itemView){
        fun bindView(item: ItemRSS) {
            itemView.item_titulo.text=item.title
            itemView.item_data.text=item.pubDate
        }
    }


    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items.get(position)
        holder.bindView(item)
        holder.itemView.setOnClickListener {
            val uri = Uri.parse(item.link)
            context.startActivity(Intent(Intent.ACTION_VIEW, uri))
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.itemlista,parent,false)
        return ViewHolder(view)
    }

}




