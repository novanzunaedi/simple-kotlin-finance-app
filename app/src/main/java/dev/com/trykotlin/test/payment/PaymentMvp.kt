package dev.com.trykotlin.test.payment

import android.content.Context

interface PaymentMvp {

    interface View {

    }

    interface Model {
        fun saveRekData(id: Int, context: Context, namabank: String, nomorrekening: String,
                        atasnama: String):Boolean
    }

    interface Presenter {
        fun setView(view: PaymentMvp.View, context: Context)
        fun updateRek(id: Int, context: Context, namabank: String, nomorrekening: String,
                      atasnama: String):Boolean
    }
}