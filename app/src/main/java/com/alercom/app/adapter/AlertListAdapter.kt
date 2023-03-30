package com.alercom.app.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.alercom.app.R
import com.alercom.app.data.model.Alert
import com.alercom.app.databinding.ItemAlertListBinding


class AlertListAdapter(private val alerts: List<Alert>, private val onClickListener: (Alert) ->Unit )
    : RecyclerView.Adapter<AlertListViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlertListViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return AlertListViewHolder(layoutInflater.inflate(R.layout.item_alert_list,parent,false))
    }

    override fun onBindViewHolder(holder: AlertListViewHolder, position: Int) {
        val item = alerts[position]
        holder.render(item,onClickListener)
    }

    override fun getItemCount(): Int = alerts.size
}

class AlertListViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val binding = ItemAlertListBinding.bind(view)
    fun render(alert: Alert, onClickListener: (Alert) ->Unit){
        binding.lblUsername.text = "${alert.user?.name} ${alert.user?.lastname}"
        binding.lblLocation.text = "${alert.town?.townName}-${alert.town?.department?.referenceName}"

        binding.lblDescription.text = if(alert.eventDescription?.length!! > 80) {
            "${alert.eventDescription?.substring(1, 80)}..."
        }else{
            alert.eventDescription.toString()
        }
        binding.lblEventDate.text = "${alert.eventDate}\nEstado: ${alert.status?.referenceName}"
        itemView.setOnClickListener{
            onClickListener(alert)
        }


        binding.iconRow.setOnClickListener {
          //  onClickListener(alert)
            //System.out.println(eventType.id)

        }
    }



}