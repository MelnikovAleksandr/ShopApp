package ru.asmelnikov.android.shopapp

import androidx.core.view.isGone
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import coil.load
import ru.asmelnikov.android.shopapp.databinding.EpoxyModelItemBinding
import ru.asmelnikov.android.shopapp.epoxy.ViewBindingKotlinModel
import ru.asmelnikov.android.shopapp.models.domain.Product
import java.text.NumberFormat

data class ProductEpoxyModel(
    val product: Product?
) : ViewBindingKotlinModel<EpoxyModelItemBinding>(R.layout.epoxy_model_item) {

    private val currencyFormatter = NumberFormat.getCurrencyInstance()

    override fun EpoxyModelItemBinding.bind() {
        shimmerLayout.isVisible = product == null
        cardView.isInvisible = product == null

        product?.let { product ->
            shimmerLayout.stopShimmer()

            productTitleTextView.text = product.title
            productCategoryTextView.text = product.category
            productPriceTextView.text = currencyFormatter.format(product.price)

            imgProgressBar.isVisible = true
            productImageView.load(
                data = product.image
            ) {
                listener { request, result ->
                    imgProgressBar.isGone = true
                }
            }
        } ?: shimmerLayout.startShimmer()
    }
}
