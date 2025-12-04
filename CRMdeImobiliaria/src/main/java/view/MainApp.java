package view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class MainApp extends Application {

    @Override
    public void start(Stage stage) {

        try {
            URL fxmlUrl = getClass().getResource("/sceneBuilder/MainApp.fxml");
            System.out.println("FXML URL -> " + fxmlUrl);

            if (fxmlUrl == null) {
                System.err.println("ERRO: MainApp.fxml NÃO encontrado no classpath. Verifique o caminho e resources.");
            }

            FXMLLoader loader = (fxmlUrl != null) ? new FXMLLoader(fxmlUrl) : new FXMLLoader();
            Parent root = loader.load();

            Scene scene = new Scene(root);
            URL css = getClass().getResource("/css/style.css");
            if (css != null) scene.getStylesheets().add(css.toExternalForm());
            else System.out.println("Aviso: style.css não encontrado.");

            stage.setTitle("Programa");
            stage.setScene(scene);
            stage.setFullScreen(true);
            stage.show();

        } catch (IOException ex) {
            System.err.println("IOException ao carregar FXML:");
            ex.printStackTrace();
        } catch (RuntimeException ex) {
            System.err.println("RuntimeException ao carregar FXML (verificar fx:controller, campos @FXML, namespaces):");
            ex.printStackTrace();
        }
    }

    public static void mostrarAlerta(String titulo, String mensagem) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.showAndWait();
    }



    public static void main(String[] args) {
        launch(args);
    }
}
