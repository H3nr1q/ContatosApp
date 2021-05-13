package com.chs.contatos.conexao;

import android.content.ContentValues;
import android.content.Context;
import android.database.ContentObservable;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.chs.contatos.model.Contato;

import java.util.ArrayList;
import java.util.List;

public class ContatoDAO {
    private ContatoHelper contatoHelper;
    private SQLiteDatabase banco;

    public ContatoDAO(Context context){
        contatoHelper = new ContatoHelper(context);
        banco = contatoHelper.getWritableDatabase();
    }

    public long inserir(Contato contato){
        ContentValues values = new ContentValues();
        //values.put("id",contato.getId());
        values.put("nome", contato.getNome());
        values.put("telefone", contato.getTelefone());
        values.put("email", contato.getEmail());
        values.put("endereco", contato.getEndereco());
        //return banco.insertWithOnConflict("contato",null,values,SQLiteDatabase.CONFLICT_REPLACE);
        return banco.insert("contato", null, values);

    }

    public List<Contato> listaContatos(){
        List<Contato> contatos = new ArrayList<>();
        Cursor cursor = banco.query("contato",new String[]{"id, nome, telefone, email, endereco"},
                null, null, null, null, null);
        while (cursor.moveToNext()){
            Contato c = new Contato();
            c.setId(cursor.getInt(0));
            c.setNome(cursor.getString(1));
            c.setTelefone(cursor.getString(2));
            c.setEmail(cursor.getString(3));
            c.setEndereco(cursor.getString(4));
            contatos.add(c);
        }
        return contatos;
    }

    public void excluir(Contato contato){
        banco.delete("contato", "id = ?", new String[]{contato.getId().toString()});
    }

    public void atualizar(Contato contato){
        ContentValues values = new ContentValues();
        values.put("nome",contato.getNome());
        values.put("telefone", contato.getTelefone());
        values.put("email", contato.getEmail());
        values.put("endereco", contato.getEndereco());
        banco.update("contato",values,"id = ?", new String[]{contato.getId().toString()});

    }


}
