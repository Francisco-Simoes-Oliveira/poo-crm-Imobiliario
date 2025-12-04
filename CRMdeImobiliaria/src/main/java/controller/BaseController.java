package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;
import java.io.IOException;
import java.net.URL;

public abstract class BaseController {

    protected StackPane conteudo;

    public void setConteudo(StackPane conteudo) {
        this.conteudo = conteudo;
    }

    protected void trocarTela(String caminhoFXML) {
        try {
            URL fxmlUrl = getClass().getResource(caminhoFXML);
            if (fxmlUrl == null) {
                throw new IllegalArgumentException("Arquivo FXML não encontrado: " + caminhoFXML);
            }

            FXMLLoader loader = new FXMLLoader(fxmlUrl);
            Node novaTela = loader.load();

            Object controller = loader.getController();
            if (controller instanceof BaseController baseCtrl) {
                baseCtrl.setConteudo(conteudo);
            }

            conteudo.getChildren().setAll(novaTela);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void abrirConfiguracao() {
        trocarTela("/sceneBuilder/ConfiguracaoView.fxml");
    }

    @FXML
    private void abrirImoveis() {
        trocarTela("/sceneBuilder/ImovelView.fxml");
    }
}
