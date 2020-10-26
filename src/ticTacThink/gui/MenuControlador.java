package ticTacThink.gui;

import javafx.fxml.FXML;

public class MenuControlador {
    
    @FXML 
    void iniciar() {
        App.mudarTela("ConfigPartida");
    }

    @FXML 
    void perfil() {

    }

    @FXML 
    void ranking() {

    }
    
    @FXML 
    void logout() {
        App.mudarTela("Login");
    }
}
