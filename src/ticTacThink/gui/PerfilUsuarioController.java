package ticTacThink.gui;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import ticTacThink.GerenciadorPrincipal;

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

	@FXML
	void voltar() {
		App.mudarTela("Menu");
	}

	@FXML
	void editarPerfil() {
		App.abrirPopup("EditarPerfil");
	}
	
	//pensando em colocar uma caixa de confirmação
	@FXML
    void excluirConta() {
		GerenciadorPrincipal.getInstance().removerConta(GerenciadorPrincipal.getUsuarioInstancia());
		GerenciadorPrincipal.getInstance().logout();
		System.out.println("Conta excluída com sucesso!");
		App.mudarTela("Login");
    }

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		carregarValores();
		System.out.println("Informações carregadas com sucesso!");
	}

	private void carregarValores() {
		
		labelNome.setText(GerenciadorPrincipal.getUsuarioInstancia().getNome());
		labelNasc.setText(GerenciadorPrincipal.getUsuarioInstancia().getDataNasc());
		labelGen.setText(GerenciadorPrincipal.getUsuarioInstancia().getGenero());
		labelPais.setText(GerenciadorPrincipal.getUsuarioInstancia().getPais());
		labelPon.setText(String.valueOf(GerenciadorPrincipal.getUsuarioInstancia().getPontuacao()));
		labelEmail.setText(GerenciadorPrincipal.getUsuarioInstancia().getEmail());
	}

}
