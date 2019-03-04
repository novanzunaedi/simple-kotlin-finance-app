package dev.com.trykotlin.test.newfinances

import android.content.Context
import dev.com.trykotlin.test.payment.Rekening

interface NewFinancesMvp {

    interface View{

        fun  populateEditTesxt()
    }

    interface Model{

        fun saveData(id: Int, context: Context, dari: String, keterangan: String, jumlah: String,
                     tanggal: String, nomor: String, idpay: String):Boolean

        fun getRekeningfromDB(query: String, context: Context): ArrayList<Rekening>
    }

    interface Presenter{
        fun callPayment(context: Context)
        fun setView(view: NewFinancesMvp.View, context: Context)
        fun update(id: Int, context: Context, dari: String, keterangan: String, jumlah: String,
                   tanggal: String, nomor: String, idpay: String):Boolean
        fun loadInitialRekData(context: Context) : ArrayList<Rekening>

    }
}