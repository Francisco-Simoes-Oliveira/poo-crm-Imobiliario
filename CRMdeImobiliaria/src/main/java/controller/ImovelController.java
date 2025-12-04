package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import modelo.Imovel;
import service.ImovelService;

import java.io.IOException;

public class ImovelController extends BaseController {

    private ImovelService service = new ImovelService();

    @FXML private ComboBox<String> comboFiltro;
    @FXML private TextField campoPesquisa;

    @FXML
    private TableView<Imovel> tabelaImovels;
    private ObservableList<Imovel> imovelsObservable;

    @FXML
    private TableColumn<Imovel, String> colunaId;
    @FXML
    private TableColumn<Imovel, String> colunaLogradoro;
    @FXML
    private TableColumn<Imovel, String> colunaStatus;
    @FXML
    private TableColumn<Imovel, Void> colunaImg;
    @FXML
    private TableColumn<Imovel, Void> colunaInfo;
    @FXML
    private TableColumn<Imovel, Void> colunaAcoes;


    @FXML
    public void initialize() {
        comboFiltro.getSelectionModel().selectFirst();

        colunaId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colunaLogradoro.setCellValueFactory(new PropertyValueFactory<>("logradoro"));
        colunaStatus.setCellValueFactory(new PropertyValueFactory<>("status"));

        colunaId.setPrefWidth(60);
        colunaLogradoro.setPrefWidth(200);
        colunaStatus.setPrefWidth(150);
       

        colunaLogradoro.setMinWidth(150);

        tabelaImovels.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);

        carregarImovels();

        campoPesquisa.textProperty().addListener((obs, oldVal, newVal) -> pesquisar());

