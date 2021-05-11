package com.chs.contatos.conexao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class ContatoHelper extends SQLiteOpenHelper {
    private static final String name = "Contato.db";
    private static final int version = 1;

    public ContatoHelper(Context context) {
        super(context, name, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table contato" +
                "(id integer primary key autoincrement," +
                "nome varchar(50)," +
                "telefone varchar(20)," +
                "email varchar (100)," +
                "endereco varchar (100))");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
