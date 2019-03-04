package dev.com.trykotlin.test.newfinances

import dagger.Module
import dagger.Provides

@Module
class NewFinancesModule{

    @Provides
    fun providesNewFinancesPresenter(model: NewFinancesModel): NewFinancesPresenter {


        return NewFinancesPresenter(model)
    }

    @Provides
    fun providesNewFinancesMOdel(): NewFinancesModel {

        return NewFinancesModel()
    }


}