package ru.asmelnikov.android.shopapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import coil.load
import dagger.hilt.android.AndroidEntryPoint
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import retrofit2.http.GET
import ru.asmelnikov.android.shopapp.databinding.ActivityMainBinding
import ru.asmelnikov.android.shopapp.hilt.ProductService
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var productService: ProductService

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        refreshData()
        setupListeners()

    }

    private fun setupListeners() {
        binding.cardView.setOnClickListener {
            binding.productDescriptionTextView.apply {
                isVisible = !isVisible
            }
        }

        binding.addToCartButton.setOnClickListener {
            binding.inCartView.apply {
                isVisible = !isVisible
            }
        }

        var isFavorite = false
        binding.favoriteImageView.setOnClickListener {
            val imageRes = if (isFavorite) {
                R.drawable.ic_baseline_favorite_border_24
            } else {
                R.drawable.ic_baseline_favorite_24
            }
            binding.favoriteImageView.setIconResource(imageRes)
            isFavorite = !isFavorite
        }
    }

    private fun refreshData() {
        lifecycleScope.launchWhenStarted {
            binding.imgProgressBar.isVisible = true
            val response = productService.getAllProducts()
            binding.productImageView.load(
                data = "https://fakestoreapi.com/img/81fPKd-2AYL._AC_SL1500_.jpg"
            ) {
                listener { request, result ->
                    binding.imgProgressBar.isGone = true
                }
            }
            Log.i("Data", response.body()!!.toString())
        }
    }
}