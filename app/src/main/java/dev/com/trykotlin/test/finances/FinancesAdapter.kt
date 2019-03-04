package dev.com.trykotlin.test.finances

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dev.com.trykotlin.test.R
import dev.com.trykotlin.test.data.DbFinance
import dev.com.trykotlin.test.newfinances.NewFinanceActivity
import kotlinx.android.synthetic.main.item_finance.view.*

class FinancesAdapter(val mContext: Context, val mFinances : ArrayList<Finance>):
        RecyclerView.Adapter<FinancesAdapter.ViewHolder>(){

    val dbf = DbFinance(mContext)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(mContext).inflate(R.layout.item_finance, parent, false)
        return ViewHolder(view)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val currentPhrase = mFinances.get(position)
        holder.let {
            it.bindView(currentPhrase)
        }
    }


    override fun getItemCount(): Int {
        return mFinances.size
    }


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener{

        val dari = itemView.tvDari
        val keterangan = itemView.tvKeterangan
        val jumlah = itemView.tvJumlah
        val tanggal = itemView.tvDate
        val nomor = itemView.tvNomor
        val payby = itemView.tvPayby
        val btEdit = itemView.btFinanceEdit
        val btDel = itemView.btFinanceDelete


        init {
            itemView.setOnClickListener(this)
            btEdit.setOnClickListener(this)
            btDel.setOnClickListener(this)
        }

        override fun onClick(v: View?) {

            val currentFinance = mFinances[adapterPosition]
            val selectionArgs = arrayOf(currentFinance.finId.toString())
            when(v){

                btDel -> {
                    dbf.delete("ID=?",selectionArgs)
                    notifyItemRemoved(adapterPosition)

                }

                btEdit ->{

                    val intent = Intent(mContext, NewFinanceActivity::class.java)
                    intent.putExtra("id",currentFinance.finId)
                    intent.putExtra("dari",currentFinance.dari)
                    intent.putExtra("keterangan",currentFinance.keterangan)
                    intent.putExtra("jumlah", currentFinance.jumlah)
                    intent.putExtra("tanggal", currentFinance.tanggal)
                    intent.putExtra("nomor", currentFinance.nomor)
                    intent.putExtra("idpay",currentFinance.idpay)
                    mContext.startActivity(intent)
                }

            }
        }


         fun bindView(finance: Finance){
            dari.text = finance.dari
            keterangan.text = finance.keterangan
            jumlah.text = finance.jumlah
            tanggal.text = finance.tanggal
            nomor.text = finance.nomor
            payby.text = dbf.querygetRekeningNamefun(finance.idpay.toInt() - 1).atasnama

             println("DAtA REKENING SATU " + dbf.querygetRekeningNamefun(finance.idpay.toInt() - 1))

        }


    }


}
