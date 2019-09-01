package com.example.movie.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.NavigationUI.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.movie.R
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController

    companion object{
        lateinit var mainApp:MainActivity
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mainApp=this
        //Getting the Navigation Controller
        navController = Navigation.findNavController(this, R.id.nav_host_fragment)

        bottomNavigation.setupWithNavController(navController)
        setupActionBarWithNavController(this, navController)


    }
    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(Navigation.findNavController(this,
            R.id.nav_host_fragment
        ), null)
    }


    fun hideBottomSheet(){
        bottomNavigation.visibility= View.GONE
    }

    fun showBottomSheet(){
        bottomNavigation.visibility= View.VISIBLE
    }
}
