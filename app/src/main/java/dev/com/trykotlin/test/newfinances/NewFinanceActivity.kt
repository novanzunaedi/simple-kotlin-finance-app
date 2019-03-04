package dev.com.trykotlin.test.newfinances

import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import dev.com.trykotlin.test.R
import dev.com.trykotlin.test.data.sharepref.SP
import dev.com.trykotlin.test.main.MainApp
import dev.com.trykotlin.test.payment.Rekening
import kotlinx.android.synthetic.main.activity_new_finance.*
import org.joda.time.DateTime
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import javax.inject.Inject



class NewFinanceActivity : AppCompatActivity(), NewFinancesMvp.View {

    @Inject
    lateinit var presenter: NewFinancesPresenter

    var id = 0

    var rekAdapter : RekeningAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_finance)

        (application as MainApp).component.inject(this)

        btListener()

    }

    fun btListener(){

        btnSimpan.setOnClickListener {

            var result = presenter.update(id,this@NewFinanceActivity,
                    etDari.text.toString(),etKeterangan.text.toString(), etJumlah.text.toString(),
                    tvTanggal.text.toString(), tvNomor.text.toString(),
                    tvSpinnerSelected.text.toString())


            if(result){
                Toast.makeText(baseContext,"sucess", Toast.LENGTH_SHORT).show()

                val extras = intent.extras
                if(extras == null){
                    val noURUT = SP.getInstance().get(SP.NOMOR_URUT) as Int + 1
                    SP.getInstance().put(SP.NOMOR_URUT, noURUT)
                }
            }else{

                Toast.makeText(baseContext,"failed", Toast.LENGTH_SHORT).show()
            }
        }

        btnAddRekening.setOnClickListener {
            presenter.callPayment(this@NewFinanceActivity)
        }

    }


    override fun populateEditTesxt() {

        val extras = intent.extras
        if (extras != null) {
            id = extras.getInt("id",0)
            val dari = extras.getString("dari")
            val keterangan = extras.getString("keterangan")
            val jumlah = extras.getString("jumlah")
            val tanggal = extras.getString("tanggal")
            val nomor = extras.getString("nomor")
            val idpay = extras.getString("idpay")

            etDari.setText(dari)
            etKeterangan.setText(keterangan)
            etJumlah.setText(jumlah)
            tvTanggal.setText(tanggal)
            tvNomor.setText(nomor)

            spinnerRek.setSelection(idpay.toInt() - 1)
            tvSpinnerSelected.setText(idpay as String)
        }

        val tgl: String = tvTanggal.text.toString()
        println("DATA ET TANGGAL " + tgl)
        if (tgl.trim().equals("Tanggal")){ // check tvTanggal empty or not // cek equal Tanggal karena text di XML == Tanggal
            DateNow()
        }


        val nmr: String = tvNomor.text.toString()
        if (nmr.trim().equals("Nomor")){ // check tvNomor empty or not // cek equal Tanggal karena text di XML == Nomor
            NumberTransfer()
        }
    }

    fun DateNow() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val current = LocalDateTime.now()
            val formatter = DateTimeFormatter.BASIC_ISO_DATE
            var answer: String =  current.format(formatter)

            tvTanggal.setText(answer)
        } else {
            var date = Date();
            val formatter = SimpleDateFormat("MMM dd yyyy")
            val answer: String = formatter.format(date)

            tvTanggal.setText(answer)
        }
    }

    fun NumberTransfer(){

        val now = DateTime.now()

//        val localDate = LocalDate.now()
//
//        println("DAY SAVED " + SP.getInstance().get(SP.DATE_SAVE))
//
//        println("DATA NOW JODA " + now)
//        println("DATA NOW JODA YEARS " + now.year().get())
//        println("DATA NOW JODA MONTH " + now.monthOfYear().get())
//        println("DATA NOW JODA DAY " + now.dayOfMonth().get())

        val hurTerakhir = now.year().get() // tahun dua angka terakhir beres
        val tahun2Angka = hurTerakhir.toString().split("20").toTypedArray()
//        println("DATA 2 LAST " + tahun2Angka.get(1))

        val dcBulan = when { // diartikan dcBulan sama dengan ketika hari lebih kecil dari 10 maka dan jika
            now.monthOfYear().get() < 10 -> {
                "0" + now.monthOfYear().get().toString()
            }
            else -> now.monthOfYear().get().toString()
        }

//        val dBulanc = if(now.monthOfYear().get() < 10){ // di artikan dBulanc jika lebih kecil dari 10 maka dan lainnya maka
//            "0" + now.monthOfYear().get().toString()
//        } else now.monthOfYear().get().toString()

        val dcHari = when { // diartikan dcHari sama dengan ketika hari lebih kecil dari 10 maka dan jika
            now.dayOfMonth().get() < 10 -> {
                "0" + now.dayOfMonth().get().toString()
            }
            else -> now.dayOfMonth().get().toString()
        }

//        val dHaric = if(now.dayOfMonth().get() < 10){
//            "0" + now.dayOfMonth().get().toString()
//        }else now.dayOfMonth().get().toString()

        val noURUT = SP.getInstance().get(SP.NOMOR_URUT) as Int + 1 // nomor URUT
//        println("NO URUT " + noURUT)

        val nomorTrfs = "UM/" + tahun2Angka.get(1) + dcBulan + dcHari + "/" + noURUT
        tvNomor.setText(nomorTrfs)
    }


    fun isNullOrEmpty(str: String?): Boolean {
        if (str != null && !str.isEmpty())
            return false
        return true
    }

    override fun onResume() {
        super.onResume()

        loadRekeningList()
        populateEditTesxt()
    }

    private fun loadRekeningList() {

        val dRekenings = presenter.loadInitialRekData(this)
//        println("DATA REKE LIST ITERATOR " + dRekenings.listIterator())        // prints "[1, 2, 3]"

        rekAdapter = RekeningAdapter(this@NewFinanceActivity, dRekenings)
        spinnerRek.adapter = rekAdapter

        spinnerRek.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(adapterView: AdapterView<*>, view: View, position: Int, l: Long) {
                val rekening = adapterView.adapter.getItem(position) as Rekening

                tvSpinnerSelected.setText(rekening.finId.toString())

            }

            override fun onNothingSelected(adapterView: AdapterView<*>) {}
        }
    }
}

