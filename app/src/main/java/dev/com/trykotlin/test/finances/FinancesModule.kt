package dev.com.trykotlin.test.finances

import dagger.Module
import dagger.Provides

@Module
class FinancesModule {


    @Provides
    fun providesFinancesPresenter(model : FinancesModel): FinancesPresenter {

        return FinancesPresenter(model)
    }

    @Provides
    fun providesFinancesModel(): FinancesModel {

        return FinancesModel()
    }
}