        tabelaImovels.setRowFactory(tv -> {
            TableRow<Imovel> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && !row.isEmpty()) {
                    Imovel imovelSelecionado = row.getItem();
                    abrirModalEdicao(imovelSelecionado);
                }
            });
            return row;
        });

        colunaAcoes.setCellFactory(coluna -> new TableCell<>() {
            private final Button btn = new Button("Editar");

            {
                btn.getStyleClass().add("edit-button");
                btn.setOnAction(event -> {
                    Imovel imovel = getTableView().getItems().get(getIndex());
                    abrirModalEdicao(imovel);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : btn);
            }
        });

        colunaImg.setCellFactory(coluna -> new TableCell<>() {
            private final Button btn = new Button("Img");
            
            {
                btn.getStyleClass().add("edit-button");
                btn.setOnAction(event -> {
                    Imovel imovel = getTableView().getItems().get(getIndex());
                    abrirModalEdicao(imovel);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : btn);
            }
        });

        colunaInfo.setCellFactory(coluna -> new TableCell<>() {
            private final Button btn = new Button("Info");

            {
                btn.getStyleClass().add("edit-button");
                btn.setOnAction(event -> {
                    Imovel imovel = getTableView().getItems().get(getIndex());
                    abrirModalEdicao(imovel);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : btn);
            }
        });

    }

    @FXML
    private void abrirNovoImovelModal(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/sceneBuilder/FormImovel.fxml"));
            Parent root = loader.load();

            FormImovelController controller = loader.getController();
            controller.setImovelsObservable(imovelsObservable);


            Stage modalStage = new Stage();
            modalStage.setTitle("Novo Imovel");

            modalStage.initModality(Modality.APPLICATION_MODAL);
            modalStage.initOwner(conteudo.getScene().getWindow());

            Scene scene = new Scene(root);
            modalStage.setScene(scene);
            modalStage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void carregarImovels() {
        imovelsObservable = FXCollections.observableArrayList(service.buscarTodos());
        tabelaImovels.setItems(imovelsObservable);
    }

    private void abrirModalEdicao(Imovel imovel) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/sceneBuilder/FormImovel.fxml"));
            Parent root = loader.load();

            FormImovelController controller = loader.getController();
            controller.setImovel(imovel); // você cria esse método no FormImovelController

            Stage stage = new Stage();
            stage.setTitle("Editar Imovel");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initOwner(conteudo.getScene().getWindow());

            stage.setScene(new Scene(root));
            stage.showAndWait();

            tabelaImovels.setItems(FXCollections.observableArrayList(service.buscarTodos()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void pesquisar(){
        String filtro = comboFiltro.getValue();
        String termo = campoPesquisa.getText().toLowerCase();

        ObservableList<Imovel> filtrados = imovelsObservable.filtered(imovel -> {
            if (termo.isEmpty()) return true;

            switch (filtro) {
                case "Logradoro":
                    return imovel.getEndereco().getLogradouro().toLowerCase().contains(termo);
                case "CEP":
                    return imovel.getEndereco().getCep().contains(termo);
                case "Status":
                    return imovel.getStatus().toString().toLowerCase().contains(termo);
                default:
                    return false;
            }
        });

        tabelaImovels.setItems(filtrados);
    }

}


/*
private void formAddImovel(Stage principalStage){
        Stage formImovel = new Stage();
        GridPane gridForm = new GridPane();
        Scene scene = new Scene(gridForm,450,300);

        Label cidadeLabel = new Label("Cidade*   ");
        TextField cidadeField  = new TextField();

        Label cepLabel = new Label("CEP(Apenas numeros)*   ");
        TextField cepField  = new TextField();
        Button btnCep = new Button();

        Label ufLabel = new Label("UF   ");
        TextField ufField  = new TextField();

        Label precoLabel = new Label("Preço R$  ");
        TextField precoField  = new TextField();

        Label esp = new Label("      ");
        gridForm.setAlignment(Pos.TOP_CENTER);
        gridForm.add(esp,1,0);
        gridForm.add(cidadeLabel,0,0);
        gridForm.add(cidadeField,0,1);
        gridForm.add(cepLabel,2,0);
        gridForm.add(cepField,2,1);
        gridForm.add(btnCep,3,1);
        gridForm.add(ufLabel,0,2);
        gridForm.add(ufField,0,3);
        gridForm.add(precoLabel,2,2);
        gridForm.add(precoField,2,3);

        btnCep.setOnAction(e->{
            JSONObject ojt = Endereco.buscaViaCep(cepField.getText());
            cidadeField.setText(ojt.getString("localidade"));
            ufField.setText(ojt.getString("uf"));
        });

        Button salvar = new Button("SALVAR");
        salvar.setOnAction(e->{
            if(!cidadeLabel.getText().isEmpty() && !cepLabel.getText().isEmpty()) {
                if (Imovel.validarCpf(cepLabel.getText())) {
                    ImovelService service = new ImovelService();
                    service.add(cidadeField.getText(), cepField.getText(),
                            ufField.getText(), precoField.getText());
                    formImovel.close();
                }else mostrarAlerta("Erro", "CPF inválido!");;
            }else {
                mostrarAlerta("Erro", "O campo nome e cpf são obrigatórios!");
            }
        });
        Label obs = new Label("Os campos com * são obrigatórios");
        gridForm.add(obs,0,4,1,4);
        gridForm.add(salvar,2, 4);


        formImovel.setScene(scene);

        formImovel.initOwner(principalStage);
        formImovel.initModality(Modality.APPLICATION_MODAL);

        formImovel.showAndWait();
    }

    private GridPane listaImovelPage(Stage stage) {
        ImovelService service = new ImovelService();
        List<Imovel> imoveis = service.buscarTodos();
               List.of(
                new Imovel("Francisco", "11570209928", "francisco@email.com", ""),
                new Imovel("Maria", "", "maria@email.com", ""),
                new Imovel("João", "", "joao@email.com", "")
        );

VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(10));
        vbox.getStyleClass().add("lista-imoveis");

        for (
Imovel imovel : imoveis) {
HBox imovelBox = new HBox(20);
            imovelBox.setPadding(new Insets(5));
        imovelBox.getStyleClass().add("imovel-box");

            Label nomeLabel = new Label(imovel.getNome());
            Label emailLabel = new Label(imovel.getEmail());

            imovelBox.getChildren().addAll(nomeLabel, emailLabel);
            vbox.getChildren().add(imovelBox);
        }

ScrollPane scrollPane = new ScrollPane(vbox);
        scrollPane.setFitToWidth(true);

GridPane grid = new GridPane();
        grid.getStyleClass().add("grid");
        grid.setAlignment(Pos.TOP_CENTER);
        grid.add(scrollPane,1,2);


HBox barraPesquisa = new HBox(200);
        barraPesquisa.setAlignment(Pos.TOP_CENTER);
        barraPesquisa.setPadding(new Insets(5));
        barraPesquisa.getStyleClass().add("barra-pesquisa");
Label tituloLabel = new Label("Imovel");

Button bntAdd = new Button("Adicionar");

        barraPesquisa.getChildren().addAll(tituloLabel,bntAdd);

        grid.add(barraPesquisa,1,1);



        bntAdd.setOnAction(e-> formAddImovel(stage));


        return grid;
    }

*/
