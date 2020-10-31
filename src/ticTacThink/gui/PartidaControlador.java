package ticTacThink.gui;

import java.net.URL;
import java.util.NoSuchElementException;
import java.util.ResourceBundle;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import ticTacThink.GerenciadorPrincipal;
import ticTacThink.aplicacao.beans.Pergunta;

public class PartidaControlador implements Initializable {
    
    private final int TEMPO_POR_PERGUNTA = 20;
    private final int TEMPO_PARA_INICIAR = 5;
    
    // Trakers
    private int segundos = TEMPO_PARA_INICIAR;
    private Pergunta perguntaAtual;
    private Timeline contador;
    private boolean iniciando = true;

    @FXML private Label perguntaLabel;
    @FXML private VBox respostasVBox;
    @FXML private VBox popup;
    @FXML private Label popupTexto;
    @FXML private HBox popupInicial;
    
    // Contadores
    @FXML private Label perguntasRestantes;
    @FXML private Label tempoRestante;

    // Info da partida
    @FXML private Label categoria;
    @FXML private Label dificuldade;
    @FXML private Label tipo;
    @FXML private Button botaoIniciar;

    @FXML private HBox barraInferior;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        barraInferior.setVisible(false);

        tipo.setText(GerenciadorPrincipal.getPartida().getTipo());
        categoria.setText(GerenciadorPrincipal.getPartida().getCategoria());
        dificuldade.setText(GerenciadorPrincipal.getPartida().getDificuldade());


        // A cada Segundo diminui contagem para iniciar a partida
        contador = new Timeline(new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                botaoIniciar.setText("Iniciar " + segundos);
                if (segundos-- == 0) {
                    iniciar();
                }
            }
        }));
        contador.setCycleCount(TEMPO_POR_PERGUNTA + 1);
        popup.getStyleClass().add("popup-info");
        popupInicial.setVisible(true);
        contador.playFromStart();
    }


    private void mostrarPopup(boolean visibilidade, String mensagem) {
        popupTexto.setText(mensagem);
        popup.setVisible(visibilidade);
        respostasVBox.setDisable(visibilidade);
    }
    

    private void fimDoTempo() {
        System.out.println("Tempo Esgotado!");

        mostrarPopup(true, "Tempo Esgotado!");
        popup.getStyleClass().set(0, "popup-info");
        GerenciadorPrincipal.getPartida().responderPergunta(null);
    }


    private void atualizarContador() {
        tempoRestante.setText(String.valueOf(segundos));
    }


    private void responder(String resposta) {
        System.out.println("Resposta enviada.");

        contador.stop();
        boolean acertou = GerenciadorPrincipal.getPartida().responderPergunta(resposta);
        if (acertou) {
            popup.getStyleClass().set(0, "popup-resposta-certa");
            mostrarPopup(true, "Você Acertou!");
        } else {
            popup.getStyleClass().set(0, "popup-resposta-errada");
            mostrarPopup(true, "Você Errou");
        }
        for (var a : popup.getStyleClass()) {
            System.out.println(a);
        }
    }


    /* 
     * iniciar remove a tela inicial,
     * atribuir a um novo eventHandler ao contador e
     * e chama a proxima pergunta
     */
    @FXML
    private void iniciar() {
        System.out.println("Iniciando Partida.");
        
        contador.stop();
        popupInicial.setVisible(false);
        barraInferior.setVisible(true);

        // A cada segundo diminui a contagem regressiva de cada pergunta
        contador.setOnFinished(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
                atualizarContador();
                if (segundos-- == 0) {
                    fimDoTempo();
                    segundos = TEMPO_POR_PERGUNTA;
                }
            }
        });
        proximaPergunta();
    }


    /* 
     * Limpa a tela e mostra os resultados
     */
    @FXML
    private void finalizar() {
        System.out.println("Finalizando Partida.");
        
        popup.getStyleClass().set(0, "popup-info");
        mostrarPopup(true, "Fim da Partida\nPontuação: "+ GerenciadorPrincipal.getPartida().getPontuacao());
        contador.stop();

        GerenciadorPrincipal.getInstance().finalizarPartida();
        barraInferior.setVisible(false);
        Button popupButton = (Button) popup.getChildren().get(1);
        popupButton.getStyleClass().add("botao-grande");
        popupButton.setText("Voltar ao Menu");
        popupButton.setOnAction(a -> {
            App.mudarTela("Menu");
        });

    }

    @FXML
    void proximaPergunta() {
        mostrarPopup(false, "");
        segundos = TEMPO_POR_PERGUNTA;
        contador.playFromStart();

        try {
            perguntaAtual = GerenciadorPrincipal.getPartida().pegarPergunta();
        } catch (NoSuchElementException e) {
            finalizar();
        }

        // Atualiza todos os elementos da tela para a nova pergunta
        perguntaLabel.setText(perguntaAtual.getTexto());
        respostasVBox.getChildren().clear();
        
        for (String text : perguntaAtual.getRespostas()) {
            var button = new Button(text);
            button.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    responder(text);
                }
            });
            button.getStyleClass().add("button-resposta");
            respostasVBox.getChildren().add(button);
        }
        
        perguntasRestantes.setText(String.valueOf(
                GerenciadorPrincipal.getPartida().numPerguntasRestante()));
    }
}
