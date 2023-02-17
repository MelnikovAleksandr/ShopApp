package ru.asmelnikov.android.shopapp.models.mapper

import ru.asmelnikov.android.shopapp.models.domain.Address
import ru.asmelnikov.android.shopapp.models.domain.Name
import ru.asmelnikov.android.shopapp.models.domain.User
import ru.asmelnikov.android.shopapp.models.network.NetworkUser
import java.util.*
import javax.inject.Inject

class UserMapper @Inject constructor() {

    fun buildFrom(networkUser: NetworkUser): User {
        return User(
            id = networkUser.id,
            name = Name(
                firstname = networkUser.name.firstname.capitalize(),
                lastname = networkUser.name.lastname.capitalize()
            ),
            username = networkUser.username,
            phoneNumber = networkUser.phone,
            address = Address(
                city = networkUser.address.city,
                number = networkUser.address.number,
                street = networkUser.address.street,
                zipcode = networkUser.address.zipcode,
                lat = networkUser.address.geolocation.lat,
                long = networkUser.address.geolocation.long
            )
        )
    }
}

private fun String.capitalize(): String {
    return this.replaceFirstChar {
        if (it.isLowerCase()) it.titlecase(
            Locale.getDefault()
        ) else it.toString()
    }
}