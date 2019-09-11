package fr.pcohen.app.binding

import android.graphics.Bitmap
import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter

object BindingAdapters {
    @JvmStatic
    @BindingAdapter("visibleGone")
    fun showHide(view: View, show: Boolean) {
        view.visibility = if (show) View.VISIBLE else View.GONE
    }

    @JvmStatic
    @BindingAdapter("loadBitmap")
    fun loadBitmap(imageView: ImageView, bitmap: Bitmap) {
        imageView.setImageBitmap(bitmap)
    }
}