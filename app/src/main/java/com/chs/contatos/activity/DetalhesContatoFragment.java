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

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String TAG_DETALHE = "tagDetalhe";
    private static final String EXTRA_CONTATO = "contato";
    private TextView txtNome, txtTelefone, txtEmail, txtEndereco;
    private ConstraintLayout layout;
    private Contato contato;


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public DetalhesContatoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param Contato Parameter 1.
     * @param contato Parameter 2.
     * @return A new instance of fragment DetalhesContatoFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DetalhesContatoFragment newInstance(String Contato, String contato){
        DetalhesContatoFragment fragment = new DetalhesContatoFragment();
        Bundle args = new Bundle();
        args.putSerializable(EXTRA_CONTATO, contato);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
             contato = (Contato) getArguments().getSerializable(EXTRA_CONTATO);
             setHasOptionsMenu(true);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        bindViews();
        if(contato != null){
            txtNome.setText(contato.getNome());
            txtTelefone.setText(contato.getTelefone());
            txtEmail.setText(contato.getEmail());
            txtEndereco.setText(contato.getEndereco());
        }
        return inflater.inflate(R.layout.fragment_detalhes_contato, container, false);
    }

    private void bindViews(){
        txtNome = layout.findViewById(R.id.txtFNome);
        txtTelefone = layout.findViewById(R.id.txtFTelefone);
        txtEmail = layout.findViewById(R.id.txtFEmail);
        txtEndereco = layout.findViewById(R.id.txtFEndereco);

    }


}