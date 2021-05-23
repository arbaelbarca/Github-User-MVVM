package com.arbaelbarca.github_user_project.presentation.ui.activity

import android.view.LayoutInflater
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.arbaelbarca.github_user_project.R
import com.arbaelbarca.github_user_project.basebinding.BaseActivityBinding
import com.arbaelbarca.github_user_project.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivityBinding<ActivityMainBinding>() {

    override val bindingInflater: (LayoutInflater) -> ActivityMainBinding
        get() = ActivityMainBinding::inflate

    override fun setupView(binding: ActivityMainBinding) {
        initial(binding)
    }

    private fun initial(binding: ActivityMainBinding) {
        val navController: NavController =
            Navigation.findNavController(this, R.id.my_nav_host_fragment)
        val appBarConfiguration =
            AppBarConfiguration(setOf(R.id.homeFragment, R.id.favoriteUserFragment))
        setupActionBarWithNavController(navController, appBarConfiguration)
        binding.navigationBottom.setupWithNavController(navController)

    }
}