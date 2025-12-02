package controller;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import modelo.Imovel;
import modelo.Endereco;
import org.json.JSONObject;
import service.ImovelService;

import java.io.File;

public class FormImovelController {
    private ImovelService service = new ImovelService();

    @FXML private VBox telaEndereco;
    @FXML private VBox telaComodos;
    @FXML private VBox telaInformacoes;

    @FXML private TextField nomeCidadeField;
    @FXML private TextField cepField;
    @FXML private TextField logradouroField;
    @FXML private TextField ufField;
    @FXML private TextField numeroField;
    @FXML private TextField complementoField;

    @FXML private Spinner spinnerQuarto;
    @FXML private Spinner spinnerSala;
    @FXML private Spinner spinnerBanheiro;
    @FXML private Spinner spinnerCozinha;
    @FXML private Spinner spinnerLavanderia;
    @FXML private Spinner spinnerGaragem;

    private Imovel imovelAtual;
    private ObservableList<Imovel> imovelsObservable;

    public void initialize(){
        mostrarTela(telaEndereco);

        spinnerQuarto.setValueFactory(
                new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 20, 1)
        );
        spinnerSala.setValueFactory(
                new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 20, 1)
        );
        spinnerBanheiro.setValueFactory(
                new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 20, 1)
        );
        spinnerCozinha.setValueFactory(
                new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 20, 1)
        );
        spinnerLavanderia.setValueFactory(
                new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 20, 1)
        );
        spinnerGaragem.setValueFactory(
                new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 20, 0)
        );

    }


    public void setImovelsObservable(ObservableList<Imovel> imovelsObservable) {
        this.imovelsObservable = imovelsObservable;
    }
    public void setImovel(Imovel imovel) {
        this.imovelAtual = imovel;
        // Preenche os campos de texto normalmente
        nomeCidadeField.setText(imovel.getEndereco().getCidade());
        cepField.setText(imovel.getEndereco().getCep());
        logradouroField.setText(imovel.getEndereco().getLogradoro());
        ufField.setText(imovel.getEndereco().getUf());
        numeroField.setText(imovel.getEndereco().getNumero());
        complementoField.setText(imovel.getEndereco().getComplemento());
    }

    @FXML
    private void buscaCep(){
        JSONObject obj = Endereco.buscaViaCep(cepField.getText());
        nomeCidadeField.setText(obj.getString("localidade"));
    }


    @FXML
    private void trocarTela(ActionEvent e) {

        String tela = ((Button)e.getSource()).getUserData().toString();

        mostrarTela(telaEndereco);
        mostrarTela(telaComodos);
        mostrarTela(telaInformacoes);

        switch (tela){
            case "endereco": mostrarTela(telaEndereco); break;
            case "comodos": mostrarTela(telaComodos); break;
            case "info":    mostrarTela(telaInformacoes); break;
        }
    }

    private void mostrarTela(Node tela) {
        telaEndereco.setVisible(false);
        telaEndereco.setManaged(false);
        telaEndereco.setMouseTransparent(true);

        telaComodos.setVisible(false);
        telaComodos.setManaged(false);
        telaComodos.setMouseTransparent(true);

        telaInformacoes.setVisible(false);
        telaInformacoes.setManaged(false);
        telaInformacoes.setMouseTransparent(true);

        tela.setVisible(true);
        tela.setManaged(true);
        tela.setMouseTransparent(false);
    }

    @FXML
    private ImageView imagePreview;

    private File imagemSelecionada;

    @FXML
    private void selecionarImagem(ActionEvent event) {

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Selecionar Imagem do Imóvel");

        // filtros de arquivos
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Imagens", "*.png", "*.jpg", "*.jpeg")
        );

        // abre a janela
        File file = fileChooser.showOpenDialog(null);

        if (file != null) {
            imagemSelecionada = file;

            // mostra no preview
            imagePreview.setImage(new Image(file.toURI().toString()));
        }
    }



/*
    @FXML
    private void salvar() {

        if (nomeCidadeField.getText().isEmpty() || cepField.getText().isEmpty()) {
            MainApp.mostrarAlerta("Erro", "O campo nome e CPF são obrigatórios!");
            return;
        }
        if(!nomeCidadeField.getText().isEmpty() && !cepField.getText().isEmpty()) {
            if (Imovel.validarCpf(cepField.getText())) {

                if (imovelAtual == null) imovelAtual = new Imovel();
                imovelAtual.setNome(nomeCidadeField.getText());
                imovelAtual.setCpf(cepField.getText());
                imovelAtual.setEmail(emailField.getText());
                imovelAtual.setTelefone(telefoneField.getText());

                if (status.isSelected()){
                    imovelAtual.setStatus(StatusPessoa.ATIVO);
                }else {
                    imovelAtual.setStatus(StatusPessoa.DESATIVADO);
                }
                service.alter(imovelAtual);
                Stage stage = (Stage) nomeCidadeField.getScene().getWindow();
                if (imovelsObservable != null) {
                    imovelsObservable.add(imovelAtual);
                }
                stage.close();
            }else MainApp.mostrarAlerta("Erro", "CPF inválido!");;
        }
    }
    @FXML
    private void cancelar(){
        Stage stage = (Stage) nomeCidadeField.getScene().getWindow();
        stage.close();
    }*/
}
