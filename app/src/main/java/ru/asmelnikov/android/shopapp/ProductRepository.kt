package ru.asmelnikov.android.shopapp

import ru.asmelnikov.android.shopapp.hilt.ProductService
import ru.asmelnikov.android.shopapp.models.domain.Product
import ru.asmelnikov.android.shopapp.models.mapper.ProductMapper
import javax.inject.Inject

class ProductRepository @Inject constructor(
    private val productService: ProductService,
    private val productMapper: ProductMapper
) {

    suspend fun fetchAllProducts(): List<Product> {
        return productService.getAllProducts().body()?.let { networkProducts ->
            networkProducts.map { productMapper.buildFrom(it) }
        } ?: emptyList()
    }
}