package com.chs.contatos.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.ListFragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;

import com.chs.contatos.R;
import com.chs.contatos.adapter.ContatoAdapter;
import com.chs.contatos.conexao.ContatoDAO;
import com.chs.contatos.model.Contato;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MainPresenter.MainView, ContatoListFragment.AoClicarNoContato {
    private ListView listView;
    private List<Contato> contatos = new ArrayList<>();
    private List<Contato> contatosFiltrados = new ArrayList<>();
    private MainPresenter mainPresenter;
    private ContatoAdapter adaptador;
    FloatingActionButton fab;
    private ListFragment listFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bindViews();
        mainPresenter = new MainPresenter(this);
        //mainPresenter.listarContatos();

    }

    private void bindViews(){

        listView = findViewById(R.id.lv_contatos);
        adaptador = new ContatoAdapter(this,contatosFiltrados);
        listView.setAdapter(adaptador);

        fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), CadastroActivity.class);
                startActivity(i);
            }
        });

        registerForContextMenu(listView);

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
                        ContatoDAO.getInstance().excluir(contatoExcluir);
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
        contatos = ContatoDAO.getInstance().listaContatos();
        contatosFiltrados.clear();
        contatosFiltrados.addAll(contatos);
        listView.invalidateViews();
    }

    @Override
    public void refreshList(List<Contato> contatos) {
        this.contatos = contatos;
        contatosFiltrados = contatos;
        adaptador.setContatos(contatos);
    }

    @Override
    public void clicouNoContato(Contato contato) {
        Intent it = new Intent(this, DetalhesContatoActivity.class);
        it.putExtra(DetalhesContatoActivity.EXTRA_CONTATO,contato);
        startActivity(it);
    }
}
