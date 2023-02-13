package ru.asmelnikov.android.shopapp.presentation.cart

import android.view.ViewGroup
import androidx.annotation.Dimension
import androidx.core.view.updateLayoutParams
import coil.load
import com.airbnb.epoxy.TypedEpoxyController
import ru.asmelnikov.android.shopapp.R
import ru.asmelnikov.android.shopapp.databinding.EpoxyModelCartItemBinding
import ru.asmelnikov.android.shopapp.epoxy.VerticalSpaceEpoxyModel
import ru.asmelnikov.android.shopapp.epoxy.ViewBindingKotlinModel
import ru.asmelnikov.android.shopapp.extensions.toPX
import ru.asmelnikov.android.shopapp.models.ui.UiProduct

class CartFragmentEpoxyController : TypedEpoxyController<CartFragment.UiState>() {

    override fun buildModels(data: CartFragment.UiState?) {
        when (data) {
            null, is CartFragment.UiState.Empty -> {
                CartEmptyEpoxyModel(onClick = {

                }).id("empty_state").addTo(this)
            }
            is CartFragment.UiState.NonEmpty -> {
                data.products.forEachIndexed { index, uiProduct ->

                    addVerticalStyling(index)

                    CartItemEpoxyModel(
                        uiProduct = uiProduct,
                        horizontalMargin = 16.toPX(),
                        onFavoriteIconClicked = {

                        },
                        onDeleteClicked = {

                        }
                    ).id(uiProduct.product.id).addTo(this)
                }
            }
        }
    }

    private fun addVerticalStyling(index: Int) {
        VerticalSpaceEpoxyModel(8.toPX()).id("top_space_$index").addTo(this)

        if (index != 0) {
            DividerEpoxyModel(horizontalMargin = 16.toPX()).id("divider_$index")
                .addTo(this)
        }

        VerticalSpaceEpoxyModel(8.toPX()).id("bottom_space_$index").addTo(this)
    }

    data class CartItemEpoxyModel(
        val uiProduct: UiProduct,
        @Dimension(unit = Dimension.PX) private val horizontalMargin: Int,
        val onFavoriteIconClicked: () -> Unit,
        val onDeleteClicked: () -> Unit
    ) : ViewBindingKotlinModel<EpoxyModelCartItemBinding>(R.layout.epoxy_model_cart_item) {

        override fun EpoxyModelCartItemBinding.bind() {

            // Setup text
            productTitleTextView.text = uiProduct.product.title

            //Favorite icon
            val imageRes = if (uiProduct.isFavorite) {
                R.drawable.ic_baseline_favorite_24
            } else {
                R.drawable.ic_baseline_favorite_border_24
            }
            favoriteImageView.setIconResource(imageRes)
            favoriteImageView.setOnClickListener {
                onFavoriteIconClicked()
            }

            deleteCart.setOnClickListener { onDeleteClicked() }

            //Load image
            productImageView.load(data = uiProduct.product.image)

            root.updateLayoutParams<ViewGroup.MarginLayoutParams> {
                setMargins(horizontalMargin, 0, horizontalMargin, 0)
            }

            quantityView.apply {
                quantityTextView.text = 9.toString()
                minusImageView.setOnClickListener { }
                plusImageView.setOnClickListener { }
            }
        }
    }
}