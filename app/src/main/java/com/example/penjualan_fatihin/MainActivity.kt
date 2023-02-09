package com.example.penjualan_fatihin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.penjualan_fatihin.room.Penjualan
import com.example.penjualan_fatihin.room.tbBarang
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {
    lateinit var barangAdapter: BarangAdapter
    val db by lazy { Penjualan(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        halEdit()
        setupRecyclerView()
    }

    override fun onStart() {
        super.onStart()
        loadData()
    }
    fun loadData() {
        CoroutineScope(Dispatchers.IO).launch {
            val barang = db.tbBrgDao().tampilall()
            Log.d("MainActivity", "dbResponse:$barang")
            withContext(Dispatchers.Main) {
                barangAdapter.setData(barang)
            }
        }
    }
    private fun halEdit() {
        btnInput.setOnClickListener {
            intentEdit(0, Constant.TYPE_CREATE)
        }
    }

    fun intentEdit(tbBrgid: Int, intentType: Int) {
        startActivity(
            Intent(applicationContext, EditActivity::class.java)
                .putExtra("intent_id", tbBrgid)
                .putExtra("intent_type", intentType)
        )
    }
    fun setupRecyclerView() {
        barangAdapter = BarangAdapter(arrayListOf(), object : BarangAdapter.OnAdapterListener {
            override fun onClik(tbBrg: tbBarang) {
                intentEdit(tbBrg.id_brg, Constant.TYPE_READ)

            }

            override fun onUpdate(tbBrg: tbBarang) {
                intentEdit(tbBrg.id_brg, Constant.TYPE_UPDATE)
            }

            override fun onDelete(tbBrg: tbBarang) {
                deleteAlert(tbBrg)
            }
        })
        //id recyclerview
        listdatapenjualan.apply {
            layoutManager = LinearLayoutManager(applicationContext)
            adapter = barangAdapter
        }
    }
    private fun deleteAlert(tbBrg: tbBarang) {
        val dialog = AlertDialog.Builder(this)
        dialog.apply {
            setTitle("Konfirmasi Hapus")
            setMessage("Yakin Mau Hapus ${tbBrg.nm_brg}?")
            setNegativeButton("Batal") { dialogInterface, i ->
                dialogInterface.dismiss()
            }
            setPositiveButton("Hapus") { dialogInterface, i ->
                CoroutineScope(Dispatchers.IO).launch {
                    db.tbBrgDao().deltbBarang(tbBrg )
                    dialogInterface.dismiss()
                    loadData()
                }
            }
        }
        dialog.show()
    }
}