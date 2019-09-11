package fr.pcohen.app.moshi

import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson
import org.threeten.bp.Instant
import org.threeten.bp.ZonedDateTime

class ThreeTenAdapters {

    @FromJson
    fun instantFromJson(value: String): Instant = ZonedDateTime.parse(value).toInstant()

    @ToJson
    fun instantToJson(value: Instant): String = value.toString()

}