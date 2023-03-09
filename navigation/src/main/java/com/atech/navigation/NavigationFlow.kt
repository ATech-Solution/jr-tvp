package com.atech.navigation

sealed class NavigationFlow {
    class HomeFlow(val title: String) : NavigationFlow()
    class ProfileFlow(val title: String) : NavigationFlow()
}