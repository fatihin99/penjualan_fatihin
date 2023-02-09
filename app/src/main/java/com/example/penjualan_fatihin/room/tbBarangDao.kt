package com.example.penjualan_fatihin.room

import androidx.room.*

@Dao
interface tbBarangDao {
    @Insert
    fun addtbBarang(tbBrg: tbBarang)

    @Update
    fun updatetbBarang(tbBrg: tbBarang)

    @Delete
    fun deltbBarang(tbBrg: tbBarang)

    @Query("SELECT* FROM tbBarang")
    fun tampilall(): List<tbBarang>

    @Query( "SELECT * FROM tbBarang WHERE id_brg=:barang_id")
    fun tampilId (barang_id: Int) : List<tbBarang>
}