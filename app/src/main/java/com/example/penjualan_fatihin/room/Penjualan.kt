package com.example.penjualan_fatihin.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [tbBarang:: class],
    version = 1)
abstract class Penjualan : RoomDatabase() {
abstract fun tbBrgDao(): tbBarangDao

companion object{
    @Volatile private var instance: Penjualan? = null
    private  val LOCK = Any()

    operator fun invoke(context: Context) = instance?: synchronized(LOCK){
        instance ?: buildDatabase(context).also{
            instance = it
        }
    }
    private fun buildDatabase(context: Context) = Room.databaseBuilder(
        context.applicationContext,
        Penjualan::class.java,
        "penjualan.db"
    ).build()
}
}