package com.example.primeraprueba


import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class SQLiteHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null,DATABASE_VERSION) {
    companion object {
        private const val DATABASE_NAME = "Aplicativo"
        private const val DATABASE_VERSION = 1
        private const val TBL_USUARIO = "tbl_usuario"
        private const val ID = "id"
        private const val USER = "usuario"
        private const val EMAIL = "email"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTblUsuario = ("CREATE TABLE " + TBL_USUARIO + "("
                + ID + " INTEGER PRIMARY KEY," + USER + " TEXT,"
                + EMAIL + " TEXT" + ")")
        db?.execSQL(createTblUsuario)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS $TBL_USUARIO")
        onCreate(db)
    }

    fun insertUsuario(std: UsrModel): Long {
        val db = this.writableDatabase

        val contentValues = ContentValues()
        contentValues.put(ID, std.id)
        contentValues.put(USER, std.usuario)
        contentValues.put(EMAIL, std.email)

        val success = db.insert(TBL_USUARIO, null, contentValues)
        db.close()
        return success
    }

    fun getAllUsuario(): ArrayList<UsrModel> {
        val stdList: ArrayList<UsrModel> = ArrayList()
        val selectQuery = "SELECT * FROM $TBL_USUARIO"
        val db = this.readableDatabase

        val cursor: Cursor?

        try {
            cursor = db.rawQuery(selectQuery, null)
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
            db.execSQL(selectQuery)
            return ArrayList()
        }
        var id: Int
        var usuario: String
        var email: String

        if (cursor.moveToFirst()) {
            do {
                id = cursor.getInt(cursor.getColumnIndexOrThrow("id"))
                usuario = cursor.getString(cursor.getColumnIndexOrThrow("usuario"))
                email = cursor.getString(cursor.getColumnIndexOrThrow("email"))

                val std = UsrModel(id = id, usuario = usuario, email = email)
                stdList.add(std)
            } while (cursor.moveToNext())
        }
        cursor.close()
        return stdList
    }

    fun updateUser(std: UsrModel): Int {
        val db = this.writableDatabase

        val contentValues = ContentValues()
        contentValues.put(ID, std.id)
        contentValues.put(USER, std.usuario)
        contentValues.put(EMAIL, std.email)

        val success = db.update(TBL_USUARIO, contentValues, "id=" + std.id, null)
        db.close()
        return success
    }
    fun deleteUserByID(id:Int): Int {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(ID, id)

        val success = db.delete(TBL_USUARIO, "id=$id", null)
        db.close()
        return success
    }
}

