package com.chs.contatos.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.chs.contatos.R;
import com.chs.contatos.model.Contato;

import java.util.List;

public class ContatoAdapter extends BaseAdapter {
    private List<Contato> contatos;
    private Activity activity;

    public ContatoAdapter(Activity activity, List<Contato> contatos) {
        this.contatos = contatos;
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return contatos.size();
    }

    @Override
    public Object getItem(int i) {
        return contatos.get(i);
    }

    @Override
    public long getItemId(int i) {
        return contatos.get(i).getId();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v = activity.getLayoutInflater().inflate(R.layout.contato, viewGroup, false);
        TextView nome = v.findViewById(R.id.txtNome);
        TextView telefone = v.findViewById(R.id.txtTelefone);
        TextView email = v.findViewById(R.id.txtEmail);
        Contato contato = contatos.get(i);
        nome.setText(contato.getNome());
        telefone.setText(contato.getTelefone());
        email.setText(contato.getEmail());
        return v;
    }
}
