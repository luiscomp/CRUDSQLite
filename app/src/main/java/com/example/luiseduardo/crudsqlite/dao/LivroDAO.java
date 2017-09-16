package com.example.luiseduardo.crudsqlite.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.luiseduardo.crudsqlite.vo.LivroVO;

import java.util.ArrayList;

/**
 * Created by Luis Eduardo on 16/09/2017.
 */

public class LivroDAO extends BaseDAO {

    protected static SQLiteDatabase db;
    protected static final String TABLE_NAME = "Livro";

    public static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS "+TABLE_NAME
            +" ("
            +" id                   INTEGER     PRIMARY KEY NOT NULL,"
            +" titulo               TEXT        NOT NULL,"
            +" autor                INTEGER     NOT NULL,"
            +" ano                  INTEGER     NOT NULL"
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

    public static ArrayList<LivroVO> recuperar(LivroVO livro) {
        try {
            StringBuffer sql = new StringBuffer(SELECT);
            if(livro != null) {
                if(livro.getId() != 0) {
                    sql.append(" WHERE id = "+livro.getId());
                }
            }

            Cursor cursor = db.rawQuery(sql.toString(), null);

            ArrayList<LivroVO> lista = new ArrayList<>();
            LivroVO item;
            if(cursor.moveToFirst()) {
                do {
                    item = new LivroVO();
                    item.setId(cursor.getLong(cursor.getColumnIndex("id")));
                    item.setTitulo(cursor.getString(cursor.getColumnIndex("titulo")));
                    item.setAutor(cursor.getString(cursor.getColumnIndex("autor")));
                    item.setAno(cursor.getInt(cursor.getColumnIndex("ano")));

                    lista.add(item);
                } while (cursor.moveToNext());
            }
            cursor.close();

            return lista;
        } catch (Exception e) {
            throw e;
        }
    }

    public static void inserir(ArrayList<LivroVO> listaLivros) {
        try {
            db.beginTransaction();

            for (LivroVO livro : listaLivros) {
                inserir(livro);
            }

            db.setTransactionSuccessful();
        } catch (Exception e) {
            throw e;
        } finally {
            db.endTransaction();
        }
    }

    public static void inserir(LivroVO livro) {
        try {
            if(atualizar(livro) == 0) {
                ContentValues values = new ContentValues();
                values.put("titulo", livro.getTitulo());
                values.put("autor", livro.getAutor());
                values.put("ano", livro.getAno());

                livro.setId(db.insert(TABLE_NAME, null, values));
            }
        } catch (Exception e) {
            throw e;
        }
    }

    public static int atualizar(LivroVO livro) {
        try {
            ContentValues values = new ContentValues();
            values.put("titulo", livro.getTitulo());
            values.put("autor", livro.getAutor());
            values.put("ano", livro.getAno());

            return db.update(TABLE_NAME, values, "id = " + livro.getId(), null);
        } catch (Exception e) {
            throw e;
        }
    }

    public static void deletar(LivroVO livro) {
        try {
            db.delete(TABLE_NAME, "id = " + livro.getId(), null);
        } catch (Exception e) {
            throw e;
        }
    }
}
