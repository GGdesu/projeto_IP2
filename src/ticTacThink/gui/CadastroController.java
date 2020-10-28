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
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;

public class CadastroController implements Initializable {

	@FXML
	private TextField txtfieldNome;

	@FXML
	private TextField txtfieldEmail;

	@FXML
	private TextField txtfieldConfSenha;

	@FXML
	private TextField txtfieldSenha;

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
	void cadastrar() {
		System.out.println("Tentando Cadastrar");

	}

	@FXML
	void dp() {
		System.out.println(datePicker.getValue());
		converterDatePicker();
		System.out.println(cbPais.getValue());
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		carregarPaises();

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

			Locale obj = new Locale("en", countryCode);

			paises.add(obj.getDisplayCountry());

		}
		
		observablePais = FXCollections.observableArrayList(paises);
		cbPais.setItems(observablePais);
	}

}
