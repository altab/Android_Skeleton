package fr.pcohen.app.viewmodels

import android.content.Context
import javax.inject.Inject

class ResourceProvider @Inject constructor (private var context: Context) {

    fun getString(resourceId: Int): String {
        return context.getString(resourceId)
    }

    fun getString(resourceId: Int, value: String): String {
        return context.getString(resourceId, value)
    }
}