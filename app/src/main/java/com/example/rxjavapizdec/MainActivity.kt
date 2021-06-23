package com.example.rxjavapizdec

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        val navHost=supportFragmentManager.findFragmentById(R.id.fragment_container_view) as NavHostFragment
        val controller= navHost.navController

    }

}