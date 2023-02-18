package ru.asmelnikov.android.shopapp.redux.updater

import ru.asmelnikov.android.shopapp.redux.ApplicationState
import javax.inject.Inject

class UiProductQuantityUpdater @Inject constructor() {

    fun update(productId: Int, newQuantity: Int, currentState: ApplicationState): ApplicationState {
        if (newQuantity < 1) return currentState
        val currentQuantitiesMap = currentState.cartQuantitiesMap
        val newQuantitiesMap = currentQuantitiesMap + (productId to newQuantity)
        return currentState.copy(cartQuantitiesMap = newQuantitiesMap)
    }
}