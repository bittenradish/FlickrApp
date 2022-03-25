package de.example.challenge.flickrapp.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "requests")
data class RequestHistoryModel(
    @PrimaryKey
    val key: Long,
    val request: String
)