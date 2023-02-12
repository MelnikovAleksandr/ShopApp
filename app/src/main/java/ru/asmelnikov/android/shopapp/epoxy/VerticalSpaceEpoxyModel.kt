package ru.asmelnikov.android.shopapp.epoxy

import android.view.ViewGroup
import androidx.annotation.Dimension
import androidx.annotation.Dimension.Companion.PX
import ru.asmelnikov.android.shopapp.R
import ru.asmelnikov.android.shopapp.databinding.EpoxyModelVerticalSpaceBinding

data class VerticalSpaceEpoxyModel(
    @Dimension(unit = PX) val height: Int
) : ViewBindingKotlinModel<EpoxyModelVerticalSpaceBinding>(R.layout.epoxy_model_vertical_space) {

    override fun EpoxyModelVerticalSpaceBinding.bind() {
        root.layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT, height
        )
    }
}