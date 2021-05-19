package com.chs.contatos.conexao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.chs.contatos.app.ContatosApp;
import com.chs.contatos.model.Contato;

import java.util.ArrayList;
import java.util.List;

public class ContatoDAO extends DAO{
    private static ContatoDAO instance;

    public synchronized static ContatoDAO getInstance() {
        if (instance == null){
            instance = new ContatoDAO();
        }
        return instance;
    }

    public ContatoDAO(){

    }

    @Override
    public boolean saveOrEdit(Object object) {
        return false;
    }

    @Override
    public List searchByName(String name) {
        return null;
    }

    @Override
    public boolean deletar(Object object) {
        return false;
    }

    @Override
    public List listar() {
        return null;
    }

    @Override
    public Object searchContactById(int id) {
        return null;
    }

    public void inserir(Contato contato){

        ContentValues values = new ContentValues();
        values.put("nome", contato.getNome());
        values.put("telefone", contato.getTelefone());
        values.put("email", contato.getEmail());
        values.put("endereco", contato.getEndereco());
        getWritableDB().insertWithOnConflict("contato",null,values,SQLiteDatabase.CONFLICT_REPLACE);
        getWritableDB().close();
    }

    public List<Contato> listaContatos(){
        List<Contato> contatos = new ArrayList<>();
        Cursor cursor = getReadableDB().query("contato",new String[]{"id, nome, telefone, email, endereco"},
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
        getWritableDB().delete("contato", "id = ?", new String[]{contato.getId().toString()});
    }

    public void atualizar(Contato contato){
        ContentValues values = new ContentValues();
        values.put("nome",contato.getNome());
        values.put("telefone", contato.getTelefone());
        values.put("email", contato.getEmail());
        values.put("endereco", contato.getEndereco());
        getWritableDB().update("contato",values,"id = ?", new String[]{contato.getId().toString()});
        getWritableDB().close();

    }


}
