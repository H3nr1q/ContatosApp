package com.chs.contatos.activity;

import android.os.Bundle;

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
    private ConstraintLayout layout;
    private Contato contato;
    Contato mContato;


    public DetalhesContatoFragment() {
        // Required empty public constructor
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
             mContato = (Contato) getArguments().getSerializable(EXTRA_CONTATO);
             setHasOptionsMenu(true);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_detalhes_contato,container,false);
        bindViews();
        if(mContato != null){
            txtNome.setText(mContato.getNome());
            txtTelefone.setText(mContato.getTelefone());
            txtEmail.setText(mContato.getEmail());
            txtEndereco.setText(mContato.getEndereco());
        }
        return layout;
    }

    private void bindViews(){
        txtNome = layout.findViewById(R.id.txtFNome);
        txtTelefone = layout.findViewById(R.id.txtFTelefone);
        txtEmail = layout.findViewById(R.id.txtFEmail);
        txtEndereco = layout.findViewById(R.id.txtFEndereco);


    }


}