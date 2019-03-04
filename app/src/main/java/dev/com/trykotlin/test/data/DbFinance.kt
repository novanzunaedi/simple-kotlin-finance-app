package dev.com.trykotlin.test.data

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.database.sqlite.SQLiteQueryBuilder
import android.widget.Toast
import dev.com.trykotlin.test.payment.Rekening

class DbFinance {

    val dbName = "MyFinanceDb"
    val tableName = "finances"

    val dbVersion = 5
    val colId = "ID"
    val colDari = "Dari"
    val colKeterangan = "Keterangan"
    val colJumlah = "Jumlah"
    val colTanggal = "Tanggal"
    val colNomor = "Nomor"
    val colIdpay = "Idpay"
    val sqlCreateTable = "CREATE TABLE IF NOT EXISTS $tableName ( $colId INTEGER PRIMARY KEY, " +
            "$colDari TEXT, $colKeterangan TEXT, $colJumlah TEXT, $colTanggal TEXT, $colNomor TEXT, $colIdpay TEXT);"

    val tableRekening = "rekening"
    val colNamaBank = "NamaBank"
    val colNomorRekening = "NomorRekening"
    val colAtasNama = "AtasNama"
    val sqlCreateTableRekening = "CREATE TABLE IF NOT EXISTS $tableRekening ( $colId INTEGER PRIMARY KEY, " +
            "$colNamaBank TEXT, $colNomorRekening TEXT, $colAtasNama TEXT);"

    var sqlDatabase: SQLiteDatabase? = null

    constructor(context: Context){
        var db = DbMyfinancesHelper(context)
        sqlDatabase = db.writableDatabase
    }

    inner class DbMyfinancesHelper: SQLiteOpenHelper {

        var context : Context? = null
        constructor(context: Context):super(context,dbName,null,dbVersion){
            this.context = context
        }
        override fun onCreate(db: SQLiteDatabase?) {
            db!!.execSQL(sqlCreateTable)
            db!!.execSQL(sqlCreateTableRekening)
            Toast.makeText(context,"db created", Toast.LENGTH_SHORT).show()
        }

        override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
            db!!.execSQL("DROP TABLE IF EXISTS $tableName")
        }

    }

    fun insert(values: ContentValues): Long{
        val id = sqlDatabase!!.insert(tableName,"",values)
        return id
    }

    fun  query(projection:Array<String>, selection:String, selectionArgs:Array<String>, sorOrder:String): Cursor {
        val qb= SQLiteQueryBuilder()
        qb.tables=tableName
        val cursor=qb.query(sqlDatabase,projection,selection,selectionArgs,null,null,sorOrder)
        return cursor
    }

    fun delete(selection:String,selectionArgs:Array<String>):Int{
        val count=sqlDatabase!!.delete(tableName,selection,selectionArgs)
        return  count
    }

    fun update(values: ContentValues, selection:String, selectionargs:Array<String>):Int{
        val count=sqlDatabase!!.update(tableName,values,selection,selectionargs)
        return count
    }


    fun insertRekening(values: ContentValues): Long{
        val id = sqlDatabase!!.insert(tableRekening,"",values)
        return id
    }

    fun  queryRekening(projection:Array<String>, selection:String, selectionArgs:Array<String>, sorOrder:String): Cursor {
        val qb= SQLiteQueryBuilder()
        qb.tables=tableRekening
        val cursor=qb.query(sqlDatabase,projection,selection,selectionArgs,null,null,sorOrder)
        return cursor
    }

    fun deleteRekening(selection:String,selectionArgs:Array<String>):Int{
        val count=sqlDatabase!!.delete(tableRekening,selection,selectionArgs)
        return  count
    }

    fun updateRekening(values: ContentValues, selection:String, selectionargs:Array<String>):Int{
        val count=sqlDatabase!!.update(tableRekening,values,selection,selectionargs)
        return count
    }


    fun querygetRekeningNamefun(position: Int): Rekening {

        val rekenings = arrayListOf<Rekening>()
        val rawQueryData = "SELECT * FROM $tableRekening"
        val cursor = sqlDatabase!!.rawQuery(rawQueryData, null)

        if(cursor.moveToFirst()){
            do {
                val id = cursor.getInt(cursor.getColumnIndex("ID"))
                val namabank = cursor.getString(cursor.getColumnIndex("NamaBank"))
                val nomorrekening = cursor.getString(cursor.getColumnIndex("NomorRekening"))
                val atasnama = cursor.getString(cursor.getColumnIndex("AtasNama"))
                rekenings.add(Rekening(id, namabank, nomorrekening, atasnama))
            }while (cursor.moveToNext())
        }

         return rekenings.get(position)
    }

}

