package ticTacThink.gui;

import javafx.fxml.FXML;
import ticTacThink.GerenciadorPrincipal;

public class MenuControlador {
    
    @FXML 
    void iniciar() {
        App.mudarTela("ConfigPartida");
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
    void logout() {
    	GerenciadorPrincipal.getInstance().logout();
        App.mudarTela("Login");
    }
}
