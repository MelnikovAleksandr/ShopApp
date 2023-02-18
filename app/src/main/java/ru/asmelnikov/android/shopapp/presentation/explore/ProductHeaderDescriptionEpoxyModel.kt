package ru.asmelnikov.android.shopapp.presentation.explore

import ru.asmelnikov.android.shopapp.R
import ru.asmelnikov.android.shopapp.databinding.EpoxyModelExploreProductHeaderBinding
import ru.asmelnikov.android.shopapp.epoxy.ViewBindingKotlinModel
import ru.asmelnikov.android.shopapp.models.ui.UiProduct
import java.text.NumberFormat

data class ProductHeaderDescriptionEpoxyModel(
    val uiProduct: UiProduct
): ViewBindingKotlinModel<EpoxyModelExploreProductHeaderBinding>(
    R.layout.epoxy_model_explore_product_header
) {
    private val currencyFormatter = NumberFormat.getCurrencyInstance()

    override fun EpoxyModelExploreProductHeaderBinding.bind() {
        productTitleTextView.text = uiProduct.product.title
        productDescriptionTextView.text = uiProduct.product.description
        productCategoryTextView.text = uiProduct.product.category
        productPriceTextView.text = currencyFormatter.format(uiProduct.product.price)
    }
}