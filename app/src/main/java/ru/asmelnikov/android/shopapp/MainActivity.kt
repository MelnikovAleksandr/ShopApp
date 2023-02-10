package ru.asmelnikov.android.shopapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import retrofit2.Response
import ru.asmelnikov.android.shopapp.databinding.ActivityMainBinding
import ru.asmelnikov.android.shopapp.hilt.ProductService
import ru.asmelnikov.android.shopapp.models.domain.Product
import ru.asmelnikov.android.shopapp.models.mapper.ProductMapper
import ru.asmelnikov.android.shopapp.models.network.NetworkProduct
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val viewModel: MainActivityViewModel by lazy {
        ViewModelProvider(this)[MainActivityViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val controller = ProductEpoxyController()
        binding.epoxyRecyclerView.setController(controller)
        controller.setData(emptyList())

        viewModel.store.stateFlow.map {
            it.products
        }.distinctUntilChanged().asLiveData().observe(this) { products ->
            controller.setData(products)
        }
        viewModel.refreshProducts()

    }
}
