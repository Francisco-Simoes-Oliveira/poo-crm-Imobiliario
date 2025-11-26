package controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.application.Platform;
import java.net.URL;
import java.util.ResourceBundle;

public class MainAppController extends BaseController implements Initializable {

    @FXML
    private StackPane conteudo;


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Platform.runLater(() -> {
            setConteudo(conteudo);
            trocarTela("/sceneBuilder/ClientesView.fxml");
        });
    }

    @FXML
    private void abrirClientes() {
        trocarTela("/sceneBuilder/ClientesView.fxml");
    }

    @FXML
    private void abrirVisitas() {
        trocarTela("/sceneBuilder/VisitasView.fxml");
    }

    @FXML
    private void abrirRelatorios() {
        trocarTela("/sceneBuilder/RelatoriosView.fxml");
    }


    @FXML
    private Button Sair;

    @FXML
    private void onSairClick(ActionEvent event) {
        Platform.exit();
    }
}
