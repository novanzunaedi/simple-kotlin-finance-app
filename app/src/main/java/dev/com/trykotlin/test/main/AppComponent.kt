package dev.com.trykotlin.test.main

import dev.com.trykotlin.test.finances.MainActivity
import dagger.Component
import dev.com.trykotlin.test.finances.FinancesModule
import dev.com.trykotlin.test.newfinances.NewFinanceActivity
import dev.com.trykotlin.test.newfinances.NewFinancesModule
import dev.com.trykotlin.test.payment.PaymentActivity
import dev.com.trykotlin.test.payment.PaymentModule
import javax.inject.Singleton

@Singleton
@Component(modules = [(AppModule::class), (FinancesModule::class), (NewFinancesModule::class),
    (PaymentModule::class)])
interface AppComponent {

    fun inject(target: MainApp)
    fun inject(target: MainActivity)
    fun inject(target: NewFinanceActivity)
    fun inject(target: PaymentActivity)
}
