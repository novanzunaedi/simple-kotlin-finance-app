package dev.com.trykotlin.test.newfinances

import android.content.ContentValues
import android.content.Context
import dev.com.trykotlin.test.data.DbFinance
import dev.com.trykotlin.test.payment.Rekening

class NewFinancesModel: NewFinancesMvp.Model{
    override fun saveData(id: Int, context: Context, dari: String, keterangan: String,
                          jumlah: String, tanggal: String, nomor: String, idpay: String):Boolean {

        var bol = false
        val dbf = DbFinance(context)
        var values = ContentValues()

        values.put("Dari",dari)
        values.put("Keterangan",keterangan)
        values.put("jumlah",jumlah)
        values.put("tanggal",tanggal)
        values.put("nomor",nomor)
        values.put("idpay",idpay)

        if (id == 0){
            val id = dbf.insert(values)
            bol = id > 0
        }else{
            val selectionArgs = arrayOf(id.toString())
            val id = dbf.update(values, "ID=?",selectionArgs)
            bol = id > 0
        }
        return bol
    }


    override fun getRekeningfromDB(query: String, context: Context): ArrayList<Rekening> {

        val rekenings = arrayListOf<Rekening>()
        val dbf = DbFinance(context)
        val projections = arrayOf("ID","NamaBank","NomorRekening","AtasNama")
        val selectionArgs = arrayOf(query)
        val cursor = dbf.queryRekening(projections,"AtasNama like ?",selectionArgs ,"ID")

        if(cursor.moveToFirst()){
            do {
                val id = cursor.getInt(cursor.getColumnIndex("ID"))
                val namabank = cursor.getString(cursor.getColumnIndex("NamaBank"))
                val nomorrekening = cursor.getString(cursor.getColumnIndex("NomorRekening"))
                val atasnama = cursor.getString(cursor.getColumnIndex("AtasNama"))
                rekenings.add(Rekening(id, namabank, nomorrekening, atasnama))
            }while (cursor.moveToNext())
        }
        return rekenings
    }
}