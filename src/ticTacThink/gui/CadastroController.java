package ticTacThink.gui;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import ticTacThink.GerenciadorPrincipal;
import ticTacThink.aplicacao.beans.Usuario;
import ticTacThink.exceptions.UsuarioJaExiste;

public class CadastroController implements Initializable {

	@FXML
	private TextField txtfieldNome;

	@FXML
	private TextField txtfieldEmail;

	@FXML
	private PasswordField pfSenha;

	@FXML
	private PasswordField pfConfSenha;

	@FXML
	private ToggleGroup generoGrupo;

	@FXML
	private DatePicker datePicker;

	@FXML
	private ComboBox<String> cbPais;

	private ObservableList<String> observablePais;

	@FXML
	void voltar() {
		App.mudarTela("Login");
	}

	@FXML
	void cadastrar() throws UsuarioJaExiste {
		if(verificarCampo("email")) {
			if(verificarCampo("nome")) {
				if(verificarCampo("senha")) {
					if(verificarCampo("confSenha")) {
						
						//Pegando o valor do togglegrup
						RadioButton btnSelecionado = (RadioButton) generoGrupo.getSelectedToggle();
						
						
						Usuario usuario = new Usuario(txtfieldNome.getText(), pfSenha.getText(), txtfieldEmail.getText(), 
								cbPais.getValue(), btnSelecionado.getText(), converterDatePicker());
						
						GerenciadorPrincipal.getInstance().cadastrarUsuario(usuario);
						System.out.println("Usuario cadastrado com sucesso!");
						
					}else {
						
						Alert alerta = new Alert(AlertType.ERROR);
						alerta.setHeaderText("Senha Diferente");
						alerta.setTitle("Erro");
						alerta.setContentText("Senha Diferente da digitada anteriormente, digite a nova senha novamente");
						alerta.show();
						
					}
					
				}else {
					
					Alert alerta = new Alert(AlertType.ERROR);
					alerta.setHeaderText("Senha nula");
					alerta.setTitle("Erro");
					alerta.setContentText("Senha nula, digite a nova senha novamente");
					alerta.show();
				}
				
			}else {
				
				if(txtfieldNome.getText().equals("")) {
					Alert alerta = new Alert(AlertType.ERROR);
					alerta.setHeaderText("Nome nulo");
					alerta.setTitle("Erro");
					alerta.setContentText("Nome nulo, digite o nome novamente");
					alerta.show();
				}else {
					Alert alerta = new Alert(AlertType.ERROR);
					alerta.setHeaderText("Nome inválido");
					alerta.setTitle("Erro");
					alerta.setContentText("Nome inválido, digite somente letras");
					alerta.show();
				}
			}
			
		}else {
			
			if(GerenciadorPrincipal.getInstance().existeUsuario(txtfieldEmail.getText()) == true){
				Alert alerta = new Alert(AlertType.ERROR);
				alerta.setHeaderText("Email já existe");
				alerta.setTitle("Erro");
				alerta.setContentText("Email indisponível, tente outro email");
				alerta.show();
			}else {
				Alert alerta = new Alert(AlertType.ERROR);
				alerta.setHeaderText("Email nulo ou inválido");
				alerta.setTitle("Erro");
				alerta.setContentText("Email nulo ou inválido, digite um email com @, '.com' e sem deixar espaços");
				alerta.show();
			}
			
		}

	}


	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		carregarPaises();
		datePicker.setValue(LocalDate.now());
		cbPais.setValue("Desconhecido");

	}

	// o usuario agora irá ter uma data de nascimento ao inves de idade
	private String converterDatePicker() {

		String pattern = "dd/MM/yyyy";

		LocalDate ld = this.datePicker.getValue();

		String dataFormatada = ld.format(DateTimeFormatter.ofPattern(pattern));

		System.out.println("data formatada: " + dataFormatada);

		return dataFormatada;

	}

	private void carregarPaises() {

		String[] countryCodes = Locale.getISOCountries();
		ArrayList<String> paises = new ArrayList<>();

		// para pegar os paises
		for (String countryCode : countryCodes) {

			Locale obj = new Locale("EN", countryCode);

			paises.add(obj.getDisplayCountry());

		}

		observablePais = FXCollections.observableArrayList(paises);
		cbPais.setItems(observablePais);
	}

	private boolean verificarCampo(String campo) {

		switch (campo) {
		case "nome":
			if (!txtfieldNome.getText().equals("")
					&& txtfieldNome.getText().matches("^[A-Za-záàâãéèêíïóôõöûúçñÁÀÂÃÉÊÈÍÏÓÔÕÖÚÛÇÑ ]+$")) {
				return true;

			} else {
				return false;

			}
		case "email":

			int countP = 0, countA = 0;
			int posP = txtfieldEmail.getText().indexOf(".");
			int posA = txtfieldEmail.getText().indexOf("@");

			while (posA != -1) {
				countA++;
				posA = txtfieldEmail.getText().indexOf("@", posA + 1);
			}

			while (posP != -1) {
				countP++;
				posP = txtfieldEmail.getText().indexOf(".", posP + 1);
			}

			if (GerenciadorPrincipal.getInstance().existeUsuario(txtfieldEmail.getText()) == false
					&& !txtfieldEmail.getText().equals("") && txtfieldEmail.getText().endsWith(".com")
					&& txtfieldEmail.getText().indexOf(" ") == -1 && countP == 1 && countA == 1) {
				return true;

			} else {
				return false;

			}
		case "senha":
			if (!pfSenha.getText().equals("") && pfSenha.getText().length() >= 8) {
				return true;

			} else {
				return false;

			}
		case "confSenha":
			if (pfConfSenha.getText().equals(pfSenha.getText())) {
				return true;

			} else {
				return false;

			}
			
		}
		System.out.println("Nenhuma das alternativas foi escolhida!");
		return false;
	}

}
