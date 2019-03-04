package dev.com.trykotlin.test.finances

import android.content.Context
import android.content.Intent
import dev.com.trykotlin.test.newfinances.NewFinanceActivity
import dev.com.trykotlin.test.payment.Rekening

class FinancesPresenter: FinancesMvp.Presenter {

    private lateinit var view: FinancesMvp.View
    private lateinit var context: Context
    var model: FinancesModel

    constructor(model: FinancesModel){

        this.model = model
    }

    override fun setView(view: FinancesMvp.View, context: Context){

        this.view = view
        this.context = context
    }

    override fun callDetail(context: Context) {

        val intent = Intent(context, NewFinanceActivity::class.java)
        context.startActivity(intent)
    }

    override fun loadInitialData(context: Context) : ArrayList<Finance> {

        return model.getFinancesFromDb("%",context)
    }

    override fun loadPaymentData(context: Context) : ArrayList<Rekening>{

        return model.getJoinTable("%",context)
    }


}