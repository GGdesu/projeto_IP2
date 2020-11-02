package ticTacThink.gui;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import ticTacThink.GerenciadorPrincipal;
import ticTacThink.aplicacao.beans.Rank;

public class RankController implements Initializable {

	// classe que representa uma linha de informação sobre rank
	public class RankLinha {
		private String nome;
		private Integer pontuacao;
		private Integer posicao;

		public RankLinha(String nome, Integer pontuacao, Integer posicao) {
			this.nome = nome;
			this.pontuacao = pontuacao;
			this.posicao = posicao;
		}
		public String getNome() { return nome; }
		public Integer getPontuacao() { return pontuacao; }
		public Integer getPosicao() { return posicao; }
	}

	@FXML
	private TableView<RankLinha> tvRank;

	private List<Rank> rank = new ArrayList<>();

	@FXML
	private TableColumn<RankLinha, String> nomeCol;

	@FXML
	private TableColumn<RankLinha, Integer> posCol;

	@FXML
	private TableColumn<RankLinha, Integer> pontCol;

	@FXML
	void voltar() {
		App.mudarTela("Menu");
	}

	@FXML
	void estatisticasPergunta() {
		App.mudarTela("EstatisticasPergunta");
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		carregarRank();
	}

	private void carregarRank() {

		nomeCol.setCellValueFactory(new PropertyValueFactory<>("nome"));
		posCol.setCellValueFactory(new PropertyValueFactory<>("posicao"));
		pontCol.setCellValueFactory(new PropertyValueFactory<>("pontuacao"));
		
		rank = GerenciadorPrincipal.getInstance().rank();

		RankLinha[] lista = new RankLinha[rank.size()];
		int i = 0;
		for (Rank r : rank) {
			lista[i] = new RankLinha(r.getNome(), r.getPontuacao(), i+1);
			i++;
		}
		tvRank.setItems(FXCollections.observableArrayList(lista));
	}
}
