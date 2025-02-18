package com.example.convidados.repository

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import com.example.convidados.constants.DataBaseConstants
import com.example.convidados.model.GuestModel

class GuestRepository private constructor(context: Context){

    private val guestDataBase = GuestDataBase(context)

    companion object{
        private lateinit var repository: GuestRepository

        fun getInstance(context: Context): GuestRepository {
            if(!Companion::repository.isInitialized){
                repository = GuestRepository(context)
            }
            return repository
        }
    }
    fun insert(guest: GuestModel): Boolean{
        return try {
            val db = guestDataBase.writableDatabase

            val gostou = if (guest.gostou) 1 else 0

            val values = ContentValues()
            values.put(DataBaseConstants.GUEST.COLUMNS.GOSTOU, gostou)
            values.put(DataBaseConstants.GUEST.COLUMNS.NAME, guest.name)

            db.insert(DataBaseConstants.GUEST.TABLE_NAME, null, values)
            true
        }catch (e: Exception){
            false
        }

    }

    fun update(guest: GuestModel): Boolean{

        return try {
            val db = guestDataBase.writableDatabase
            val gostou = if (guest.gostou) 1 else 0

            val values = ContentValues()
            values.put(DataBaseConstants.GUEST.COLUMNS.GOSTOU, gostou)
            values.put(DataBaseConstants.GUEST.COLUMNS.NAME, guest.name)

            val selection = DataBaseConstants.GUEST.COLUMNS.ID + " = ?"
            val args = arrayOf(guest.id.toString())

            db.update(DataBaseConstants.GUEST.TABLE_NAME, values, selection, args)
            true
        }catch (e: Exception){
            false
        }

    }

    fun delete(id: Int): Boolean{

        return try{
            val db = guestDataBase.writableDatabase

            val selection = DataBaseConstants.GUEST.COLUMNS.ID + " = ?"
            var args = arrayOf(id.toString())

            db.delete(DataBaseConstants.GUEST.TABLE_NAME, selection, args)
            true
        }catch (e: Exception){
            false
        }
    }

    @SuppressLint("Recycle", "Range")
    fun get(id: Int) : GuestModel?{

        var guest: GuestModel? = null

        try{
            val db = guestDataBase.readableDatabase

            val projection = arrayOf(
                DataBaseConstants.GUEST.COLUMNS.ID,
                DataBaseConstants.GUEST.COLUMNS.NAME,
                DataBaseConstants.GUEST.COLUMNS.GOSTOU,
                DataBaseConstants.GUEST.COLUMNS.COMENTARIO
            )

            val selection = DataBaseConstants.GUEST.COLUMNS.ID + " = ?"
            val args = arrayOf(id.toString())

            val cursor = db.query(DataBaseConstants.GUEST.TABLE_NAME, projection, selection, args,null,null,null)

            if(cursor != null && cursor.count > 0){
                while (cursor.moveToNext()) {
                    val name = cursor.getString(cursor.getColumnIndex(DataBaseConstants.GUEST.COLUMNS.NAME))
                    val gostou = cursor.getInt(cursor.getColumnIndex(DataBaseConstants.GUEST.COLUMNS.GOSTOU))
                    val comentario = cursor.getString(cursor.getColumnIndex(DataBaseConstants.GUEST.COLUMNS.COMENTARIO))
                    guest = GuestModel(id, name, gostou == 1, comentario)
                }
            }

            cursor.close()

        }catch (e: Exception){
            return guest
        }
        return guest
    }

