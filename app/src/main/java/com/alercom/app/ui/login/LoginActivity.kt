package com.alercom.app.ui.login


import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController

import com.alercom.app.MainActivity
import com.alercom.app.R
import com.alercom.app.network.Prefs

class LoginActivity : AppCompatActivity() {

companion object{

}
    lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        navController = findNavController(R.id.loginInitFragment)



    }
}

