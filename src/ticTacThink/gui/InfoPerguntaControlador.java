package ticTacThink.gui;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import ticTacThink.aplicacao.beans.PerguntaInfo;

public class InfoPerguntaControlador implements Initializable {

    private static PerguntaInfo perguntaInfo;

    @FXML
    private Label texto;
    @FXML
    private CheckBox respostaCBox;
    @FXML
    private Label alternativas;
    @FXML
    private Label categoria;
    @FXML
    private Label tipo;
    @FXML
    private Label dificuldade;
    @FXML
    private Label respostaCerta;
    @FXML
    private Label aparicoes;
    @FXML
    private Label acertos;

    public static void setPerguntaInfo(PerguntaInfo p) {
        perguntaInfo = p;
    }

    @FXML
    void toggleResposta() {
        respostaCerta.setVisible(!respostaCerta.isVisible());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (perguntaInfo != null) {
            var pergunta = perguntaInfo.getPergunta();
            var alternativasArray = pergunta.getRespostas();

            var alternativasText = "";
            for (String string : alternativasArray) {
                alternativasText += string + "\n";
            }
            texto.setText(new String(pergunta.getTexto()));
            
            alternativas.setText(alternativasText);
            
            respostaCerta.setText(alternativasArray[pergunta.getCerta()]);
    
            tipo.setText(pergunta.getTipo());
            categoria.setText(pergunta.getCategoria());
            dificuldade.setText(pergunta.getDificuldade());
    
            acertos.setText(String.format("%d vezes.", perguntaInfo.getAcertos()));
            aparicoes.setText(String.format("%d vezes.", perguntaInfo.getAparicoes()));
        }

    }
}
