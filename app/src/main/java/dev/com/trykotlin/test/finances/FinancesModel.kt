package dev.com.trykotlin.test.finances

import android.content.Context
import dev.com.trykotlin.test.data.DbFinance
import dev.com.trykotlin.test.payment.Rekening

class FinancesModel: FinancesMvp.Model {
    override fun getFinancesFromDb(query: String, context: Context): ArrayList<Finance> {

        val finances = arrayListOf<Finance>()
        val dbf = DbFinance(context)
        val projections = arrayOf("ID","Dari","Keterangan","Jumlah","Tanggal","Nomor","Idpay")
        val selectionArgs = arrayOf(query)
        val cursor = dbf.query(projections,"Tanggal like ?",selectionArgs ,"Tanggal")
//        val cursor = dbf.query(projections, "Tanggal like ?", selectionArgs, "ORDER BY Tanggal DESC")

        if(cursor.moveToFirst()){
            do {
                val id = cursor.getInt(cursor.getColumnIndex("ID"))
                val dari = cursor.getString(cursor.getColumnIndex("Dari"))
                val keterangan = cursor.getString(cursor.getColumnIndex("Keterangan"))
                val jumlah = cursor.getString(cursor.getColumnIndex("Jumlah"))
                val tanggal = cursor.getString(cursor.getColumnIndex("Tanggal"))
                val nomor = cursor.getString(cursor.getColumnIndex("Nomor"))
                val idpay = cursor.getString(cursor.getColumnIndex("Idpay"))
                finances.add(Finance(id, dari, keterangan, jumlah, tanggal, nomor, idpay))
            }while (cursor.moveToNext())
        }
        return finances
    }

    override fun getJoinTable(query: String, context: Context): ArrayList<Rekening> {

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