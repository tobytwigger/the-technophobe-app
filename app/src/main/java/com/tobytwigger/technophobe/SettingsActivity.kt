package com.tobytwigger.technophobe

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.tobytwigger.technophobe.databinding.ActivityMainBinding

class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        @Suppress("UNUSED_VARIABLE")
        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.settings)

        val navController = this.findNavController(R.id.navhostFragment)
        NavigationUI.setupActionBarWithNavController(this, navController)

    }

}