package dev.com.trykotlin.test.finances

import android.content.Context
import dev.com.trykotlin.test.payment.Rekening

interface FinancesMvp {

    interface View{
        fun showList(list: ArrayList<Finance>)
    }

    interface Model{
        fun getFinancesFromDb(query: String, context: Context): ArrayList<Finance>
        fun getJoinTable(query: String, context: Context): ArrayList<Rekening>
    }

    interface Presenter{
        fun setView(view: View, context: Context)
        fun callDetail(context: Context)
        fun loadInitialData(context: Context) : ArrayList<Finance>
        fun loadPaymentData(context: Context) : ArrayList<Rekening>
    }
}