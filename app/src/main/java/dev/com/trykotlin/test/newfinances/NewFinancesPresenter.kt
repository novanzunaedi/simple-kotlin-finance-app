package dev.com.trykotlin.test.newfinances

import android.content.Context
import android.content.Intent
import dev.com.trykotlin.test.payment.PaymentActivity
import dev.com.trykotlin.test.payment.Rekening

class NewFinancesPresenter: NewFinancesMvp.Presenter{

    lateinit var context: Context
    lateinit var view : NewFinancesMvp.View

    var model: NewFinancesModel

    constructor(model: NewFinancesModel){

        this.model = model
    }

    override fun setView(view: NewFinancesMvp.View, context: Context) {
        this.context = context
        this.view = view
    }

    override fun update(id: Int, context: Context, dari: String, keterangan: String, jumlah: String,
                        tanggal: String, nomor: String, idpay: String):Boolean {

        if(!dari.isBlank() && !keterangan.isBlank()){ // belum di lakukan pengecekan jumlah (int) apabila kosong

            return model.saveData(id,context,dari,keterangan,jumlah,tanggal,nomor,idpay)
        }
        else{
            return false
        }
    }

    override fun callPayment(context: Context) {
        val intent = Intent(context, PaymentActivity::class.java)
        context.startActivity(intent)
    }

    override fun loadInitialRekData(context: Context) : ArrayList<Rekening> {

        return model.getRekeningfromDB("%",context)
    }

}