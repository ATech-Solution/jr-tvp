package com.atech.android

import android.view.MenuItem
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.navigation.NavDestination
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.atech.android.databinding.ActivityMainBinding
import com.atech.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>() {

    override val binding: ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    override val viewModel: MainViewModel by viewModels()

    override fun onInitViews() {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController

        navigator.navController = navController

        binding.navView.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            binding.navView.isVisible = mainVisibilityCheck(destination)
        }
    }

    private fun mainVisibilityCheck(destination: NavDestination): Boolean {
        return destination.id in listOf(
            R.id.homeFragment,
            R.id.profileFragment
        )
    }

    override fun onInitObservers() = Unit

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                if (!navigator.navController.popBackStack())
                    finish()
                else
                    navigator.navController.navigateUp()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}