    @SuppressLint("Recycle", "Range")
    fun getAll() : List<GuestModel>{

        val list = mutableListOf<GuestModel>()

        try{
        val db = guestDataBase.readableDatabase

        val projection = arrayOf(
            DataBaseConstants.GUEST.COLUMNS.ID,
            DataBaseConstants.GUEST.COLUMNS.NAME,
            DataBaseConstants.GUEST.COLUMNS.GOSTOU,
            DataBaseConstants.GUEST.COLUMNS.COMENTARIO
        )

        val cursor = db.rawQuery("SELECT id, name, gostou, comentario FROM Guest", null)

        if(cursor != null && cursor.count > 0){
            while (cursor.moveToNext()) {
                val id = cursor.getInt(cursor.getColumnIndex(DataBaseConstants.GUEST.COLUMNS.ID))
                val name = cursor.getString(cursor.getColumnIndex(DataBaseConstants.GUEST.COLUMNS.NAME))
                val gostou = cursor.getInt(cursor.getColumnIndex(DataBaseConstants.GUEST.COLUMNS.GOSTOU))
                val comentario = cursor.getString(cursor.getColumnIndex(DataBaseConstants.GUEST.COLUMNS.COMENTARIO))

                val guest = GuestModel(id, name, gostou == 1, comentario)
                list.add(guest)
            }
        }

        cursor.close()
    }catch (e: Exception){
        return list
    }
    return list
}

    @SuppressLint("Recycle", "Range")
    fun getOpinionPositive() : List<GuestModel>{

        val list = mutableListOf<GuestModel>()

        try{
            val db = guestDataBase.readableDatabase

            val projection = arrayOf(
                DataBaseConstants.GUEST.COLUMNS.ID,
                DataBaseConstants.GUEST.COLUMNS.NAME,
                DataBaseConstants.GUEST.COLUMNS.GOSTOU,
                DataBaseConstants.GUEST.COLUMNS.COMENTARIO
            )

            val cursor = db.rawQuery("SELECT id, name, gostou, comentario FROM Guest WHERE gostou == 1", null)

            if(cursor != null && cursor.count > 0){
                while (cursor.moveToNext()) {
                    val id = cursor.getInt(cursor.getColumnIndex(DataBaseConstants.GUEST.COLUMNS.ID))
                    val name = cursor.getString(cursor.getColumnIndex(DataBaseConstants.GUEST.COLUMNS.NAME))
                    val gostou = cursor.getInt(cursor.getColumnIndex(DataBaseConstants.GUEST.COLUMNS.GOSTOU))
                    val comentario = cursor.getString(cursor.getColumnIndex(DataBaseConstants.GUEST.COLUMNS.COMENTARIO))

                    val guest = GuestModel(id, name, gostou == 1, comentario)
                    list.add(guest)
                }
            }

            cursor.close()
        }catch (e: Exception){
            return list
        }
        return list
    }

    @SuppressLint("Recycle", "Range")
    fun getOpinionNegative() : List<GuestModel>{

        val list = mutableListOf<GuestModel>()

        try{
            val db = guestDataBase.readableDatabase

            val projection = arrayOf(
                DataBaseConstants.GUEST.COLUMNS.ID,
                DataBaseConstants.GUEST.COLUMNS.NAME,
                DataBaseConstants.GUEST.COLUMNS.GOSTOU,
                DataBaseConstants.GUEST.COLUMNS.COMENTARIO
            )

            val cursor = db.rawQuery("SELECT id, name, gostou FROM Guest WHERE gostou == 0", null)

            if(cursor != null && cursor.count > 0){
                while (cursor.moveToNext()) {
                    val id = cursor.getInt(cursor.getColumnIndex(DataBaseConstants.GUEST.COLUMNS.ID))
                    val name = cursor.getString(cursor.getColumnIndex(DataBaseConstants.GUEST.COLUMNS.NAME))
                    val gostou = cursor.getInt(cursor.getColumnIndex(DataBaseConstants.GUEST.COLUMNS.GOSTOU))
                    val comentario = cursor.getString(cursor.getColumnIndex(DataBaseConstants.GUEST.COLUMNS.COMENTARIO))

                    val guest = GuestModel(id, name, gostou == 0, comentario)
                    list.add(guest)
                }
            }

            cursor.close()
        }catch (e: Exception){
            return list
        }
        return list
    }

    @SuppressLint("Recycle", "Range")
    fun getComentario(idComent: Int): String{
        try {

            val db = guestDataBase.readableDatabase


            val cursor = db.rawQuery("SELECT comentario FROM Guest WHERE id== idComent", null);
            val coment = cursor.getString(cursor.getColumnIndex(DataBaseConstants.GUEST.COLUMNS.COMENTARIO));

            return coment;

            cursor.close();
        } catch (e: Exception){
            return "Erro ao Exibir comentario";
        }
    }


}