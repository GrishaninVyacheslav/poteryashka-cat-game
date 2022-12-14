package io.github.poteryashkagame.poteryashka_admin.app.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.isVisible
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.androidx.AppNavigator
import io.github.grishaninvyacheslav.core_ui.presentation.BottomNavigation
import io.github.poteryashkagame.poteryashka_admin.R
import io.github.poteryashkagame.poteryashka_admin.databinding.ActivityMainBinding
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity(), BottomNavigation {
    companion object {
        val BOTTOM_NAV_VISIBILITY_ARG = "BOTTOM_NAV_VISIBILITY"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViews()
        if (savedInstanceState == null) {
            binding.bottomNavigation.selectedItemId = R.id.pets
        } else{
            isBottomNavigationVisible = savedInstanceState.getBoolean(BOTTOM_NAV_VISIBILITY_ARG)
        }
    }

    private fun initViews() = with(binding) {
        bottomNavigation.isVisible = isBottomNavigationVisible
        bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.rooms -> viewModel.navigateToRoomsList()
                R.id.pets -> viewModel.navigateToPetsList()
            }
            return@setOnItemSelectedListener true
        }
    }

    override var isBottomNavigationVisible: Boolean = false
        set(value) {
            field = value
            binding.bottomNavigation.isVisible = value
        }

    override fun onSaveInstanceState(savedInstanceState: Bundle) {
        super.onSaveInstanceState(savedInstanceState)
        savedInstanceState.putBoolean(BOTTOM_NAV_VISIBILITY_ARG, isBottomNavigationVisible)
    }

    override fun onResumeFragments() {
        super.onResumeFragments()
        navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        navigatorHolder.removeNavigator()
        super.onPause()
    }

    private val viewModel: MainViewModel by viewModel()
    private lateinit var binding: ActivityMainBinding
    private val navigatorHolder: NavigatorHolder by inject()
    private val navigator = AppNavigator(this, R.id.container)
}