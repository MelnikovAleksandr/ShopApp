package ru.asmelnikov.android.shopapp.presentation.cart

import android.view.ViewGroup
import androidx.annotation.Dimension
import androidx.core.view.updateLayoutParams
import coil.load
import ru.asmelnikov.android.shopapp.R
import ru.asmelnikov.android.shopapp.databinding.EpoxyModelCartItemBinding
import ru.asmelnikov.android.shopapp.epoxy.ViewBindingKotlinModel
import ru.asmelnikov.android.shopapp.models.ui.UiProductCart

data class CartItemEpoxyModel(
    val uiProductInCart: UiProductCart,
    @Dimension(unit = Dimension.PX) private val horizontalMargin: Int,
    private val onFavoriteIconClicked: () -> Unit,
    private val onQuantityChanged: (Int) -> Unit
) : ViewBindingKotlinModel<EpoxyModelCartItemBinding>(R.layout.epoxy_model_cart_item) {

    override fun EpoxyModelCartItemBinding.bind() {
        swipeToDismissTextView.translationX = 0f
        // Setup text
        productTitleTextView.text = uiProductInCart.uiProduct.product.title

        //Favorite icon
        val imageRes = if (uiProductInCart.uiProduct.isFavorite) {
            R.drawable.ic_baseline_favorite_24
        } else {
            R.drawable.ic_baseline_favorite_border_24
        }
        favoriteImageView.setIconResource(imageRes)
        favoriteImageView.setOnClickListener {
            onFavoriteIconClicked()
        }

        //Load image
        productImageView.load(data = uiProductInCart.uiProduct.product.image)

        root.updateLayoutParams<ViewGroup.MarginLayoutParams> {
            setMargins(horizontalMargin, 0, horizontalMargin, 0)
        }

        quantityView.apply {
            quantityTextView.text = uiProductInCart.quantity.toString()
            minusImageView.setOnClickListener { onQuantityChanged(uiProductInCart.quantity - 1) }
            plusImageView.setOnClickListener { onQuantityChanged(uiProductInCart.quantity + 1) }
        }
    }
}
