package com.chs.contatos.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ActionMenuView;
import android.widget.EditText;
import android.widget.Toast;

import com.chs.contatos.R;
import com.chs.contatos.conexao.ContatoDAO;
import com.chs.contatos.model.Contato;

public class CadastroActivity extends AppCompatActivity {
    private EditText nome, email, endereco, fone;
    private ContatoDAO dao;
    private Contato contato = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        nome = findViewById(R.id.editTextNome);
        email = findViewById(R.id.editTextEmail);
        endereco = findViewById(R.id.editeTextEndereco);
        fone = findViewById(R.id.editTextFone);
        dao = new ContatoDAO(this);

        Intent i = getIntent();
        if(i.hasExtra("contato")){
            contato = (Contato) i.getSerializableExtra("contato");
            nome.setText(contato.getNome());
            fone.setText(contato.getTelefone());
            email.setText(contato.getEmail());
            endereco.setText(contato.getEndereco());
        }


    }

    public void salvar(View view){
        /*Contato c = new Contato();
        c.setNome(nome.getText().toString());
        c.setTelefone(fone.getText().toString());
        c.setEmail(email.getText().toString());
        c.setEndereco(endereco.getText().toString());
        dao.inserir(c);
        Toast.makeText(this, "Contato "+c.getNome()+" inserido com sucesso", Toast.LENGTH_LONG).show();*/
        if(contato == null) {
            contato = new Contato();
            //contato.setId(id);
            contato.setNome(nome.getText().toString());
            contato.setTelefone(fone.getText().toString());
            contato.setEmail(email.getText().toString());
            contato.setEndereco(endereco.getText().toString());
            dao.inserir(contato);
            Toast.makeText(this, "Contato " + contato.getNome() + " inserido com sucesso", Toast.LENGTH_LONG).show();
            finish();
        }
        if (contato !=null) {
            contato.setNome(nome.getText().toString());
            contato.setTelefone(fone.getText().toString());
            contato.setEmail(email.getText().toString());
            contato.setEndereco(endereco.getText().toString());
            dao.atualizar(contato);
            Toast.makeText(this, "Contato " + contato.getNome() + " atualizado com sucesso", Toast.LENGTH_LONG).show();
            finish();
        }
    }
}