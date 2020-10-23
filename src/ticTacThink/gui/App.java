package ticTacThink.gui;

import java.io.IOException;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;

public class App extends Application {

    private static Scene scene;
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Tic Tac Think!");

        scene = new Scene(carregarTela("Login"));
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void mudarTela(String fxml) throws IOException {
        scene.setRoot(carregarTela(fxml));
    }
    
    // Telas: Login , Cadastro , Perfil , Editar Perfil , Menu , Partida
    public static Parent carregarTela(String nome) throws IOException {
        return (new FXMLLoader(App.class.getResource(nome + ".fxml"))).load();
    }

    public static void main(String[] args) {
        launch();
    }
}
