package ticTacThink.gui;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import ticTacThink.GerenciadorPrincipal;

public class ConfigPartidaControlador implements Initializable {

    @FXML
    private ComboBox<String> categoriaComboBox;
    @FXML
    private ComboBox<String> dificuldadeComboBox;
    @FXML
    private ComboBox<String> tipoComboBox;

    boolean ranqueada = false;

    @FXML
    void voltar() {
        App.mudarTela("Menu");
    }

    @FXML
    void rankedSelecionada() {
        ranqueada = true;
        categoriaComboBox.setDisable(true);
        dificuldadeComboBox.setDisable(true);
        tipoComboBox.setDisable(true);
    }

    @FXML
    void casualSelecionada() {
        ranqueada = false;
        categoriaComboBox.setDisable(false);
        dificuldadeComboBox.setDisable(false);
        tipoComboBox.setDisable(false);
    }

    @FXML
    void iniciar() {
        var tipo = tipoComboBox.getSelectionModel().getSelectedItem();
        var categoria = categoriaComboBox.getSelectionModel().getSelectedItem();
        var dificuldade = dificuldadeComboBox.getSelectionModel().getSelectedItem();
        var perguntas = GerenciadorPrincipal.getInstance().baixarPerguntas(15, categoria, dificuldade, tipo);
        GerenciadorPrincipal.getInstance().criarPartida(ranqueada, perguntas);
        App.mudarTela("Partida");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        dificuldadeComboBox.setItems(FXCollections.observableArrayList("easy", "medium", "hard"));
        tipoComboBox.setItems(FXCollections.observableArrayList("multiple", "boolean"));
        categoriaComboBox.setItems(FXCollections.observableArrayList(GerenciadorPrincipal.getInstance().getCategoriasDisponiveis()));
    }
}
