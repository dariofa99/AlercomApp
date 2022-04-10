package com.app.alercom.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.alercom.app.R
import com.alercom.app.data.model.Reference


class AffectsRangeSpinnerAdapter (context: Context, data:List<Reference>) :
    ArrayAdapter<Reference>(context,0,data) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        return initView(position,convertView,parent)

    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        return initView(position, convertView, parent)
    }


    private fun initView (position: Int, convertView: View?, parent: ViewGroup) : View {
        val affectRange = getItem(position)
        val view =  LayoutInflater.from(context).inflate(R.layout.item_range_spinner, parent, false)
        val text = view.findViewById<TextView>(R.id.rangeItem)
        text.text = affectRange?.referenceName
        return  view

    }



}
