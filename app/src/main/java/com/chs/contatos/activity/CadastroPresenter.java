package com.chs.contatos.activity;

import android.widget.Toast;

import com.chs.contatos.conexao.ContatoDAO;
import com.chs.contatos.model.Contato;

public class CadastroPresenter {
    private final CadastroView cadastroView;


    public CadastroPresenter (CadastroView view){
        cadastroView = view;
    }

    public void salvarContatos(Contato contato){
        if(contato.getId() == null) {
            ContatoDAO.getInstance().inserir(contato);
            cadastroView.exibirMensgagem("Contato " + contato.getNome() + " inserido com sucesso");
        }else {
            if (contato.getId() !=null) {
              ContatoDAO.getInstance().atualizar(contato);
              cadastroView.exibirMensgagem("Contato " + contato.getNome() + " atualizado com sucesso");
            }

        }
    }


    interface CadastroView{

        void exibirMensgagem(String msg);

    }

}
