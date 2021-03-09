package com.wnet.basic.adapter

import android.content.Context
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.wnet.basic.MainActivity
import com.wnet.basic.R
import com.wnet.basic.data.CardInfoModel
import com.wnet.basic.data.Data
import java.util.*

class ViewPagerAdapter(
    val list: ArrayList<Data>, val activity: MainActivity
) : RecyclerView.Adapter<ViewPagerAdapter.PagerViewHolder>() {

    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PagerViewHolder {
        val item = View.inflate(parent.getContext(), R.layout.item_layout, null)
        context = item.context
        val lp = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        item.layoutParams = lp
        return PagerViewHolder(item)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun addupdates(upgradeModel: List<Data>) {
        val initPosition = list.size
        list.clear()
        list.addAll(upgradeModel)
        notifyItemRangeInserted(initPosition, list.size)
    }

    override fun onBindViewHolder(holder: PagerViewHolder, position: Int) {
        holder.info.text = list.get(position).text
    }


    class PagerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val info = itemView.findViewById<TextView>(R.id.info)
    }

}