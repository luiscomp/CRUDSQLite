package com.example.luiseduardo.crudsqlite.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.luiseduardo.crudsqlite.vo.CarroVO;

import java.util.ArrayList;

/**
 * Created by Luis Eduardo on 16/09/2017.
 */

public class CarroDAO extends BaseDAO {

    protected static SQLiteDatabase db;
    protected static final String TABLE_NAME = "Carro";

    public static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS "+TABLE_NAME
            +" ("
            +" id                   INTEGER     PRIMARY KEY NOT NULL,"
            +" modelo               TEXT        NOT NULL,"
            +" marca                TEXT        NOT NULL,"
            +" ano                  INTEGER     NOT NULL,"
            +" cor                  INTEGER     NOT NULL"
            + ")";

    private static final String SELECT = "SELECT * FROM "+TABLE_NAME;

    public static void abrirConexao(Context context) {
        newInstance(context);
        db = DBHelper.getWritableDatabase();
    }

    public static void fecharConexao() {
        if(db != null){
            db.close();
            db = null;
            DBHelper = null;
        }
    }

    public static ArrayList<CarroVO> recuperar(CarroVO carro) {
        try {
            StringBuffer sql = new StringBuffer(SELECT);
            if(carro != null && carro.getId() != 0) {
                sql.append(" WHERE id = "+carro.getId());
            }

            Cursor cursor = db.rawQuery(sql.toString(), null);

            ArrayList<CarroVO> lista = new ArrayList<>();
            CarroVO item;
            if(cursor.moveToFirst()) {
                do {
                    item = new CarroVO();
                    item.setId(cursor.getLong(cursor.getColumnIndex("id")));
                    item.setModelo(cursor.getString(cursor.getColumnIndex("modelo")));
                    item.setMarca(cursor.getString(cursor.getColumnIndex("marca")));
                    item.setAno(cursor.getInt(cursor.getColumnIndex("ano")));
                    item.setCor(cursor.getInt(cursor.getColumnIndex("cor")));

                    lista.add(item);
                } while (cursor.moveToNext());
            }
            cursor.close();

            return lista;
        } catch (Exception e) {
            throw e;
        }
    }

    public static void inserir(ArrayList<CarroVO> listaCarros) {
        try {
            db.beginTransaction();

            for (CarroVO carro : listaCarros) {
                inserir(carro);
            }

            db.setTransactionSuccessful();
        } catch (Exception e) {
            throw e;
        } finally {
            db.endTransaction();
        }
    }

    public static void inserir(CarroVO carro) {
        try {
            if(atualizar(carro) == 0) {
                ContentValues values = new ContentValues();
                values.put("modelo", carro.getModelo());
                values.put("marca", carro.getMarca());
                values.put("ano", carro.getAno());
                values.put("cor", carro.getCor());

                carro.setId(db.insert(TABLE_NAME, null, values));
            }
        } catch (Exception e) {
            throw e;
        }
    }

    public static int atualizar(CarroVO carro) {
        try {
            ContentValues values = new ContentValues();
            values.put("modelo", carro.getModelo());
            values.put("marca", carro.getMarca());
            values.put("ano", carro.getAno());
            values.put("cor", carro.getCor());

            return db.update(TABLE_NAME, values, "id = " + carro.getId(), null);
        } catch (Exception e) {
            throw e;
        }
    }

    public static void deletar(CarroVO carro) {
        try {
            db.delete(TABLE_NAME, "id = " + carro.getId(), null);
        } catch (Exception e) {
            throw e;
        }
    }
}
