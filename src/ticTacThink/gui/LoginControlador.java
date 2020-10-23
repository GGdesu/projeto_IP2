package ticTacThink.gui;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class LoginControlador {
    @FXML
    TextField email;
    @FXML
    TextField senha;

    @FXML
    void entrar() {
        App.mudarTela("Menu");
    }
    @FXML
    void sair() {
        
    }
}