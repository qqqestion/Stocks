package ru.tashkent.stocks.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface StockDao {

    @Insert
    suspend fun insert(stock: Stock)

    @Delete
    suspend fun delete(stock: Stock)

    @Query("SELECT symbol FROM stocks")
    suspend fun getAll(): List<String>

    @Query("DELETE FROM stocks")
    suspend fun deleteAll()
}