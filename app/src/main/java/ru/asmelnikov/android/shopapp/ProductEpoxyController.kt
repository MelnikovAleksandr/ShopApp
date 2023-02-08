package ru.asmelnikov.android.shopapp

import com.airbnb.epoxy.TypedEpoxyController
import ru.asmelnikov.android.shopapp.models.domain.Product

class ProductEpoxyController : TypedEpoxyController<List<Product>>() {

    override fun buildModels(data: List<Product>?) {
        if (data == null || data.isEmpty()) {
            return
        }
        data.forEach {
            ProductEpoxyModel(it).id(it.id).addTo(this)
        }
    }
}