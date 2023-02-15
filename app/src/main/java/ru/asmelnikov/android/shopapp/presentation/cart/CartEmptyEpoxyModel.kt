package ru.asmelnikov.android.shopapp.presentation.cart

import android.view.View
import ru.asmelnikov.android.shopapp.R
import ru.asmelnikov.android.shopapp.databinding.EpoxyModelCartEmptyBinding
import ru.asmelnikov.android.shopapp.epoxy.ViewBindingKotlinModel

data class CartEmptyEpoxyModel(
    private val onClick: (View) -> Unit
) : ViewBindingKotlinModel<EpoxyModelCartEmptyBinding>(R.layout.epoxy_model_cart_empty) {

    override fun EpoxyModelCartEmptyBinding.bind() {
        button.setOnClickListener(onClick)
    }
}
