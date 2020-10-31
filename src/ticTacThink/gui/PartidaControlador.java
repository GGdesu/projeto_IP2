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
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import ticTacThink.GerenciadorPrincipal;
import ticTacThink.aplicacao.beans.Pergunta;

public class PartidaControlador implements Initializable {

    @FXML
    private Label perguntaLabel;
    @FXML
    private VBox respostasVBox;

    @FXML
    private VBox popupFimDoTempo;
    
    @FXML
    private Label perguntasRestantes;
    
    @FXML
    private Label tempoRestante;

    private Pergunta perguntaAtual;
    Timeline animation;
    int t = 20;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        popupFimDoTempo.setVisible(true);

        animation = new Timeline(new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                atualizarContador();
                if (t-- == 0) {
                    fimDoTempo();
                    t = 20;
                }
            }
        }));
        animation.setCycleCount(21);
        System.out.println(animation.getDelay());
    }
    
    private void fimDoTempo() {
        popupFimDoTempo.setVisible(true);
        GerenciadorPrincipal.getPartida().responderPergunta(null);
    }
    private void atualizarContador() {
        tempoRestante.setText(String.valueOf(t));
    }

    void responder(String resposta) {
        GerenciadorPrincipal.getPartida().responderPergunta(resposta);
        proximaPergunta();
    }

    @FXML
    void proximaPergunta() {
        popupFimDoTempo.setVisible(false);
        animation.playFromStart();
        t = 20;
        perguntaAtual = GerenciadorPrincipal.getPartida().pegarPergunta();

        perguntaLabel.setText(perguntaAtual.getTexto());

        respostasVBox.getChildren().clear();

        for (String text : perguntaAtual.getRespostas()) {
            
            var button = new Button(text);
            button.setOnAction(value -> {
                responder(text);
            });
            button.getStyleClass().add("button-resposta");
            respostasVBox.getChildren().add(button);
        }
        
        perguntasRestantes.setText(String.valueOf(
                GerenciadorPrincipal.getPartida().numPerguntasRestante()));
    }
    
    @FXML
    void sair() {
        GerenciadorPrincipal.getInstance().finalizarPartida();
        App.mudarTela("Menu");
    }

}
