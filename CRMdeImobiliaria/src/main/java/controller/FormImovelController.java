package controller;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import modelo.*;
import org.json.JSONObject;
import service.FuncionarioService;
import service.ImovelService;
import view.MainApp;

import java.io.File;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicBoolean;

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

    @FXML private Spinner<Integer> spinnerQuarto;
    @FXML private Spinner<Integer> spinnerSala;
    @FXML private Spinner<Integer> spinnerBanheiro;
    @FXML private Spinner<Integer> spinnerCozinha;
    @FXML private Spinner<Integer> spinnerLavanderia;
    @FXML private Spinner<Integer> spinnerGaragem;

    @FXML private TextField precoField;
    @FXML private ChoiceBox<String> statusBox;
    @FXML private TextField campoFuncionario;
    @FXML private ListView<Funcionario> listaSugestoes;
    private ObservableList<Funcionario> listaFuncionarios;
    @FXML private DatePicker data;



    private Imovel imovelAtual;
    private ObservableList<Imovel> imovelsObservable;

    public void initialize(){

        precoField.textProperty().addListener((obs, oldValue, newValue) -> {
            if (!newValue.matches("\\d*(\\.\\d*)?")) {
                precoField.setText(oldValue);
            }
        });

        //Fazer mascara se der tempo

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

        FuncionarioService funcService = new FuncionarioService();
        listaFuncionarios = FXCollections.observableArrayList(funcService.buscarTodos());

        configurarAutocomplete(funcService);

       data.setValue(LocalDate.now());
       data.setEditable(false);
       data.setDisable(true);
    }
    private void configurarAutocomplete(FuncionarioService funcService) {

        listaFuncionarios = FXCollections.observableArrayList(funcService.buscarTodos());

        listaSugestoes.setItems(listaFuncionarios);

        // Quando digitar → filtra
        campoFuncionario.textProperty().addListener((obs, old, novo) -> {
            if (novo.isBlank()) {
                listaSugestoes.setVisible(false);
                listaSugestoes.setManaged(false);
                return;
            }

            listaSugestoes.setItems(
                    listaFuncionarios.filtered(f ->
                            f.getNome().toLowerCase().contains(novo.toLowerCase())
                    )
            );

            listaSugestoes.setVisible(true);
            listaSugestoes.setManaged(true);
        });

        // Quando clicar numa sugestão → coloca no campo
        listaSugestoes.setOnMouseClicked(ev -> {
            Funcionario f = listaSugestoes.getSelectionModel().getSelectedItem();
            if (f != null) {
                campoFuncionario.setText(f.getNome());
                listaSugestoes.setVisible(false);
                listaSugestoes.setManaged(false);
            }
        });
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

        precoField.setText(imovel.getPreco().toString());
    }

    @FXML
    private void buscaCep(){
        JSONObject obj = Endereco.buscaViaCep(cepField.getText());
        nomeCidadeField.setText(obj.getString("localidade"));
    }


    @FXML
    private void trocarTela(ActionEvent e) {

        String tela = ((Button)e.getSource()).getUserData().toString();

        switch (tela){
            case "endereco":
                mostrarTela(telaEndereco);
                break;
            case "comodos":
                mostrarTela(telaComodos);
                break;
            case "info":
                mostrarTela(telaInformacoes);
                break;
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
    nomeCidadeField;
    cepField;
    logradouroField;
    ufField;
    numeroField;
    complementoField;

    spinnerQuarto;
    spinnerSala;
    spinnerBanheiro;
    spinnerCozinha;
    spinnerLavanderia;
    spinnerGaragem;
    */


    @FXML
    private void salvar() {
        try {
            if (nomeCidadeField.getText().isEmpty() || cepField.getText().isEmpty() ||logradouroField.getText().isEmpty()
            ||ufField.getText().isEmpty() || numeroField.getText().isEmpty() || precoField.getText().isEmpty()) {
                MainApp.mostrarAlerta("Erro", "Compos da area Endereço são obrigatórios!");
                return;
            }
            if(!nomeCidadeField.getText().isEmpty() && !cepField.getText().isEmpty()) {


                if (imovelAtual == null) {
                    imovelAtual = new Imovel();
                    imovelAtual.setEndereco(new Endereco());
                    imovelAtual.setComodos(new Comodos());
                }

                //Muita coisa
                imovelAtual.getEndereco().setCidade(nomeCidadeField.getText());
                imovelAtual.getEndereco().setCep(cepField.getText());
                imovelAtual.getEndereco().setLogradoro(logradouroField.getText());
                imovelAtual.getEndereco().setUf(ufField.getText());
                imovelAtual.getEndereco().setComplemento(complementoField.getText());

                imovelAtual.getComodos().setQuarto(spinnerQuarto.getValue());
                imovelAtual.getComodos().setSala(spinnerSala.getValue());
                imovelAtual.getComodos().setBanheiro(spinnerBanheiro.getValue());
                imovelAtual.getComodos().setCozinha(spinnerCozinha.getValue());
                imovelAtual.getComodos().setLavanderia(spinnerLavanderia.getValue());
                imovelAtual.getComodos().setGaragem(spinnerGaragem.getValue());

                FuncionarioService funcService = new FuncionarioService();
                imovelAtual.setFuncionario(funcService.buscarPorNome(campoFuncionario.getText()));

                imovelAtual.setPreco(Double.parseDouble(precoField.getText()));

                switch (statusBox.getValue()){
                    case "DISPONIVEL":
                        imovelAtual.setStatus(StatusImovel.DISPONIVEL);
                        break;
                    case "NEGOCIAÇÃO":
                        imovelAtual.setStatus(StatusImovel.NEGOCIAÇÃO);
                        break;
                    case "VENDIDO":
                        imovelAtual.setStatus(StatusImovel.VENDIDO);
                        break;
                    case "ALUGADO":
                        imovelAtual.setStatus(StatusImovel.ALUGADO);
                        break;
                }

                service.alter(imovelAtual);
                Stage stage = (Stage) nomeCidadeField.getScene().getWindow();
                if (imovelsObservable != null) {
                    imovelsObservable.add(imovelAtual);
                }
                stage.close();
            }
        }catch(Exception e){
            e.printStackTrace();
            MainApp.mostrarAlerta("Erro grave", "Não foi possível salvar o imóvel.");
        }
    }
    @FXML
    private void cancelar(){
        Stage stage = (Stage) nomeCidadeField.getScene().getWindow();
        stage.close();
    }
}
