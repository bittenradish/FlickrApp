package de.example.challenge.flickrapp.data.repository.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface RequestDao {

    @Insert
    fun add(request: RequestHistoryModel)

    @Query("SELECT * FROM REQUESTS")
    fun getAll(): LiveData<List<RequestHistoryModel>>

    @Query("DELETE FROM REQUESTS")
    fun clearDB()

    @Query("DELETE FROM REQUESTS WHERE request = :request")
    fun delete(request: String)

    @Query("UPDATE REQUESTS SET request = :request WHERE request =:request")
    fun update(request: String)


}