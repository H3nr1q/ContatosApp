package com.chs.contatos.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.chs.contatos.R;
import com.chs.contatos.conexao.ContatoDAO;
import com.chs.contatos.model.Contato;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ListView listView;
    private ContatoDAO dao;
    private List<Contato> contatos;
    private List<Contato> contatosFiltrados = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        FloatingActionButton fab;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.lv_contatos);
        dao = new ContatoDAO(this);
        contatos = dao.listaContatos();
        contatosFiltrados.addAll(contatos);
        ArrayAdapter<Contato> adapter = new ArrayAdapter<Contato>(this
        , android.R.layout.simple_expandable_list_item_1, contatosFiltrados);
        listView.setAdapter(adapter);

        fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),CadastroActivity.class);
                startActivity(i);
            }
        });
    }
    @Override
    public void onResume(){
        super.onResume();
        contatos = dao.listaContatos();
        contatosFiltrados.clear();
        contatosFiltrados.addAll(contatos);
        listView.invalidateViews();
}
}
