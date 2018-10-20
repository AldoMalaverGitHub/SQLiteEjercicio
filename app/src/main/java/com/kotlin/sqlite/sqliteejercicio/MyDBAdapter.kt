package com.kotlin.sqlite.sqliteejercicio

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class MyDBAdapter(_context: Context) {

    private val nombreBd = "BdPersona"
    private var myContext: Context ?= null
    private var mDbHelper: MyDBHelper?=null
    private var sqLiteDatabase: SQLiteDatabase?= null
    private val version = 1

    init{
        this.myContext = _context
        mDbHelper = MyDBHelper(_context, nombreBd,
                null, version)
    }

    inner class MyDBHelper(context: Context?, name: String?,
                           factory: SQLiteDatabase.CursorFactory?,
                           version:Int): SQLiteOpenHelper(context, name,
                            factory, version) {
        override fun onCreate(db: SQLiteDatabase?) {
            val consulta = "CREATE TABLE persona(id Integer primary key " +
                    "autoincrement, nombre text, facultad integer);"
            db?.execSQL(consulta)
        }

        override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
            val consulta = "DROP TABLE IF EXISTS persona;"
            db?.execSQL(consulta)
            onCreate(db)
        }
    }

    public fun insertarPersona(nombre: String, facultad: Int){
        val cv: ContentValues = ContentValues()
        cv.put("nombre", nombre)
        cv.put("facultad", facultad)
        sqLiteDatabase?.insert("persona", null, cv)
    }

    public fun eliminarTodo(){
        sqLiteDatabase?.delete("persona", null, null)
    }

    public fun obtenerPersonas(): List<String>{
       var lista: MutableList<String> = ArrayList()
        var cursor: Cursor = sqLiteDatabase?.query("persona",
                null, null, null,
                null, null, null)!!

        if(cursor.moveToFirst()){
            do{
                lista.add(cursor.getString(1))
            }while(cursor.moveToNext())
        }
        return lista
    }

}


