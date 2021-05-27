package com.chs.contatos.activity;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chs.contatos.R;
import com.chs.contatos.model.Contato;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DetalhesContatoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DetalhesContatoFragment extends Fragment {

    public static final String TAG_DETALHE = "tagDetalhe";
    public static final String EXTRA_CONTATO = "contato";
    private TextView txtNome, txtTelefone, txtEmail, txtEndereco;
    private Contato contato;


    public DetalhesContatoFragment() {
        super(R.layout.fragment_detalhes_contato);
    }

    public static DetalhesContatoFragment newInstance(Contato contato){
        Bundle args = new Bundle();
        args.putSerializable(EXTRA_CONTATO, contato);
        DetalhesContatoFragment fragment = new DetalhesContatoFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        contato = (Contato) getArguments().getSerializable(EXTRA_CONTATO);
             setHasOptionsMenu(true);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bindViews(view);
    }

    private void bindViews(View view){
        txtNome = view.findViewById(R.id.txtFNome);
        txtTelefone = view.findViewById(R.id.txtFTelefone);
        txtEmail = view.findViewById(R.id.txtFEmail);
        txtEndereco = view.findViewById(R.id.txtFEndereco);

        if(contato != null){
            txtNome.setText(contato.getNome());
            txtTelefone.setText(contato.getTelefone());
            txtEmail.setText(contato.getEmail());
            txtEndereco.setText(contato.getEndereco());
        }
    }
}