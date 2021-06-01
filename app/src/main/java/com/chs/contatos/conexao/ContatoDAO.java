package com.chs.contatos.conexao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.chs.contatos.app.ContatosApp;
import com.chs.contatos.model.Contato;

import java.util.ArrayList;
import java.util.List;

public class ContatoDAO extends DAO<Contato>{
    private static ContatoDAO instance;

    public ContatoDAO(){

    }

    @Override
    public boolean saveOrEdit(Contato object) {
        return false;
    }

    @Override
    public List<Contato> searchByName(String name) {
        return null;
    }

    @Override
    public boolean deletar(Contato object) {
        return false;
    }

    @Override
    public List<Contato> listar() {
        return null;
    }

    @Override
    public Contato searchContactById(int id) {
        return null;
    }

    public synchronized static ContatoDAO getInstance() {
        if (instance == null){
            instance = new ContatoDAO();
        }
        return instance;
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
        String sql = "SELECT * FROM " + "contato" + ";";
        Cursor cursor = getReadableDB().rawQuery(sql, null);
        //        Cursor cursor = getReadableDB().query("contato",new String[] {"id, nome, telefone, email, endereco"},
        //                null, null, null, null, null);
        while (cursor.moveToNext()){
            Contato c = new Contato();
            c.setId(cursor.getInt(cursor.getColumnIndex("id")));
            c.setNome(cursor.getString(cursor.getColumnIndex("nome")));
            c.setTelefone(cursor.getString(cursor.getColumnIndex("telefone")));
            c.setEmail(cursor.getString(cursor.getColumnIndex("email")));
            c.setEndereco(cursor.getString(cursor.getColumnIndex("endereco")));
//            c.setId(cursor.getInt(0));
//            c.setNome(cursor.getString(1));
//            c.setTelefone(cursor.getString(2));
//            c.setEmail(cursor.getString(3));
//            c.setEndereco(cursor.getString(4));
            contatos.add(c);
        }
        cursor.close();
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
