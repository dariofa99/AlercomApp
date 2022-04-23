package com.alercom.app.resources

import androidx.appcompat.app.AppCompatActivity
import com.alercom.app.R

class MyTooblar {

    fun show(activity: AppCompatActivity,title:String,upButton:Boolean){
        activity.setSupportActionBar(activity.findViewById(R.id.toolbar))
        activity.supportActionBar?.title = title
        activity.supportActionBar?.setDisplayHomeAsUpEnabled(upButton)
    }
}