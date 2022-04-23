package de.example.challenge.flickrapp.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import de.example.challenge.flickrapp.adapters.recycler.AdapterItem

@Entity(tableName = "requests", indices = [Index(value = ["request"], unique = true)])
data class RequestHistoryModel(
    @ColumnInfo(name = "request") val request: String
) : AdapterItem {
    @PrimaryKey(autoGenerate = true)
    var key: Long = 0
    override fun getItemType(): Int {
        return HISTORY_TYPE
    }
}