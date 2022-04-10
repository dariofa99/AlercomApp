package com.app.alercom.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import android.widget.ArrayAdapter
import android.widget.BaseAdapter
import android.widget.TextView
import com.alercom.app.R
import com.alercom.app.data.model.Reference


/*
class DeparmentsSpinnerAdapter(private val context: Context,private val data:List<Reference>) :
    BaseAdapter() {
    private var _binding: ItemDepartmentSpinnerBinding?  = null

    override fun getCount(): Int {
        return data.size
    }

    override fun getItem(position: Int): Any {
        return data?.get(position)
    }

    override fun getItemId(position: Int): Long {
        return data?.get(position).id?.toLong()!!
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view =  LayoutInflater.from(context).inflate(R.layout.item_department_spinner, parent, false)
        val department = getItem(position)
       // val view =  LayoutInflater.from(context).inflate(R.layout.item_department_spinner, parent, false)
        val text = view.findViewById<TextView>(R.id.departmentItem)
        text.text = "jajajaj"

        // val view = LayoutInflater.from(context).inflate(R.layout.item_department_spinner,parent,false
        return  view
    }


}*/

class DeparmentsSpinnerAdapter(context: Context, data:List<Reference>) :
    ArrayAdapter<Reference>(context,0,data) {



    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        //val view =  LayoutInflater.from(context).inflate(R.layout.item_department_spinner, parent, false)

        return initView(position,convertView,parent)

    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        return initView(position, convertView, parent)
    }


    private fun initView (position: Int, convertView: View?, parent: ViewGroup) : View {
       val department = getItem(position)
       val view =  LayoutInflater.from(context).inflate(R.layout.item_department_spinner, parent, false)
       val text = view.findViewById<TextView>(R.id.departmentItem)
        text.text = department?.referenceName
       return  view

    }


}