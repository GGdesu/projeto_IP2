package ticTacThink.gui;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import ticTacThink.GerenciadorPrincipal;
import ticTacThink.aplicacao.beans.Usuario;

public class PerfilUsuarioController implements Initializable {

	@FXML
	private Label labelNome;

	@FXML
	private Label labelNasc;

	@FXML
	private Label labelGen;

	@FXML
	private Label labelPais;

	@FXML
	private Label labelPon;

	@FXML
	private Label labelEmail;

	private Usuario u = GerenciadorPrincipal.getUsuarioInstancia();

	@FXML
	void voltar() {
		App.mudarTela("Menu");
	}

	@FXML
	void editarPerfil() {
		App.mudarTela("EditarPerfil");
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		carregarValores();
	}

	private void carregarValores() {
		
		labelNome.setText(u.getNome());
		labelNasc.setText(u.getDataNasc());
		labelGen.setText(u.getGenero());
		labelPais.setText(u.getPais());
		labelPon.setText(String.valueOf(u.getPontuacao()));
		labelEmail.setText(u.getEmail());
	}

}
