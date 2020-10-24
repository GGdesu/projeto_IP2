package ticTacThink.gui;

import javafx.fxml.FXML;

public class MenuControlador {
    
    @FXML 
    void iniciar() {
        
    }

    @FXML 
    void perfil() {

    }

    @FXML 
    void ranking() {

    }
    
    @FXML 
    void sair() {
        System.exit(0);
    }

    @FXML 
    void logout() {
        App.mudarTela("Login");
    }
}
