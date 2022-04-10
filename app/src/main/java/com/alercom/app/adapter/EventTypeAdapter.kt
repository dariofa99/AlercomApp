package com.alercom.app.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.alercom.app.R
import com.alercom.app.data.model.EventType
import com.alercom.app.databinding.ItemEventTypeBinding


class EventTypeAdapter(private val eventTypes: List<EventType>, private val onClickListener: (EventType) ->Unit )
    : RecyclerView.Adapter<EventTypeViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventTypeViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return EventTypeViewHolder(layoutInflater.inflate(R.layout.item_event_type,parent,false))
    }

    override fun onBindViewHolder(holder: EventTypeViewHolder, position: Int) {
        val item = eventTypes[position]
        holder.render(item,onClickListener)
    }

    override fun getItemCount(): Int = eventTypes.size

}


class EventTypeViewHolder(view:View) : RecyclerView.ViewHolder(view) {

    val binding = ItemEventTypeBinding.bind(view)
    fun render(eventType: EventType, onClickListener: (EventType) ->Unit){
        binding.lblDescription.text = eventType.eventTypeDescription
        binding.lblTitle.text = eventType.eventTypeName

        itemView.setOnClickListener{
            onClickListener(eventType)
        }


        binding.iconRow.setOnClickListener {

            System.out.println(eventType.id)

        }
    }



}