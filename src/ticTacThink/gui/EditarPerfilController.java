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
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Alert.AlertType;
import ticTacThink.GerenciadorPrincipal;

public class EditarPerfilController implements Initializable{

	@FXML
	private TextField tfNome;
	
	@FXML
    private PasswordField pfSenha;

    @FXML
    private PasswordField pfConfSenha;

	@FXML
	private DatePicker datePicker;

	@FXML
	private ComboBox<String> cbPais;
	
	private ObservableList<String> observablePais;

	@FXML
	private ToggleGroup grupo;

	@FXML
	void voltar() {
		App.mudarTela("PerfilUsuario");
	}
	
	@FXML
	void editar() {
		if(verificarCampo("nome")) {
			if(verificarCampo("senha")) {
				if(verificarCampo("confSenha")) {
					
					//Pegar o genero selecionado do toggleGroup
					RadioButton btnSelecionado = (RadioButton) grupo.getSelectedToggle();
					
					GerenciadorPrincipal.getUsuarioInstancia().setNome(tfNome.getText());
					GerenciadorPrincipal.getUsuarioInstancia().setSenha(pfSenha.getText());
					GerenciadorPrincipal.getUsuarioInstancia().setDataNasc(converterDatePicker());
					GerenciadorPrincipal.getUsuarioInstancia().setPais(cbPais.getValue());
					if(btnSelecionado != null) {
						GerenciadorPrincipal.getUsuarioInstancia().setGenero(btnSelecionado.getText());
					}
					
					//Atualizando no arquivo
					GerenciadorPrincipal.getInstance().atualizarUsuario(GerenciadorPrincipal.getUsuarioInstancia());
					
					System.out.println("Informações atualizadas com sucesso!");
					App.mudarTela("PerfilUsuario");
					
				}else {
					Alert alerta = new Alert(AlertType.ERROR);
					alerta.setHeaderText("Senha Diferente");
					alerta.setTitle("Erro");
					alerta.setContentText("As senhas distintas, coloque a nova senha correta!");
					alerta.show();
				}
				
			}else {
				Alert alerta = new Alert(AlertType.ERROR);
				alerta.setHeaderText("Senha Inválida");
				alerta.setTitle("Erro");
				alerta.setContentText("Digite uma senha válida!");
				alerta.show();
			}
			
		}else {
			Alert alerta = new Alert(AlertType.ERROR);
			alerta.setHeaderText("Nome Inválido");
			alerta.setTitle("Erro");
			alerta.setContentText("Digite um nome válido!");
			alerta.show();
		}
		
		
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		carregarPaises();
		carregarValores();
		System.out.println("Informações carregadas com sucesso!");
	}

	private void carregarValores() {
		tfNome.setText(GerenciadorPrincipal.getUsuarioInstancia().getNome());
		pfSenha.setText(GerenciadorPrincipal.getUsuarioInstancia().getSenha());
		pfConfSenha.setText(GerenciadorPrincipal.getUsuarioInstancia().getSenha());
		cbPais.setValue(GerenciadorPrincipal.getUsuarioInstancia().getPais());
		datePicker.setValue(stringParaLocalDate());
		
	}
	
	
	private LocalDate stringParaLocalDate() {
		String pattern = "dd/MM/yyyy";
		
		LocalDate date = LocalDate.parse(GerenciadorPrincipal.getUsuarioInstancia().getDataNasc(), 
				DateTimeFormatter.ofPattern(pattern));
		
		return date;
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
	
	private String converterDatePicker() {

		String pattern = "dd/MM/yyyy";

		LocalDate ld = this.datePicker.getValue();

		String dataFormatada = ld.format(DateTimeFormatter.ofPattern(pattern));

		return dataFormatada;

	}

	private boolean verificarCampo(String campo) {

		switch (campo) {
		case "nome":
			if (!tfNome.getText().equals("")
					&& tfNome.getText().matches("^[A-Za-záàâãéèêíïóôõöûúçñÁÀÂÃÉÊÈÍÏÓÔÕÖÚÛÇÑ ]+$")) {
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
