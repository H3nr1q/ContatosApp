package com.chs.contatos.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;

import com.chs.contatos.R;
import com.chs.contatos.adapter.ContatoAdapter;
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
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.lv_contatos);
        dao = new ContatoDAO(this);
        contatos = dao.listaContatos();
        contatosFiltrados.addAll(contatos);
        //Criei um adapter para personalizar exibição
        //ArrayAdapter<Contato> adapter = new ArrayAdapter<Contato>(this, android.R.layout.simple_expandable_list_item_1, contatosFiltrados);
        ContatoAdapter adaptador = new ContatoAdapter(this,contatosFiltrados);
        listView.setAdapter(adaptador);
        registerForContextMenu(listView);

        FloatingActionButton fab;
        fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), CadastroActivity.class);
                startActivity(i);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater i = getMenuInflater();
        i.inflate(R.menu.menu_principal, menu);
        SearchView sv = (SearchView) menu.findItem(R.id.app_bar_search).getActionView();
        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String digitouTexto) {
                posicionarContato(digitouTexto);
                return false;
            }
        });

        return true;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo){
         super.onCreateContextMenu(menu,v,menuInfo);
         MenuInflater i = getMenuInflater();
         i.inflate(R.menu.menu_contexto_item, menu);
    }


    public void posicionarContato(String nome){
        contatosFiltrados.clear();
        for(Contato c: contatos){
            if (c.getNome().toLowerCase().contains(nome.toLowerCase())){
                contatosFiltrados.add(c);
            }
        }
        listView.invalidateViews();
    }

    public void excluir (MenuItem item){
        AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        Contato contatoExcluir = contatosFiltrados.get(menuInfo.position);

        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("Atenção")
                .setMessage("Deseja excluir esse contato?")
                .setNegativeButton("Não", null)
                .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        contatosFiltrados.remove(contatoExcluir);
                        contatos.remove(contatoExcluir);
                        dao.excluir(contatoExcluir);
                        listView.invalidateViews();
                    }
                }).create();
        dialog.show();
    }

    public void editar (MenuItem item){
        AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        final Contato contatoEditar = contatosFiltrados.get(menuInfo.position);
        Intent i = new Intent(this, CadastroActivity.class);
        i.putExtra("contato", contatoEditar);
        startActivity(i);

    }


    @Override
    public void onResume() {
        super.onResume();
        contatos = dao.listaContatos();
        contatosFiltrados.clear();
        contatosFiltrados.addAll(contatos);
        listView.invalidateViews();
    }
}
