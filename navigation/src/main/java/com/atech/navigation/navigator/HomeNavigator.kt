package com.atech.navigation.navigator

import androidx.navigation.NavController

class HomeNavigator constructor(override val navController: NavController) : BaseNavigator {
    fun navigateUp() = navController.navigateUp()
}