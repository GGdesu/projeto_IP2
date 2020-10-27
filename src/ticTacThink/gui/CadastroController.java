package ticTacThink.gui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;

public class CadastroController {

    @FXML
    private TextField txtfieldNome;

    @FXML
    private TextField txtfieldEmail;

    @FXML
    private TextField txtfieldConfSenha;

    @FXML
    private TextField txtfieldData;

    @FXML
    private Button btVoltar;

    @FXML
    private Button btCadastrar;

    @FXML
    private TextField txtfieldSenha;

    @FXML
    private ToggleGroup generoGrupo;
    
    @FXML
    private TextField txtfieldPais;

    /*@FXML
    void 545454(ActionEvent event) {

    }*/
    
    @FXML 
    void voltar() {
    	App.mudarTela("Login");
    }
    
    @FXML
    void editar() {
    	System.out.println("Tentando mudar de tela.");
    	
    }
    
}
