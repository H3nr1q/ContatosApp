package com.chs.contatos.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ActionMenuView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.Toast;

import com.chs.contatos.R;
import com.chs.contatos.app.MaskEditUtil;
import com.chs.contatos.app.ValidarEmailUtils;
import com.chs.contatos.conexao.ContatoDAO;
import com.chs.contatos.model.Contato;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class CadastroActivity extends AppCompatActivity implements CadastroPresenter.CadastroView {
    private EditText nome, email, endereco, fone;
    private Contato contato = null;
    private Button btnSalvar;
    private CadastroPresenter cadastroPresenter;
    private MenuItem chkSalvar;
    FloatingActionButton fabSalvar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);
        bindViews();
        Intent i = getIntent();
        if(i.hasExtra("contato")){
            contato = (Contato) i.getSerializableExtra("contato");
            nome.setText(contato.getNome());
            fone.setText(contato.getTelefone());
            email.setText(contato.getEmail());
            endereco.setText(contato.getEndereco());
        }
        cadastroPresenter = new CadastroPresenter(this);
    }



    private void bindViews(){
        nome = findViewById(R.id.editTextNome);
        email = findViewById(R.id.editTextEmail);
        endereco = findViewById(R.id.editeTextEndereco);
        fone = findViewById(R.id.editTextFone);
        fone.addTextChangedListener(MaskEditUtil.mask(fone, MaskEditUtil.FORMAT_FONE));
        fabSalvar = findViewById(R.id.fabSalvar);
        fabSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String txtNome = nome.getText().toString();
                String txtTelefone = fone.getText().toString();
                String txtEmail = email.getText().toString();
                String txtEndereco = endereco.getText().toString();

                if(!txtNome.isEmpty()) {
                    if (!txtTelefone.isEmpty()) {
                        if (!txtEmail.isEmpty()) {
                            if (!txtEndereco.isEmpty()) {
                                if (txtEmail!=null){
                                    ValidarEmailUtils validarEmailUtils = new ValidarEmailUtils();
                                    if(validarEmailUtils.isValidEmailAddressRegex(txtEmail)){
                                        salvar();
                                        finish();
                                    }else{
                                        Toast.makeText(CadastroActivity.this,
                                                "Email inv??lido",
                                                Toast.LENGTH_SHORT).show();
                                    }

                                }

                            } else {
                                Toast.makeText(CadastroActivity.this,
                                        "Preencha o endere??o.",
                                        Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(CadastroActivity.this,
                                    "Preencha o e-mail.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(CadastroActivity.this,
                                "Preencha o telefone.",
                                Toast.LENGTH_SHORT).show();


                    }
                }else {
                    Toast.makeText(CadastroActivity.this,
                            "Preencha o nome.",
                            Toast.LENGTH_SHORT).show();
                }
            }

        });

    }


    public void salvar(){
        if(contato == null) {
            contato = new Contato();
            contato.setNome(nome.getText().toString());
            contato.setTelefone(fone.getText().toString());
            contato.setEmail(email.getText().toString());
            contato.setEndereco(endereco.getText().toString());
//            ContatoDAO.getInstance().inserir(contato);
//            Toast.makeText(this, "Contato " + contato.getNome() + " inserido com sucesso", Toast.LENGTH_LONG).show();
        }else {
            if (contato.getId() !=null) {
                contato.setNome(nome.getText().toString());
                contato.setTelefone(fone.getText().toString());
                contato.setEmail(email.getText().toString());
                contato.setEndereco(endereco.getText().toString());
//                ContatoDAO.getInstance().atualizar(contato);
//                Toast.makeText(this, "Contato " + contato.getNome() + " atualizado com sucesso", Toast.LENGTH_LONG).show();
            }

        }
        cadastroPresenter.salvarContatos(contato);

    }

    @Override
    public void exibirMensgagem(String msg) {
        Toast.makeText(CadastroActivity.this, msg, Toast.LENGTH_SHORT).show();
    }
}