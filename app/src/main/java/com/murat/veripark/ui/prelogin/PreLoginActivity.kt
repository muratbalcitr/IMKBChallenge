package com.murat.veripark.ui.prelogin

import android.os.Bundle
import androidx.annotation.NavigationRes
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavGraph
import androidx.navigation.fragment.NavHostFragment
import com.murat.veripark.R
import com.murat.veripark.core.BaseActivity
import com.murat.veripark.databinding.ActivityPreloginBinding
import kotlinx.android.synthetic.main.activity_prelogin.*

class PreLoginActivity :
    BaseActivity<ActivityPreloginBinding, PreLoginViewModel>(
        R.layout.activity_prelogin
    ),
    NavController.OnDestinationChangedListener {

    override fun viewModelClass() = PreLoginViewModel::class

    @NavigationRes
    private val navigationGraph: Int = R.navigation.navigation_prelogin_graph

    private lateinit var navController: NavController
    private var currentDestination = PreLoginDestination.SPLASH
    private lateinit var navGraph: NavGraph
    override fun onInitDataBinding() {
        setNavigation()
    }

    private fun setNavigation() {
        val navHostFragment = prelogin_nav_host_fragment as NavHostFragment
        val graphInflater = navHostFragment.navController.navInflater
        navGraph = graphInflater.inflate(navigationGraph)
        navController = navHostFragment.navController
        navController.addOnDestinationChangedListener(this)
        navGraph.startDestination = PreLoginDestination.getAction(currentDestination)
        navController.graph = navGraph
    }

    private fun setCurrentDestination(destination: PreLoginDestination) {
        this.currentDestination = destination
    }

    override fun onDestinationChanged(
        controller: NavController,
        destination: NavDestination,
        arguments: Bundle?
    ) {
        setCurrentDestination(PreLoginDestination.find(destination.id))
    }

}