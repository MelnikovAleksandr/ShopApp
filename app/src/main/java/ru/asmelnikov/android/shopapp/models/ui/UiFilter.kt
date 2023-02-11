package ru.asmelnikov.android.shopapp.models.ui

import ru.asmelnikov.android.shopapp.models.domain.Filter

data class UiFilter(
    val filter: Filter,
    val isSelected: Boolean
)
