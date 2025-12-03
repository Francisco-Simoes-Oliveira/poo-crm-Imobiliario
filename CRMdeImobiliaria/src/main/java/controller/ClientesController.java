package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import modelo.Cliente;
import service.ClienteService;

import java.io.IOException;


public class ClientesController extends BaseController {

    private ClienteService service = new ClienteService();


    @FXML
    private void abrirImoveis() {
        trocarTela("/view/ImoveisView.fxml");

    }

    @FXML private Button NovoCliente;
    @FXML private ComboBox<String> comboFiltro;
    @FXML private TextField campoPesquisa;


    @FXML
    private void abrirNovoClienteModal() {
        try {
            // Carrega o FXML do modal
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/sceneBuilder/FormCliente.fxml"));
            Parent root = loader.load();

            FormClienteController controller = loader.getController();
            controller.setClientesObservable(clientesObservable);

            // Cria um novo stage
            Stage modalStage = new Stage();
            modalStage.setTitle("Novo Cliente");

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

    public void pesquisar(){
        String filtro = comboFiltro.getValue();
        String termo = campoPesquisa.getText().toLowerCase();

        ObservableList<Cliente> filtrados = clientesObservable.filtered(cliente -> {
            if (termo.isEmpty()) return true;

            switch (filtro) {
                case "Nome":
                    return cliente.getNome().toLowerCase().contains(termo);
                case "CPF":
                    return cliente.getCpf().contains(termo);
                case "Email":
                    return cliente.getEmail() != null && cliente.getEmail().toLowerCase().contains(termo);
                case "Telefone":
                    return cliente.getTelefone().contains(termo);
                case "Status":
                    return cliente.getStatus().toString().toLowerCase().contains(termo);
                default:
                    return false;
            }
        });

        tabelaClientes.setItems(filtrados);
    }

    @FXML
    private TableView<Cliente> tabelaClientes;
    private ObservableList<Cliente> clientesObservable;

    @FXML
    private TableColumn<Cliente, String> colunaId;
    @FXML
    private TableColumn<Cliente, String> colunaNome;
    @FXML
    private TableColumn<Cliente, String> colunaEmail;
    @FXML
    private TableColumn<Cliente, String> colunaTelefone;
    @FXML
    private TableColumn<Cliente, String> colunaStatus;

    @FXML
    private TableColumn<Cliente, Void> colunaAcoes;

    @FXML
    public void initialize() {
        comboFiltro.getSelectionModel().selectFirst(); // Seleciona "Nome" por padrão


        // 1️⃣ Vincula cada coluna à propriedade correspondente da classe Cliente
        colunaId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colunaNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colunaEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colunaTelefone.setCellValueFactory(new PropertyValueFactory<>("telefone"));
        colunaStatus.setCellValueFactory(new PropertyValueFactory<>("status"));


        // 2️⃣ Define tamanhos preferenciais (servem como "proporções")
        colunaId.setPrefWidth(60);
        colunaNome.setPrefWidth(200);
        colunaEmail.setPrefWidth(240);
        colunaTelefone.setPrefWidth(120);
        colunaStatus.setPrefWidth(120);

        // (Opcional) Define tamanhos mínimos para não espremer demais
        colunaNome.setMinWidth(150);
        colunaEmail.setMinWidth(180);

        // 3️⃣ Ativa o redimensionamento automático das colunas
        tabelaClientes.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);

        // 4️⃣ Carrega os
        carregarClientes();

        campoPesquisa.textProperty().addListener((obs, oldVal, newVal) -> pesquisar());

        tabelaClientes.setRowFactory(tv -> {
            TableRow<Cliente> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && !row.isEmpty()) {
                    Cliente clienteSelecionado = row.getItem();
                   abrirModalEdicao(clienteSelecionado);
                }
            });
            return row;
        });

        colunaAcoes.setCellFactory(coluna -> new TableCell<>() {
            private final Button btn = new Button("Editar");

            {
                btn.getStyleClass().add("edit-button"); // se quiser estilizar no CSS
                btn.setOnAction(event -> {
                    Cliente cliente = getTableView().getItems().get(getIndex());
                    abrirModalEdicao(cliente);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : btn);
            }
        });

    }

    private void carregarClientes() {
        clientesObservable = FXCollections.observableArrayList(service.buscarTodos());
        tabelaClientes.setItems(clientesObservable);
    }

    private void abrirModalEdicao(Cliente cliente) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/sceneBuilder/FormCliente.fxml"));
            Parent root = loader.load();

            // Passa o cliente selecionado para o controlador do modal
            FormClienteController controller = loader.getController();
            controller.setCliente(cliente); // você cria esse método no FormClienteController

            Stage stage = new Stage();
            stage.setTitle("Editar Cliente");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initOwner(conteudo.getScene().getWindow());

            stage.setScene(new Scene(root));
            stage.showAndWait(); // bloqueia até o modal ser fechado

            // Atualiza a tabela depois que o modal fecha
            tabelaClientes.setItems(FXCollections.observableArrayList(service.buscarTodos()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
