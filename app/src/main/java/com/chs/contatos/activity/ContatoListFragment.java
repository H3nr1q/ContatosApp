package com.chs.contatos.activity;

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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.chs.contatos.R;
import com.chs.contatos.adapter.ContatoAdapter;
import com.chs.contatos.app.DeviceUtils;
import com.chs.contatos.conexao.ContatoDAO;
import com.chs.contatos.model.Contato;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class ContatoListFragment extends Fragment implements MainPresenter.MainView {
    private ListView listView;
    private List<Contato> contatos = new ArrayList<>();
    private List<Contato> contatosFiltrados = new ArrayList<>();
    private MainPresenter mainPresenter;
    private ContatoAdapter adaptador;
    FloatingActionButton fab;

    public ContatoListFragment() {
        super(R.layout.fragment_lista_contato);
        setHasOptionsMenu(true);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bindViews(view);
        mainPresenter = new MainPresenter(this);
        //mainPresenter.listarContatos();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_principal, menu);
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
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
//        MenuInflater i = requireActivity().getMenuInflater();
//        i.inflate(R.menu.menu_contexto_item, menu);
        menu.add(Menu.NONE, R.id.editar, Menu.NONE, "Editar");
        menu.add(Menu.NONE, R.id.excluir, Menu.NONE, "Excluir");
    }

    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.editar:
                AdapterView.AdapterContextMenuInfo menuEditar = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
                final Contato contatoEditar = contatosFiltrados.get(menuEditar.position);
                Intent i = new Intent(requireContext(), CadastroActivity.class);
                i.putExtra("contato", contatoEditar);
                startActivity(i);

                return true;
            case R.id.excluir:
                AdapterView.AdapterContextMenuInfo menuExcluir = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
                Contato contatoExcluir = contatosFiltrados.get(menuExcluir.position);

                AlertDialog dialog = new AlertDialog.Builder(requireContext())
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


                return true;
        }
        return super.onContextItemSelected(item);
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

    private void bindViews(View view) {
        listView = view.findViewById(R.id.lv_contatos);
        adaptador = new ContatoAdapter(requireActivity(), contatosFiltrados);
        listView.setAdapter(adaptador);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Contato contato = (Contato) adapterView.getAdapter().getItem(position);

                /* Jeito rapido de fazer */
//                int container = ((MainActivity) requireActivity()).isTablet() ? R.id.detalheT : R.id.container;

                /* Jeito mais bonito de fazer */
                int container = DeviceUtils.isTablet(requireContext()) ? R.id.detalheT : R.id.container;

                DetalhesContatoFragment fragment = DetalhesContatoFragment.newInstance(contato);
                FragmentManager fm = requireActivity().getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(container, fragment, DetalhesContatoFragment.class.getSimpleName());
                ft.addToBackStack(null);
                ft.commit();
            }
        });

        fab = view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(requireContext(), CadastroActivity.class);
                startActivity(i);
            }
        });

        registerForContextMenu(listView);
    }

    public void posicionarContato(String nome) {
        contatosFiltrados.clear();
        for (Contato c : contatos) {
            if (c.getNome().toLowerCase().contains(nome.toLowerCase())) {
                contatosFiltrados.add(c);
            }
        }
        listView.invalidateViews();
    }

//    public void excluir(MenuItem item) {
//        AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
//        Contato contatoExcluir = contatosFiltrados.get(menuInfo.position);
//
//        AlertDialog dialog = new AlertDialog.Builder(requireContext())
//                .setTitle("Atenção")
//                .setMessage("Deseja excluir esse contato?")
//                .setNegativeButton("Não", null)
//                .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//                        contatosFiltrados.remove(contatoExcluir);
//                        contatos.remove(contatoExcluir);
//                        ContatoDAO.getInstance().excluir(contatoExcluir);
//                        listView.invalidateViews();
//                    }
//                }).create();
//        dialog.show();
//    }
//
//    public void editar(MenuItem item) {
//        AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
//        final Contato contatoEditar = contatosFiltrados.get(menuInfo.position);
//        Intent i = new Intent(requireContext(), CadastroActivity.class);
//        i.putExtra("contato", contatoEditar);
//        startActivity(i);
//
//    }

    public interface AoClicarContato{
        void clicouNoHotel(Contato contato);
    }
}
