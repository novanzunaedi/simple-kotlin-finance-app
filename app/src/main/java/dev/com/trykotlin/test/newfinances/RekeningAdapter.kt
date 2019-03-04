package dev.com.trykotlin.test.newfinances

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import dev.com.trykotlin.test.R
import dev.com.trykotlin.test.payment.Rekening

class RekeningAdapter (var context: Context, private var rekeningList: ArrayList<Rekening>) : BaseAdapter() {

    @SuppressLint("ViewHolder")
    override fun getView(position: Int, convertView: View?, viewGroup: ViewGroup?): View {

        val language = rekeningList.get(position)
        val row = (context as Activity).layoutInflater.inflate(R.layout.item_rekening_list, viewGroup, false)

        val tvName = row.findViewById(R.id.tvName) as TextView
        tvName.text = rekeningList.get(position).atasnama

        return row
     }

    override fun getItem(position: Int): Any {
        return rekeningList[position]
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getCount(): Int {
        return rekeningList.size
    }

}