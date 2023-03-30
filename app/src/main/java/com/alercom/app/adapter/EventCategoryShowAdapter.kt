package com.alercom.app.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.alercom.app.R
import com.alercom.app.data.model.EventType
import com.alercom.app.data.model.Reference
import com.alercom.app.databinding.ItemEventTypeBinding
import com.alercom.app.databinding.ItemEventTypeShowBinding


class EventCategoryShowAdapter(private val eventTypes: List<Reference>, private val onClickListener: (Reference) ->Unit )
    : RecyclerView.Adapter<EventTypeShowViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventTypeShowViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return EventTypeShowViewHolder(layoutInflater.inflate(R.layout.item_event_type_show,parent,false))
    }

    override fun onBindViewHolder(holder: EventTypeShowViewHolder, position: Int) {
        val item = eventTypes[position]
        holder.render(item,onClickListener)
    }

    override fun getItemCount(): Int = eventTypes.size

}


class EventTypeShowViewHolder(view:View) : RecyclerView.ViewHolder(view) {

    val binding = ItemEventTypeShowBinding.bind(view)
    fun render(eventType: Reference, onClickListener: (Reference) ->Unit){
        val stringMsg = "${eventType.referenceName}\n ${eventType.referenceDescription}";
        binding.lblDescription.text = eventType.referenceDescription
        binding.lblTitle.text = eventType.referenceName

    }



}