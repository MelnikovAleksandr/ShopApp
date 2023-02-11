package ru.asmelnikov.android.shopapp.presentation.list

import androidx.core.content.ContextCompat
import ru.asmelnikov.android.shopapp.R
import ru.asmelnikov.android.shopapp.databinding.EpoxyModelProductFilterBinding
import ru.asmelnikov.android.shopapp.epoxy.ViewBindingKotlinModel
import ru.asmelnikov.android.shopapp.models.domain.Filter
import ru.asmelnikov.android.shopapp.models.ui.UiFilter

data class UiProductFilterEpoxyModel(
    val uiFilter: UiFilter,
    val onFilterSelected: (Filter) -> Unit
) : ViewBindingKotlinModel<EpoxyModelProductFilterBinding>(R.layout.epoxy_model_product_filter) {

    override fun EpoxyModelProductFilterBinding.bind() {
        root.setOnClickListener { onFilterSelected(uiFilter.filter) }
        filterNameTextView.text = uiFilter.filter.displayText

        val cardBackGroundColorRes = if (uiFilter.isSelected) {
            R.color.purple_500
        } else {
            R.color.purple_200
        }
        root.setCardBackgroundColor(ContextCompat.getColor(root.context, cardBackGroundColorRes))
    }

}