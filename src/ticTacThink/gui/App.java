package ticTacThink.gui;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;

public class App extends Application {

    private static Stage stage;
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        stage = primaryStage;
        stage.setTitle("Tic Tac Think!");
        mudarTela("Login");
        stage.show();
    }

    // Telas: Login , Cadastro , Perfil , Editar Perfil , Menu , Partida
    private static Parent carregarFXML(String nome) {
        try {
            return FXMLLoader.load(App.class.getResource(nome + ".fxml"));
        } catch (Exception e) {
            return new AnchorPane();
        }
    }

    public static void mudarTela(String fxml) {
        stage.setScene(new Scene(carregarFXML(fxml)));
        stage.setMinWidth(stage.getWidth());
        stage.setMinHeight(stage.getHeight());
    }
    
    public static Scene getScene() {
        return stage.getScene();
    }

    public static void main(String[] args) {
        launch();
    }
}
