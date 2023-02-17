package ru.asmelnikov.android.shopapp.presentation.profile

import androidx.annotation.DrawableRes
import com.airbnb.epoxy.TypedEpoxyController
import ru.asmelnikov.android.shopapp.R
import ru.asmelnikov.android.shopapp.databinding.EpoxyModelProfileSignedInItemBinding
import ru.asmelnikov.android.shopapp.databinding.EpoxyModelProfileSignedOutBinding
import ru.asmelnikov.android.shopapp.epoxy.ViewBindingKotlinModel
import ru.asmelnikov.android.shopapp.extensions.toPX
import ru.asmelnikov.android.shopapp.models.domain.User
import ru.asmelnikov.android.shopapp.presentation.cart.DividerEpoxyModel
import ru.asmelnikov.android.shopapp.utils.ResourceProvider
import javax.inject.Inject

class ProfileEpoxyController @Inject constructor(
    private val userProfilerGenerator: UserProfilerItemGenerator,
    private val profileUiAction: ProfileUiAction,
    private val resourceProvider: ResourceProvider
) : TypedEpoxyController<User?>() {

    override fun buildModels(data: User?) {
        if (data == null) {
            SignedOutEpoxyModel(onSignIn = { username, password ->
                profileUiAction.onSignIn(username, password)
            }).id("signed_out_state").addTo(this)
        } else {
            userProfilerGenerator.buildItems(user = data).forEach { profileItem ->
                SignedInItemEpoxyModel(
                    iconRes = profileItem.iconRes,
                    headerText = profileItem.headerText,
                    infoText = profileItem.infoText,
                    onCLick = { profileUiAction.onProfileItemSelected(profileItem.iconRes) }
                ).id(profileItem.iconRes).addTo(this)

                DividerEpoxyModel(
                    horizontalMargin = 20.toPX()
                ).id("divider_${profileItem.iconRes}").addTo(this)
            }

            SignedInItemEpoxyModel(
                iconRes = R.drawable.ic_round_logout_24,
                infoText = resourceProvider.getString(R.string.sign_out),
                headerText = resourceProvider.getString(R.string.logout),
                onCLick = { profileUiAction.onProfileItemSelected(R.drawable.ic_round_logout_24) }
            ).id(R.drawable.ic_round_logout_24).addTo(this)
        }
    }

    data class SignedOutEpoxyModel(
        val onSignIn: (String, String) -> Unit,
    ) : ViewBindingKotlinModel<EpoxyModelProfileSignedOutBinding>(R.layout.epoxy_model_profile_signed_out) {

        override fun EpoxyModelProfileSignedOutBinding.bind() {
            signInButton.setOnClickListener {
                onSignIn("donero", "ewedon")
            }
        }
    }

    data class SignedInItemEpoxyModel(
        @DrawableRes val iconRes: Int,
        val headerText: String,
        val infoText: String,
        val onCLick: () -> Unit
    ) : ViewBindingKotlinModel<EpoxyModelProfileSignedInItemBinding>(R.layout.epoxy_model_profile_signed_in_item) {

        override fun EpoxyModelProfileSignedInItemBinding.bind() {
            iconImageView.setImageResource(iconRes)
            headerTextView.text = headerText
            infoTextView.text = infoText
            root.setOnClickListener { onCLick() }
        }
    }
}