package ru.asmelnikov.android.shopapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.asLiveData
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import com.airbnb.epoxy.Carousel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import ru.asmelnikov.android.shopapp.databinding.ActivityMainBinding
import ru.asmelnikov.android.shopapp.redux.ApplicationState
import ru.asmelnikov.android.shopapp.redux.Store
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    @Inject
    lateinit var store: Store<ApplicationState>

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val appBarConfiguration = AppBarConfiguration(
            topLevelDestinationIds = setOf(
                R.id.productListFragment,
                R.id.profileFragment,
                R.id.cartFragment,
                R.id.exploreFragment
            )
        )
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        setupActionBarWithNavController(navController, appBarConfiguration)

        NavigationUI.setupWithNavController(binding.bottomNavigationView, navController)

        Carousel.setDefaultGlobalSnapHelperFactory(null)

        store.stateFlow.map { it.inCartProducts.size }.distinctUntilChanged().asLiveData()
            .observe(this) { productsInCart ->
                binding.bottomNavigationView.getOrCreateBadge(R.id.cartFragment).apply {
                    number = productsInCart
                    isVisible = productsInCart > 0
                }
            }
    }
}
