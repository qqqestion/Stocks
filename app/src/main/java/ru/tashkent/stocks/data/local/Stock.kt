package ru.tashkent.stocks.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "stocks"
)
data class Stock(
    @PrimaryKey
    val symbol: String
)
