package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import modelo.Cliente;
import modelo.Funcionario;
import modelo.StatusPessoa;
import modelo.Visita;
import service.ClienteService;
import service.FuncionarioService;
import javafx.scene.control.ListView;
import view.MainApp;

public class FormVisitaController {
    @FXML private TextField campoCliente;
    @FXML private ListView<Cliente> listaSugestoesCliente;
    @FXML private TextField campoFuncionario;
    @FXML private ListView<Funcionario> listaSugestoesFunc;
    @FXML private DatePicker date;
    @FXML private TextField hora;

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
        listaSugestoesFunc.setVisible(false);
        listaSugestoesFunc.setManaged(false);
        listaSugestoesCliente.setVisible(false);
        listaSugestoesCliente.setManaged(false);

        FuncionarioService funcService = new FuncionarioService();
        configurarAutocompletef(funcService);

        ClienteService clienteService = new ClienteService();
        configurarAutocompletec(clienteService);

    }

    private void configurarAutocompletef(FuncionarioService funcService) {

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

        // Quando clicar numa sugestão → coloca no campo
        listaSugestoesFunc.setOnMouseClicked(ev -> {
            Funcionario f = listaSugestoesFunc.getSelectionModel().getSelectedItem();
            if (f != null) {
                campoFuncionario.setText(f.getNome());
                listaSugestoesFunc.setVisible(false);
                listaSugestoesFunc.setManaged(false);
            }
        });
    }


    private void configurarAutocompletec(ClienteService clienteService) {

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
                    listaClientes.filtered(c ->
                            c.getNome().toLowerCase().contains(novo.toLowerCase())
                    )
            );

            listaSugestoesCliente.setVisible(true);
            listaSugestoesCliente.setManaged(true);
        });

        listaSugestoesCliente.setOnMouseClicked(ev -> {
            Cliente c = listaSugestoesCliente.getSelectionModel().getSelectedItem();
            if (c != null) {
                campoCliente.setText(c.getNome());
                listaSugestoesCliente.setVisible(false);
                listaSugestoesCliente.setManaged(false);
            }
        });

    }


    @FXML
    private void salvar() {

        if (campoCliente.getText().isEmpty() || campoFuncionario.getText().isEmpty()) {
            MainApp.mostrarAlerta("Erro", "Todos os campo são obrigatórios!");
            return;
        }/*
        if(!nomeField.getText().isEmpty() && !cpfField.getText().isEmpty()) {
            if (Cliente.validarCpf(cpfField.getText())) {

                if (visitaAtual == null) visitaAtual = new Cliente();
                visitaAtual.setNome(nomeField.getText());
                visitaAtual.setCpf(cpfField.getText());
                visitaAtual.setEmail(emailField.getText());
                visitaAtual.setTelefone(telefoneField.getText());

                if (status.isSelected()){
                    visitaAtual.setStatus(StatusPessoa.ATIVO);
                }else {
                    visitaAtual.setStatus(StatusPessoa.DESATIVADO);
                }
                service.alter(visitaAtual);
                Stage stage = (Stage) nomeField.getScene().getWindow();
                if (visitasObservable != null) {
                    visitasObservable.add(visitaAtual);
                }
                stage.close();
            }else MainApp.mostrarAlerta("Erro", "CPF inválido!");;
        }*/
    }
    @FXML
    private void cancelar(){
        Stage stage = (Stage) campoCliente.getScene().getWindow();
        stage.close();
    }

}