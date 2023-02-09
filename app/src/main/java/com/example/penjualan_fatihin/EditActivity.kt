package com.example.penjualan_fatihin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.penjualan_fatihin.room.Penjualan
import com.example.penjualan_fatihin.room.tbBarang
import kotlinx.android.synthetic.main.activity_edit.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class EditActivity : AppCompatActivity() {
    val db by lazy { Penjualan(this) }
    private var tbBrgid : Int= 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)
        tombolperintah()
        setupView()
        Toast.makeText(this,tbBrgid.toString(), Toast.LENGTH_SHORT).show()
    }
    fun setupView() {
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        val intentType = intent.getIntExtra("intent_type", 0)
        when (intentType) {
            Constant.TYPE_CREATE -> {

            }
            Constant.TYPE_READ -> {
                btnSimpan.visibility = View.GONE
                btnUpdate.visibility = View.GONE
                EtId.visibility = View.GONE
                tampilsiswa()
            }
            Constant.TYPE_UPDATE -> {
                btnSimpan.visibility = View.GONE
                EtId.visibility = View.GONE
                tampilsiswa()
            }
        }
    }
    fun tombolperintah() {
        btnSimpan.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                db.tbBrgDao().addtbBarang(
                    tbBarang(
                        EtId.text.toString().toInt(),
                        EtNamabrg.text.toString(),
                        EtHarga.text.toString().toInt(),
                        EtStok.text.toString().toInt()
                    )
                )
                finish()
            }
        }
        btnUpdate.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                db.tbBrgDao().updatetbBarang(
                    tbBarang(tbBrgid,
                        EtNamabrg.text.toString(),
                        EtHarga.text.toString().toInt(),
                        EtStok.text.toString().toInt()
                    )
                )
                finish()
            }
        }
    }
    fun tampilsiswa(){
        tbBrgid = intent.getIntExtra("intent_id",0)
        CoroutineScope(Dispatchers.IO).launch{
            val barang = db.tbBrgDao().tampilId(tbBrgid).get(0)
            val brgId : String = barang.id_brg.toString()
            val hrg: String = barang.hrg_brg.toString()
            val stok : String = barang.stok.toString()
            EtNamabrg.setText(brgId)
            EtHarga.setText(hrg)
            EtStok.setText(stok)
            EtNamabrg.setText(barang.nm_brg)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}