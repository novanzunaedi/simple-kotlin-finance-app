package dev.com.trykotlin.test.finances

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import dev.com.trykotlin.test.R
import dev.com.trykotlin.test.data.sharepref.SP

import dev.com.trykotlin.test.main.MainApp
import dev.com.trykotlin.test.payment.Rekening
import kotlinx.android.synthetic.main.activity_main.*
import org.joda.time.DateTime
import org.joda.time.DateTimeZone
import org.joda.time.Days.daysBetween
import javax.inject.Inject


class MainActivity : AppCompatActivity(), FinancesMvp.View {

    @Inject
    lateinit var presenter: FinancesPresenter


    var mAdapter : FinancesAdapter? = null
    val finances =  arrayListOf<Finance>()
    val rekenings = arrayListOf<Rekening>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        (application as MainApp).component.inject(this)

        val now = DateTime.now()
        if(isNullOrEmpty(SP.getInstance().get(SP.DATE_SAVE).toString())){

            SP.getInstance().put(SP.DATE_SAVE,
                    now.dayOfMonth().get().toString() + " " +
                    now.monthOfYear().get().toString() + " " +
                    now.year().get().toString())
        }

        if(isNullOrEmptyInt(SP.getInstance().get(SP.NOMOR_URUT) as Int?)){ // memberi nilai pada SP NO URUT
            SP.getInstance().put(SP.NOMOR_URUT, 0)
        }

        if(isNullOrEmptyInt(SP.getInstance().get(SP.YEARS_PAST) as Int?)){ // memberi nilai pada SP NO URUT

            SP.getInstance().put(SP.YEARS_PAST, now.year().get()) // save years, month, day in Sharepreference
            SP.getInstance().put(SP.MONTH_PAST, now.monthOfYear().get())
            SP.getInstance().put(SP.DAY_PAST, now.dayOfMonth().get())
        }

        cekDistanceDate()
    }

    override fun showList(list: ArrayList<Finance>) {

        finances.clear()
        this.finances.addAll(list)

        mAdapter = FinancesAdapter(this@MainActivity, finances)

      //  loadData("%")
        recycler.apply {

            layoutManager  = LinearLayoutManager(this@MainActivity)
            adapter = mAdapter
        }
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(R.menu.menu_main,menu)

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
      //  return super.onOptionsItemSelected(item)

        item.itemId.let {
            when(item.itemId){
                R.id.add_item -> {

                    presenter.callDetail(this@MainActivity)
                }

                else-> Log.i("tets","teste")

            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onResume() {
        super.onResume()

        presenter.setView(this,this)
        showList(presenter.loadInitialData(this))

    }

    fun isNullOrEmpty(str: String?): Boolean {
        if (str != null && !str.isEmpty())
            return false
        return true
    }

    fun isNullOrEmptyInt(str: Int?): Boolean {
        if (str != null)
            return false
        return true
    }

    fun cekDistanceDate(){
        val now = DateTime.now()

        val INDONESIA = DateTimeZone.forID("Asia/Jakarta")
        val start = DateTime(SP.getInstance().get(SP.YEARS_PAST) as Int,
                SP.getInstance().get(SP.MONTH_PAST) as Int ,
                SP.getInstance().get(SP.DAY_PAST) as Int, 0, 0, 0, INDONESIA)
        val end = DateTime(now.year().get(),
                now.monthOfYear().get(),
                now.dayOfMonth().get(), 0, 0, 0, 0, INDONESIA)

        val dSave = daysBetween(start.withTimeAtStartOfDay(),
                end.withTimeAtStartOfDay()).getDays()
        SP.getInstance().put(SP.DATE_DISTANCE_SAVE, dSave)

        println("DISTANCE DATE " + SP.getInstance().get(SP.DATE_SAVE))
        println("DISTANCE DAY " + SP.getInstance().get(SP.DATE_DISTANCE_SAVE))

        when {
            SP.getInstance().get(SP.DATE_DISTANCE_SAVE) as Int > 0 -> {
                SP.getInstance().put(SP.NOMOR_URUT, 0)
                SP.getInstance().put(SP.DATE_DISTANCE_SAVE, 0)

                SP.getInstance().put(SP.YEARS_PAST, now.year().get())
                SP.getInstance().put(SP.MONTH_PAST, now.monthOfYear().get())
                SP.getInstance().put(SP.DAY_PAST, now.dayOfMonth().get())
            }
            else -> println("JARAK HARI TIDAK MASALAH")
        }
    }
}
