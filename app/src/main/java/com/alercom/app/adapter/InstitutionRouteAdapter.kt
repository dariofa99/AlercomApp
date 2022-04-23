package com.alercom.app.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.alercom.app.R
import com.alercom.app.data.model.InstitutionalRoute


import com.alercom.app.databinding.ItemInstitutionalRouteBinding

class InstitutionRouteAdapter (private val institutionalRoutes:  List<InstitutionalRoute>,
                               private val onClickListener: (InstitutionalRoute) ->Unit) :
    RecyclerView.Adapter<InstitutionRouteViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InstitutionRouteViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return InstitutionRouteViewHolder(layoutInflater.inflate(R.layout.item_institutional_route,parent,false))
    }

    override fun onBindViewHolder(holder: InstitutionRouteViewHolder, position: Int) {
        val item = institutionalRoutes[position]
        holder.render(item,onClickListener)
    }

    override fun getItemCount(): Int = institutionalRoutes.size
}

class InstitutionRouteViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    val binding = ItemInstitutionalRouteBinding.bind(view)
    fun render(institutionalRoute: InstitutionalRoute, onClickListener: (InstitutionalRoute) ->Unit){
        binding.textViewInstRoute.text = institutionalRoute.routeName
       itemView.setOnClickListener{
            //onClickListener(eventType)
        }
        binding.iconNext.setOnClickListener {
            onClickListener(institutionalRoute)
        }
    }



}