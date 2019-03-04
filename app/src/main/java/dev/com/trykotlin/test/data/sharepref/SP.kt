package dev.com.trykotlin.test.data.sharepref

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson

class SP {

    public val NOMOR_URUT = "nomor_urut"
    public val DATE_SAVE = "date_save"
    public val DATE_NOW = "date_now"

    private lateinit var sharePref: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor
    private lateinit var mContext: Context

    companion object {
        @SuppressLint("StaticFieldLeak")
        private var mInstance: SP? = null

        public val NOMOR_URUT = "nomor_urut"
        public val DATE_SAVE = "date_save"
        public val DATE_DISTANCE_SAVE = "date_distance_save"
        public val DATE_NOW = "date_now"

        public val YEARS_PAST = "years_past"
        public val MONTH_PAST = "month_past"
        public val DAY_PAST = "day_past"

        fun getInstance(): SP {
            if (mInstance == null) {
                synchronized(SP::class.java)
                {
                    if (mInstance == null) {
                        mInstance = SP()
                    }
                }
            }
            return mInstance!!
        }
    }

    @SuppressLint("CommitPrefEdits")
    fun init(context: Context) {
        mContext = context
        sharePref = mContext.getSharedPreferences(context.packageName, Context.MODE_PRIVATE)
        editor = sharePref.edit()
    }
    private fun putData(key: String, value: Any) {
        when (value) {
            is String -> editor.putString(key, value.toString()).apply()
            is Int -> editor.putInt(key, value.toInt()).apply()
            is Boolean -> editor.putBoolean(key, (value as Boolean)).apply()
            is Float -> editor.putFloat(key, value.toFloat()).apply()
            is Long -> editor.putLong(key, value.toLong()).apply()
            else -> putObject(key, value)
        }
    }


    /*save specific data type*/
    fun put(key: String, value: Any) {
        putData(key, value)
    }

    /*return specific data type*/
    fun get(key: String): Any? {
        val keys = sharePref.all
        keys.forEach {
            if (it.key == key) {
                when {
                    it.value is String -> return it.value.toString()
                    it.value is Int -> return (it.value as Int).toInt()
                    it.value is Boolean -> return it.value
                    it.value is Float -> return (it.value as Float).toFloat()
                    it.value is Long -> return (it.value as Long).toLong()
                    it.value is Double -> return (it.value as Double).toDouble()
                }
            }
        }
        return null
    }


    private fun putObject(key: String, value: Any) {
        editor.putString(key, Gson().toJson(value)).apply()
    }

    /*return Object Type*/
    fun <T> get(key: String, classType: Class<T>): T {
        return Gson().fromJson(sharePref.getString(key, ""), classType)
    }

    fun isContain(key: String): Boolean {
        return sharePref.contains(key)
    }

    fun delete(key: String) {
        if (sharePref.contains(key)) {
            editor.remove(key).apply()
        }
    }

    /*save ArrayList with any Object*/
    fun <T> put(key: String, list: ArrayList<T>) {
        val json = Gson().toJson(list)
        editor.putString(key, json).apply()
    }

    /*return ArrayList<AnyObject>*/
    fun <T> get(key: String, list: ArrayList<T>): ArrayList<T> {
        var json = sharePref.getString(key, "")
        return Gson().fromJson(json, list.javaClass)
    }
}