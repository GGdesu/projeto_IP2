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

	@FXML
	private TableView<Rank> tvRank;

	private ObservableList<Rank> obsRank;
	
	private List<Rank> rank = new ArrayList<>();

	@FXML
	private TableColumn<Rank, String> nomeCol;

	@FXML
	private TableColumn<Rank, Integer> posCol;

	@FXML
	private TableColumn<Rank, Integer> pontCol;
	
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
		
		obsRank = FXCollections.observableArrayList(rank);

		tvRank.setItems(obsRank);
	}
}
