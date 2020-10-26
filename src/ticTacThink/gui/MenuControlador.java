package ticTacThink.gui;

import javafx.fxml.FXML;
import ticTacThink.GerenciadorPrincipal;

public class MenuControlador {
    
    @FXML 
    void iniciar() {
        
    }

    @FXML 
    void perfil() {
    	App.mudarTela("PerfilUsuario");

    }

    @FXML 
    void ranking() {
        App.mudarTela("Rank");        

    }
    
    @FXML 
    void sair() {
        System.exit(0);
    }

    @FXML 
    void logout() {
    	GerenciadorPrincipal.getInstance().logout();
        App.mudarTela("Login");
    }
}
