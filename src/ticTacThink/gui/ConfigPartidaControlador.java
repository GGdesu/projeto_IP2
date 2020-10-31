package ticTacThink.gui;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import ticTacThink.GerenciadorPrincipal;
import ticTacThink.aplicacao.beans.Pergunta;

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
        boolean todosTipos = tipoComboBox.getSelectionModel().getSelectedIndex() == 0;
        boolean todasCategorias = categoriaComboBox.getSelectionModel().getSelectedIndex() == 0;
        boolean todasDificuldades = dificuldadeComboBox.getSelectionModel().getSelectedIndex() == 0;

        var tipo = tipoComboBox.getSelectionModel().getSelectedItem();
        var categoria = categoriaComboBox.getSelectionModel().getSelectedItem();
        var dificuldade = dificuldadeComboBox.getSelectionModel().getSelectedItem();

        List<Pergunta> perguntas;
        if (ranqueada) {
            perguntas = GerenciadorPrincipal.getInstance().baixarPerguntas(15);
        } else {
            perguntas = GerenciadorPrincipal.getInstance().baixarPerguntas(15, 
                todasCategorias ? null : categoria, 
                todasDificuldades ? null : dificuldade,
                todosTipos ? null : tipo);
        }
        GerenciadorPrincipal.getInstance().criarPartida(categoria, dificuldade, tipo, ranqueada, perguntas);
        App.mudarTela("PartidaInicio");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        dificuldadeComboBox.setItems(FXCollections.observableArrayList("Todas", "easy", "medium", "hard"));
        categoriaComboBox.setItems(FXCollections.observableArrayList(GerenciadorPrincipal.getInstance().getCategoriasDisponiveis()));
        tipoComboBox.setItems(FXCollections.observableArrayList("Todas", "multiple", "boolean"));

        categoriaComboBox.getItems().add(0, "Todas");
        
        dificuldadeComboBox.getSelectionModel().select(0);
        categoriaComboBox.getSelectionModel().select(0);
        tipoComboBox.getSelectionModel().select(0);
    }
}
