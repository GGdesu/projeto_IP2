package ticTacThink.gui;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import ticTacThink.GerenciadorPrincipal;
import ticTacThink.aplicacao.beans.PerguntaInfo;

public class EstatisticasPerguntaControlador implements Initializable {

    public class PerguntaInfoLinha {
        private String pergunta;
        private Integer aparicoes;
        private Float percentual;

        public PerguntaInfoLinha(PerguntaInfo pInfo) {
            this.pergunta = new String(pInfo.getPergunta().getTexto());
            this.aparicoes = pInfo.getAparicoes();
            try {
                this.percentual = 100.0f * pInfo.getAcertos() / (float)pInfo.getAparicoes();
            } catch (ArithmeticException e) {
                this.percentual = 0.0f;
            }
        }
        public String getPergunta() {
            return pergunta;
        }
        public Integer getAparicoes() {
            return aparicoes;
        }
        public Float getPercentual() {
            return percentual;
        }
    }
    
    @FXML
    private TableView<PerguntaInfoLinha> tabela;
    @FXML
    private TableColumn<PerguntaInfoLinha, String> colunaPerguntas;
    @FXML
    private TableColumn<PerguntaInfoLinha, Integer> colunaAparicoes;
    @FXML
    private TableColumn<PerguntaInfoLinha, Float> colunaPercentual;

    @FXML
    private ComboBox<String> categorias;

    private List<PerguntaInfo> perguntasMostradas;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        categorias.setItems(FXCollections.observableArrayList(GerenciadorPrincipal.getInstance().getCategoriasDisponiveis()));
        categorias.getItems().add(0, "Categorias (Todas)");
        categorias.setValue("Categorias (Todas)");
        
        colunaPerguntas.setCellValueFactory(new PropertyValueFactory<>("pergunta"));
        colunaAparicoes.setCellValueFactory(new PropertyValueFactory<>("aparicoes"));
        colunaPercentual.setCellValueFactory(new PropertyValueFactory<>("percentual"));
        colunaPercentual.setCellFactory(column -> {
            return new TableCell<PerguntaInfoLinha, Float>() {
                @Override
                protected void updateItem(Float item, boolean empty) {
                    super.updateItem(item, empty);
                    super.alignmentProperty().set(Pos.CENTER_RIGHT);
                    if(empty) {
                        setText(null);
                    } else {
                        this.setText(String.format("%.1f%%", item));
                        this.setGraphic(null);
                    }
                }
            };
        });

        perguntasMostradas = GerenciadorPrincipal.getInstance().estatisticasPerguntas();
        atualizarTabela();
    }

    private void atualizarTabela() {
        tabela.setItems(FXCollections.observableArrayList(paraPerguntaInfoLinha(perguntasMostradas)));
    }

    @FXML
    private void filtrarCategoria() {
        String selected = categorias.getSelectionModel().getSelectedItem();
        List<PerguntaInfo> perguntas = GerenciadorPrincipal.getInstance().estatisticasPerguntas();
        if (selected.equals("Categorias (Todas)")) {
            this.perguntasMostradas = perguntas;
        } else {
            this.perguntasMostradas = new ArrayList<PerguntaInfo>(perguntas.size()/2);
            for (PerguntaInfo perguntaInfo : perguntas) {
                if (perguntaInfo.getPergunta().getCategoria().equals(selected)) {
                    this.perguntasMostradas.add(perguntaInfo);
                }
            }
        }
        atualizarTabela();
    }

    private PerguntaInfoLinha[] paraPerguntaInfoLinha(List<PerguntaInfo> perguntas) {
        var resultados = new PerguntaInfoLinha[perguntas.size()];
        for (int i = 0; i < resultados.length; i++) {
            resultados[i] = new PerguntaInfoLinha(perguntas.get(i));
        }
        return resultados;
    }
    
    @FXML
    void voltar() {
        App.mudarTela("Rank");
    }
}
