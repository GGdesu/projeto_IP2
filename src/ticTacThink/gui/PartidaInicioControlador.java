package ticTacThink.gui;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.util.Duration;
import ticTacThink.GerenciadorPrincipal;

public class PartidaInicioControlador implements Initializable {

    private int segundos = 5;
    private Timeline animation;

    @FXML
    private Label dificuldade;
    @FXML
    private Label categoria;
    @FXML
    private Label tipo;
    @FXML
    private Label qtdPerguntas;
    @FXML
    private Button contador;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        var partida = GerenciadorPrincipal.getPartida();
        var tipoString = partida.getTipo();
        var categoriaString = partida.getCategoria();
        var dificuldadeString = partida.getDificuldade();
        
        tipo.setText(tipoString);
        categoria.setText(categoriaString);
        dificuldade.setText(dificuldadeString);
        qtdPerguntas.setText("Perguntas: " + partida.numPerguntasRestante());
        
        animation = new Timeline(new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                contador.setText(String.valueOf("Iniciar " + segundos));
                if (segundos == 0) {
                    iniciar();
                }
                segundos--;
            }
        }));
        animation.setCycleCount(segundos + 1);
        animation.playFromStart();
    }

    @FXML
    void iniciar() {
        animation.stop();
        App.mudarTela("Partida");
    }

    @FXML
    void voltar() {
        animation.stop();
        App.mudarTela("ConfigPartida");
    }
}
