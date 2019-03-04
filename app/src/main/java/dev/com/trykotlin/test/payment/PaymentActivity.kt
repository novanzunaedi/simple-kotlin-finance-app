package dev.com.trykotlin.test.payment

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import dev.com.trykotlin.test.R
import dev.com.trykotlin.test.main.MainApp
import kotlinx.android.synthetic.main.activity_payment.*
import javax.inject.Inject

class PaymentActivity : AppCompatActivity(), PaymentMvp.View {

    @Inject
    lateinit var presenter: PaymentPresenter

    var id = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment)

        (application as MainApp).component.inject(this)

        btListener()

    }

    fun btListener(){

        btnSimpan.setOnClickListener {
            var result = presenter.updateRek(id, this@PaymentActivity,
                    spinnerBankName.selectedItem.toString(), etNomorRekening.text.toString(), etAtasNama.text.toString())

            if(result){
                Toast.makeText(baseContext,"sucess", Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(baseContext,"failed", Toast.LENGTH_SHORT).show()
            }
        }
    }
}