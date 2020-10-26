package ticTacThink.gui;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import ticTacThink.GerenciadorPrincipal;

public class LoginControlador {
    @FXML
    TextField email;
    @FXML
    TextField senha;

    @FXML
   void entrar() {
    	GerenciadorPrincipal.getInstance().login(email.getText(), senha.getText());
    	if(GerenciadorPrincipal.getUsuarioInstancia() != null) {
    		App.mudarTela("Menu");
    		System.out.println(GerenciadorPrincipal.getUsuarioInstancia().getNome());
    	}else {
    		System.out.println("Usuario nao existe");
    	}
        
    }
    
    @FXML
    void sair() {
        
    }
    
    @FXML
    void cadastrar() {
    	App.mudarTela("Cadastro");
    }
    
}