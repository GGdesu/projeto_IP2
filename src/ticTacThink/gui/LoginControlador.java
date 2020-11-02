package ticTacThink.gui;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import ticTacThink.GerenciadorPrincipal;

public class LoginControlador {
	@FXML
	TextField email;
	@FXML
	TextField senha;

	@FXML
	void entrar() {
		if (GerenciadorPrincipal.getInstance().existeUsuario(email.getText())) {
			GerenciadorPrincipal.getInstance().login(email.getText(), senha.getText());
			if (GerenciadorPrincipal.getUsuarioInstancia() != null) {
				App.mudarTela("Menu");
				System.out.println("Usuario: " + GerenciadorPrincipal.getUsuarioInstancia().getNome() + " Logado com sucesso!");
			} else {
				System.out.println("Senha incorreta!");
				Alert alerta = new Alert(AlertType.ERROR);
				alerta.setHeaderText("Erro");
				alerta.setTitle("Senha incorreta");
				alerta.setContentText("Senha incorreta, digite novamente");
				alerta.show();
			}
			
		}else {
			System.out.println("Email incorreto!");
			Alert alerta = new Alert(AlertType.ERROR);
			alerta.setHeaderText("Email incorreto");
			alerta.setTitle("Erro");
			alerta.setContentText("Email incorreto, digite novamente");
			alerta.show();
			
		}

	}

	@FXML
	void cadastrar() {
		App.abrirPopup("Cadastro");
	}

}