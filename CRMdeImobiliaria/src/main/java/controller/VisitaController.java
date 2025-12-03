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
import modelo.Visita;
import service.VisitaService;


import java.io.IOException;

public class VisitaController extends BaseController {

    private VisitaService service = new VisitaService();

    @FXML private ComboBox<String> comboFiltro;
    @FXML private TextField campoPesquisa;

    @FXML
    private TableView<Visita> tabelaVisitas;
    private ObservableList<Visita> visitasObservable;

    @FXML private TableColumn<Visita, String> colunaId;
    @FXML private TableColumn<Visita, String> colunaLogradoro;
    @FXML private TableColumn<Visita, String> colunaStatus;
    @FXML private TableColumn<Visita, String> colunaCliente;
    @FXML private TableColumn<Visita, String> colunaFunc;
    @FXML private TableColumn<Visita, Void> colunaInfo;
    @FXML private TableColumn<Visita, Void> colunaAcoes;

    @FXML
    public void initialize() {
        comboFiltro.getSelectionModel().selectFirst(); // Seleciona "Nome" por padrão


        // 1️⃣ Vincula cada coluna à propriedade correspondente da classe Visita
        colunaId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colunaLogradoro.setCellValueFactory(new PropertyValueFactory<>("logradoro"));
        colunaStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        colunaCliente.setCellValueFactory(new PropertyValueFactory<>("cliente"));
        colunaFunc.setCellValueFactory(new PropertyValueFactory<>("funcionario"));

        // 2️⃣ Define tamanhos preferenciais (servem como "proporções")
        colunaId.setPrefWidth(60);
        colunaLogradoro.setPrefWidth(200);
        colunaStatus.setPrefWidth(150);


        // (Opcional) Define tamanhos mínimos para não espremer demais
        colunaLogradoro.setMinWidth(150);

        // 3️⃣ Ativa o redimensionamento automático das colunas
        tabelaVisitas.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);

        // 4️⃣ Carrega os
        carregarVisitas();

        campoPesquisa.textProperty().addListener((obs, oldVal, newVal) -> pesquisar());

        tabelaVisitas.setRowFactory(tv -> {
            TableRow<Visita> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && !row.isEmpty()) {
                    Visita visitaSelecionado = row.getItem();
                    abrirModalEdicao(visitaSelecionado);
                }
            });
            return row;
        });

        colunaAcoes.setCellFactory(coluna -> new TableCell<>() {
            private final Button btn = new Button("Editar");

            {
                btn.getStyleClass().add("edit-button");
                btn.setOnAction(event -> {
                    Visita visita = getTableView().getItems().get(getIndex());
                    abrirModalEdicao(visita);
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
                    Visita visita = getTableView().getItems().get(getIndex());
                    abrirModalEdicao(visita);
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
    private void abrirNovoVisitaModal(){
        try {
            // Carrega o FXML do modal
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/sceneBuilder/FormVisita.fxml"));
            Parent root = loader.load();

            FormVisitaController controller = loader.getController();
            controller.setVisitasObservable(visitasObservable);

            // Cria um novo stage
            Stage modalStage = new Stage();
            modalStage.setTitle("Novo Visita");

            // Define que é modal (bloqueia a janela principal enquanto está aberto)
            modalStage.initModality(Modality.APPLICATION_MODAL);

            // Faz com que fique "sobre" a janela principal
            modalStage.initOwner(conteudo.getScene().getWindow());

            // Cria a cena e mostra
            Scene scene = new Scene(root);
            modalStage.setScene(scene);
            modalStage.showAndWait(); // espera o usuário fechar

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void carregarVisitas() {
        visitasObservable = FXCollections.observableArrayList(service.buscarTodos());
        tabelaVisitas.setItems(visitasObservable);
    }

    private void abrirModalEdicao(Visita visita) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/sceneBuilder/FormVisita.fxml"));
            Parent root = loader.load();

            // Passa o visita selecionado para o controlador do modal
            FormVisitaController controller = loader.getController();
            controller.setVisita(visita); // você cria esse método no FormVisitaController

            Stage stage = new Stage();
            stage.setTitle("Editar Visita");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initOwner(conteudo.getScene().getWindow());

            stage.setScene(new Scene(root));
            stage.showAndWait(); // bloqueia até o modal ser fechado

            // Atualiza a tabela depois que o modal fecha
            tabelaVisitas.setItems(FXCollections.observableArrayList(service.buscarTodos()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void pesquisar(){
        String filtro = comboFiltro.getValue();
        String termo = campoPesquisa.getText().toLowerCase();

        ObservableList<Visita> filtrados = visitasObservable.filtered(visita -> {
            if (termo.isEmpty()) return true;

            switch (filtro) {
                case "Logradoro":
                    return visita.getImovel().getEndereco().getLogradoro().toLowerCase().contains(termo);
                case "CEP":
                    return visita.getImovel().getEndereco().getCep().contains(termo);
                case "Cliente":
                    return visita.getCliente().getNome().toString().toLowerCase().contains(termo);
                case "Status":
                    return visita.getStatosVisita().toString().toLowerCase().contains(termo);
                default:
                    return false;
            }
        });

        tabelaVisitas.setItems(filtrados);
    }

}

