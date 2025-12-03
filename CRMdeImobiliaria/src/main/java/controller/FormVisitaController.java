package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import modelo.Cliente;
import modelo.Funcionario;
import modelo.Visita;
import service.ClienteService;
import service.FuncionarioService;
import javafx.scene.control.ListView;

public class FormVisitaController {
    @FXML private TextField campoCliente;
    @FXML private ListView<Cliente> listaSugestoesCliente;
    @FXML private TextField campoFuncionario;
    @FXML private ListView<Funcionario> listaSugestoesFunc;

    private Visita visitaAtual;
    private ObservableList<Visita> visitasObservable;

    private ObservableList<Cliente> listaClientes;
    private ObservableList<Funcionario> listaFuncionarios;


    public void setVisitasObservable(ObservableList<Visita> visitasObservable) {
        this.visitasObservable = visitasObservable;
    }

    public void setVisita(Visita visita) {

    }


    public void initialize() {
        FuncionarioService funcService = new FuncionarioService();
        configurarAutocomplete(funcService);

        ClienteService clienteService = new ClienteService();
        configurarAutocomplete(clienteService);

    }


    private void configurarAutocomplete(FuncionarioService funcService) {

        listaFuncionarios = FXCollections.observableArrayList(funcService.buscarTodos());

        listaSugestoesFunc.setItems(listaFuncionarios);

        // Quando digitar → filtra
        campoFuncionario.textProperty().addListener((obs, old, novo) -> {
            if (novo.isBlank()) {
                listaSugestoesFunc.setVisible(false);
                listaSugestoesFunc.setManaged(false);
                return;
            }

            listaSugestoesFunc.setItems(
                    listaFuncionarios.filtered(f ->
                            f.getNome().toLowerCase().contains(novo.toLowerCase())
                    )
            );

            listaSugestoesFunc.setVisible(true);
            listaSugestoesFunc.setManaged(true);
        });
    }

    private void configurarAutocomplete(ClienteService clienteService) {

        listaClientes = FXCollections.observableArrayList(clienteService.buscarTodos());

        listaSugestoesCliente.setItems(listaClientes);

        // Quando digitar → filtra
        campoCliente.textProperty().addListener((obs, old, novo) -> {
            if (novo.isBlank()) {
                listaSugestoesCliente.setVisible(false);
                listaSugestoesCliente.setManaged(false);
                return;
            }

            listaSugestoesCliente.setItems(
                    listaClientes.filtered(f ->
                            f.getNome().toLowerCase().contains(novo.toLowerCase())
                    )
            );

            listaSugestoesCliente.setVisible(true);
            listaSugestoesCliente.setManaged(true);
        });
    }
}