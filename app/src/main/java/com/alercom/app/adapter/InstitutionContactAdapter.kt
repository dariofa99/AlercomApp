package com.alercom.app.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.alercom.app.R
import com.alercom.app.data.model.Institution
import com.alercom.app.databinding.ItemInstitutionContactBinding


class InstitutionContactAdapter (private val institutions:  List<Institution>):
    RecyclerView.Adapter<InstitutionContactViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InstitutionContactViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return InstitutionContactViewHolder(layoutInflater.inflate(R.layout.item_institution_contact,parent,false))

    }
    override fun onBindViewHolder(holder: InstitutionContactViewHolder, position: Int) {
        val item = institutions[position]
        holder.render(item)
    }
    override fun getItemCount(): Int = institutions.size

}

class InstitutionContactViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val binding = ItemInstitutionContactBinding.bind(view)
    fun render(institution: Institution){
        binding.textInstitution.text = institution.institutionName
        binding.textInstitutionPhone.text = institution.institutionPhone
    }
}