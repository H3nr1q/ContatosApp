package com.chs.contatos.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.ListFragment;

import com.chs.contatos.R;
import com.chs.contatos.adapter.ContatoAdapter;
import com.chs.contatos.model.Contato;

import java.util.ArrayList;
import java.util.List;

public class ContatoListFragment extends ListFragment implements MainPresenter.MainView {
    List<Contato> mContato;
    ContatoAdapter contatoAdapter;
    private MainPresenter mainPresenter;
    private ListView listView;
    private List<Contato> contatosFiltrados = new ArrayList<>();
    private Activity MainActivity;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mainPresenter = new MainPresenter((MainPresenter.MainView) this);
        listView = listView.findViewById(R.id.lv_contatos);
        contatoAdapter = new ContatoAdapter(MainActivity,contatosFiltrados);
        listView.setAdapter(contatoAdapter);


    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        Activity activity = getActivity();
        if(activity instanceof AoClicarNoContato){
            Contato contato = (Contato) l.getItemAtPosition(position);

            AoClicarNoContato listener = (AoClicarNoContato)activity;
            listener.clicouNoContato(contato);

        }
    }

    @Override
    public void refreshList(List<Contato> contatos) {
        mContato = contatos;
        contatosFiltrados = contatos;
        contatoAdapter.setContatos(contatos);
    }

    public interface AoClicarNoContato{
        void clicouNoContato(Contato contato);
    }
}