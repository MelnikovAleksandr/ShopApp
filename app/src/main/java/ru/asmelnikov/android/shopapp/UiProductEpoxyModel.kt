package ru.asmelnikov.android.shopapp

import androidx.core.view.isGone
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import coil.load
import ru.asmelnikov.android.shopapp.databinding.EpoxyModelItemBinding
import ru.asmelnikov.android.shopapp.epoxy.ViewBindingKotlinModel
import ru.asmelnikov.android.shopapp.models.domain.Product
import ru.asmelnikov.android.shopapp.models.ui.UiProduct
import java.text.NumberFormat

data class UiProductEpoxyModel(
    val uiProduct: UiProduct?
) : ViewBindingKotlinModel<EpoxyModelItemBinding>(R.layout.epoxy_model_item) {

    private val currencyFormatter = NumberFormat.getCurrencyInstance()

    override fun EpoxyModelItemBinding.bind() {
        shimmerLayout.isVisible = uiProduct == null
        cardView.isInvisible = uiProduct == null

        uiProduct?.let { uiProduct ->
            shimmerLayout.stopShimmer()

            productTitleTextView.text = uiProduct.product.title
            productCategoryTextView.text = uiProduct.product.category
            productPriceTextView.text = currencyFormatter.format(uiProduct.product.price)

            val imageRes = if (uiProduct.isFavorite) {
                R.drawable.ic_baseline_favorite_24
            } else {
                R.drawable.ic_baseline_favorite_border_24
            }
            favoriteImageView.setIconResource(imageRes)

            imgProgressBar.isVisible = true
            productImageView.load(
                data = uiProduct.product.image
            ) {
                listener { request, result ->
                    imgProgressBar.isGone = true
                }
            }
        } ?: shimmerLayout.startShimmer()
    }
}
