package com.chs.contatos.conexao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.chs.contatos.app.ContatosApp;


public class ContatoHelper extends SQLiteOpenHelper {
    private static final String name = "Contato.db";
    private static final int version = 1;
    public static ContatoHelper contatoHelper;

    public ContatoHelper() {
        super(ContatosApp.getInstance(), name, null, version);
    }

    public synchronized static ContatoHelper getInstance(){
        if(contatoHelper == null){
            contatoHelper = new ContatoHelper();
        }
        return contatoHelper;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql =("create table if not exists contato " +
                "(id integer primary key autoincrement, " +
                " nome text," +
                " telefone text," +
                " email text," +
                " endereco text);");

        try{
            db.execSQL(sql);
            Log.i("INFO DB", "Sucesso ao criar a tabela");
        }catch (Exception e){
            Log.i("INFO DB", "Erro ao criar a tabela" + e.getMessage());

        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
