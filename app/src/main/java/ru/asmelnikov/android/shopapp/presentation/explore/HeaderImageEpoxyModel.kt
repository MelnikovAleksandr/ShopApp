package ru.asmelnikov.android.shopapp.presentation.explore

import android.graphics.drawable.BitmapDrawable
import androidx.core.content.ContextCompat
import androidx.palette.graphics.Palette
import coil.ImageLoader
import coil.load
import coil.request.Disposable
import coil.request.ImageRequest
import ru.asmelnikov.android.shopapp.R
import ru.asmelnikov.android.shopapp.databinding.EpoxyModelHeaderImageBinding
import ru.asmelnikov.android.shopapp.epoxy.ViewBindingKotlinModel

data class HeaderImageEpoxyModel(
    val imageUrl: String,
): ViewBindingKotlinModel<EpoxyModelHeaderImageBinding>(R.layout.epoxy_model_header_image) {

    private var imageDisposable: Disposable? = null

    override fun EpoxyModelHeaderImageBinding.bind() {
        val loader = ImageLoader(root.context)
        val req = ImageRequest.Builder(root.context)
            .data(imageUrl)
            .allowHardware(false)
            .target { result ->
                val bitmap = (result as BitmapDrawable).bitmap
                val builder = Palette.Builder(bitmap)
                val defaultColor = ContextCompat.getColor(root.context, R.color.purple_50)
                root.setBackgroundColor(builder.generate().getMutedColor(defaultColor))
                cardView.setCardBackgroundColor(builder.generate().getDarkMutedColor(defaultColor))
                imageView.load(bitmap)
            }
            .build()

        imageDisposable = loader.enqueue(req)
    }

    override fun EpoxyModelHeaderImageBinding.unbind() {
        imageDisposable?.dispose()
        imageDisposable = null
    }
}
