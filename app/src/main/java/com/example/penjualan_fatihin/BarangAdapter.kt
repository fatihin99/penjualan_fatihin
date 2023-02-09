package com.example.penjualan_fatihin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.penjualan_fatihin.room.tbBarang
import kotlinx.android.synthetic.main.activity_barang_adapter.view.*

class BarangAdapter (private val barang:ArrayList<tbBarang>,private val listener: OnAdapterListener)
    : RecyclerView.Adapter<BarangAdapter.BarangViewHolder>(){
    class BarangViewHolder(val view: View): RecyclerView.ViewHolder(view) {

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BarangViewHolder {
        return BarangViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.activity_barang_adapter,parent, false)
        )
    }
    override fun onBindViewHolder(holder: BarangViewHolder, position: Int) {
        val brg = barang[position]
        holder.view.TNama.text = brg.nm_brg
        holder.view.TNama.setOnClickListener{listener.onClik(brg)}
        holder.view.icon_edit.setOnClickListener{listener.onUpdate(brg)}
        holder.view.icon_delete.setOnClickListener{listener.onDelete(brg)}

    }

    override fun getItemCount() = barang.size

    fun setData(list: List<tbBarang>){
        barang.clear()
        barang.addAll(list)
        notifyDataSetChanged()
    }
    interface OnAdapterListener{
        fun onClik(tbBrg: tbBarang)
        fun onUpdate(tbBrg: tbBarang)
        fun onDelete(tbBrg: tbBarang)
    }

}
