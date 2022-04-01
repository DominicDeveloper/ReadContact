package com.dominic.readingcontact.Adapters

import android.annotation.SuppressLint
import android.content.ContentResolver
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.dominic.readingcontact.ContactMe
import com.dominic.readingcontact.R
import kotlinx.android.synthetic.main.item_contact.view.*

class MyAdapter(val context: Context, val list:ArrayList<ContactMe>, val rvClick: RvClick) : RecyclerView.Adapter<MyAdapter.VH>() {

    inner class VH(var itemRv: View): RecyclerView.ViewHolder(itemRv){
        @SuppressLint("SetTextI18n")
        fun onBind(contactMe: ContactMe){
            itemRv.contact_name.text = contactMe.name
            itemRv.contact_number.text = contactMe.number
            itemRv.setOnClickListener {
                rvClick.onClick(contactMe,itemRv.findViewById(R.id.contact_card))
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return  VH(LayoutInflater.from(parent.context).inflate(R.layout.item_contact,parent,false))
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.onBind(list[position])
        if (list[position].photo != null){
            holder.itemRv.contact_image.setImageBitmap(list[position].photo)
        }else{
            holder.itemRv.contact_image.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.ic_baseline_perm_contact_calendar_24))
        }
    }

    override fun getItemCount(): Int {
        return  list.size
    }
    interface RvClick{
        fun onClick(contactMe: ContactMe,cardView: CardView)

    }

}