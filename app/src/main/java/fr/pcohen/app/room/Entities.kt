package fr.pcohen.app.room

import androidx.room.*

@Entity(tableName = ContestCaseMedia.TABLE_NAME)
data class ContestCaseMedia(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "document_file_path") val documentFilePath: String,
    @ColumnInfo(name = "layer_file_path") val layerFilePath: String?,
    @ColumnInfo(name = "duration") val durationInSecond: Long?,
    @ColumnInfo(name = "latitude") val latitude: Double,
    @ColumnInfo(name = "longitude") val longitude: Double,
    @ColumnInfo(name = "started_at") val startedAtEpochSecond: Long,
    @ColumnInfo(name = "title") val title: String? = null,
    @ColumnInfo(name = "description") val description: String? = null) {
    companion object {
        const val TABLE_NAME = "contest_case_media"
    }
}

