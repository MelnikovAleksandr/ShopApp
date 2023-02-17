package ru.asmelnikov.android.shopapp.redux

import ru.asmelnikov.android.shopapp.models.domain.Filter
import ru.asmelnikov.android.shopapp.models.domain.Product
import ru.asmelnikov.android.shopapp.models.domain.User

data class ApplicationState(
    val authState: AuthState = AuthState.UnAuth(),
    val products: List<Product> = emptyList(),
    val favoriteProductIds: Set<Int> = emptySet(),
    val expandedProductIds: Set<Int> = emptySet(),
    val productFilterInfo: ProductFilterInfo = ProductFilterInfo(),
    val inCartProducts: Set<Int> = emptySet(),
    val cartQuantitiesMap: Map<Int, Int> = emptyMap()

) {
    data class ProductFilterInfo(
        val filters: Set<Filter> = emptySet(),
        val selectedFilter: Filter? = null
    )

    sealed interface AuthState {
        data class Auth(val user: User) : AuthState
        data class UnAuth(val errorString: String? = null) : AuthState

        fun getGreetingMessage(): String {
            return if (this is Auth) {
                user.name.firstname
            } else {
                ""
            }
        }

        fun getEmail(): String {
            return if (this is Auth) {
                user.email
            } else {
                ""
            }
        }

    }
}
