package ticTacThink.gui;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import ticTacThink.GerenciadorPrincipal;

public class ConfigPartidaControlador implements Initializable {

    @FXML
    private ChoiceBox<String> categoriaChoiceBox;
    @FXML
    private ChoiceBox<String> dificuldadeChoiceBox;
    @FXML
    private ChoiceBox<String> tipoChoiceBox;

    @FXML
    void voltar() {
        App.mudarTela("Menu");
    }

    @FXML
    void rankedSelecionada() {
        categoriaChoiceBox.setDisable(true);
        dificuldadeChoiceBox.setDisable(true);
        tipoChoiceBox.setDisable(true);
    }

    @FXML
    void casualSelecionada() {
        categoriaChoiceBox.setDisable(false);
        dificuldadeChoiceBox.setDisable(false);
        tipoChoiceBox.setDisable(false);
    }

    @FXML
    void iniciar() {
        App.mudarTela("Login");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        dificuldadeChoiceBox.setItems(FXCollections.observableArrayList("easy", "medium", "hard"));
        tipoChoiceBox.setItems(FXCollections.observableArrayList("multiple", "boolean"));
        categoriaChoiceBox.setItems(FXCollections.observableArrayList(GerenciadorPrincipal.getInstance().getCategoriasDisponiveis()));
    }
}
