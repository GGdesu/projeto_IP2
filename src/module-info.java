module ticTacThink {
	requires com.google.gson;
	requires org.jsoup;
	requires javafx.controls;
	requires javafx.graphics;
	requires javafx.fxml;

	opens ticTacThink.aplicacao.beans;
	opens ticTacThink.gui to javafx.fxml;
	exports ticTacThink.gui;
	exports ticTacThink;
	exports ticTacThink.aplicacao.beans;
	
}