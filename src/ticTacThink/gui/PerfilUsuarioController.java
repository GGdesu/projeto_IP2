package ticTacThink.gui;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import ticTacThink.GerenciadorPrincipal;

public class PerfilUsuarioController implements Initializable{

	@FXML
	private Label lbNome;

	@FXML
	private Label lbIdade;

	@FXML
	private Label lbGenero;

	@FXML
	private Label lbPais;

	@FXML
	private Label lbPontuacao;

	@FXML
	private Label lbEmail;
	
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
		lbNome.setText(GerenciadorPrincipal.getUsuarioInstancia().getNome());
		lbIdade.setText(String.valueOf(GerenciadorPrincipal.getUsuarioInstancia().getIdade()));
		lbGenero.setText(GerenciadorPrincipal.getUsuarioInstancia().getGenero());
		lbPais.setText(GerenciadorPrincipal.getUsuarioInstancia().getPais());
		lbPontuacao.setText(String.valueOf(GerenciadorPrincipal.getUsuarioInstancia().getPontuacao()));
		lbEmail.setText(GerenciadorPrincipal.getUsuarioInstancia().getEmail());
		
	}
	
	

}
