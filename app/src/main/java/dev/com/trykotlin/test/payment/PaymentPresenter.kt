package dev.com.trykotlin.test.payment

import android.content.Context

class PaymentPresenter: PaymentMvp.Presenter {

    lateinit var context: Context
    lateinit var view : PaymentMvp.View

    var model: PaymentModel

    constructor(model: PaymentModel){

        this.model = model
    }

    override fun setView(view: PaymentMvp.View, context: Context) {
        this.context = context
        this.view = view
    }

    override fun updateRek(id: Int, context: Context, namabank: String, nomorrekening: String, atasnama: String): Boolean {

        if(!namabank.isBlank() && !nomorrekening.isBlank() && !atasnama.isBlank()){ // belum di lakukan pengecekan jumlah (int) apabila kosong

            return model.saveRekData(id,context,namabank,nomorrekening,atasnama)
        }
        else{
            return false
        }
    }

}