package com.chs.contatos.activity;

import com.chs.contatos.conexao.ContatoDAO;
import com.chs.contatos.model.Contato;

import java.util.List;

public class MainPresenter {

    private MainView mainView;
    private List<Contato> contatos;


    public MainPresenter(MainView view) {
        mainView = view;
    }

    public void listarContatos(){
        contatos =  ContatoDAO.getInstance().listaContatos();
        mainView.refreshList(contatos);

    }

    interface MainView{
        void refreshList(List<Contato> contatos);

    }

}
