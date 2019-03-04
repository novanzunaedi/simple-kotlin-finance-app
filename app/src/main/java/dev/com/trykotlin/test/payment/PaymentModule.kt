package dev.com.trykotlin.test.payment

import dagger.Module
import dagger.Provides

@Module
class PaymentModule {

    @Provides
    fun providesPaymentPresenter(model: PaymentModel) : PaymentPresenter {

        return PaymentPresenter(model)
    }

    @Provides
    fun providesPaymentModel(): PaymentModel{
        return PaymentModel()
    }
}