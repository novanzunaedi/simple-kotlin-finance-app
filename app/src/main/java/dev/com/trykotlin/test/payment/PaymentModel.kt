package dev.com.trykotlin.test.payment

import android.content.ContentValues
import android.content.Context
import dev.com.trykotlin.test.data.DbFinance


class PaymentModel : PaymentMvp.Model {
    override fun saveRekData(id: Int, context: Context, namabank: String, nomorrekening: String,
                             atasnama: String):Boolean {

        var bol = false
        val dbf = DbFinance(context)
        var values = ContentValues()

        values.put("Namabank", namabank)
        values.put("Nomorrekening", nomorrekening)
        values.put("Atasnama", atasnama)

        if (id == 0){
            val id = dbf.insertRekening(values)
            bol = id > 0
        }else{
            val selectionArgs = arrayOf(id.toString())
            val id = dbf.updateRekening(values, "ID=?",selectionArgs)
            bol = id > 0
        }
        return bol
    }

}