package ru.tashkent.stocks.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import ru.tashkent.stocks.other.Constants.DATABASE_NAME

@Database(entities = [Stock::class], version = 1)
abstract class StockDatabase : RoomDatabase() {

    abstract fun stockDao(): StockDao

    companion object {

        @Volatile
        private var instance: StockDatabase? = null

        fun getInstance(context: Context): StockDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).apply { instance = this }
            }
        }

        private fun buildDatabase(context: Context): StockDatabase {
            return Room.databaseBuilder(context, StockDatabase::class.java, DATABASE_NAME)
                .build()
        }
    }
}