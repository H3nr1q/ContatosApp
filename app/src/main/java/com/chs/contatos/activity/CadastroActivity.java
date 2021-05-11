package com.chs.contatos.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.chs.contatos.R;
import com.chs.contatos.conexao.ContatoDAO;
import com.chs.contatos.model.Contato;

public class CadastroActivity extends AppCompatActivity {
    private EditText nome, email, endereco, fone;
    private ContatoDAO dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        nome = findViewById(R.id.editTextNome);
        email = findViewById(R.id.editTextEmail);
        endereco = findViewById(R.id.editeTextEndereco);
        fone = findViewById(R.id.editTextFone);
        dao = new ContatoDAO(this);

    }

    public void salvar(View view){
        Contato c = new Contato();
        c.setNome(nome.getText().toString());
        c.setTelefone(fone.getText().toString());
        c.setEmail(email.getText().toString());
        c.setEndereco(endereco.getText().toString());
        long id = dao.inserir(c);
        Toast.makeText(this, "Contato "+c.getNome()+" inserido com sucesso", Toast.LENGTH_LONG).show();
        finish();

    }
}