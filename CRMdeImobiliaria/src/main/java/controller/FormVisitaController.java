package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import modelo.*;
import service.ClienteService;
import service.FuncionarioService;
import javafx.scene.control.ListView;
import service.ImovelService;
import service.VisitaService;
import view.MainApp;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class FormVisitaController {

    @FXML private ChoiceBox<String> statusBox;
    @FXML private TextField campoCliente;
    @FXML private ListView<Cliente> listaSugestoesCliente;
    @FXML private TextField campoFuncionario;
    @FXML private ListView<Funcionario> listaSugestoesFunc;
    @FXML private TextField campoImovel;
    @FXML private ListView<Imovel> listaSugestoesImovel;
    @FXML private DatePicker date;
    @FXML private TextField hora;

    private Visita visitaAtual;
    private ObservableList<Visita> visitasObservable;

    private ObservableList<Cliente> listaClientes;
    private ObservableList<Funcionario> listaFuncionarios;
    private ObservableList<Imovel> listaImovel;

    private VisitaService visitaService = new VisitaService();
    private ImovelService imovelService = new ImovelService();
    private ClienteService clienteService = new ClienteService();
    private FuncionarioService funcService = new FuncionarioService();


    public void setVisitasObservable(ObservableList<Visita> visitasObservable) {
        this.visitasObservable = visitasObservable;
    }

    public void setVisita(Visita visita) {
        this.visitaAtual = visita;

        statusBox.setValue(visita.getStatus());
        campoCliente.setText(visita.getCliente().getNome());
        campoFuncionario.setText(visita.getFuncionario().getNome());
        campoImovel.setText(visita.getLogradouro());
        date.setValue(visita.getHorarioVisita().toLocalDate());
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm");
        hora.setText(visita.getHorarioVisita().toLocalTime().format(dtf));
    }


    public void initialize() {
        listaSugestoesFunc.setVisible(false);
        listaSugestoesFunc.setManaged(false);
        listaSugestoesCliente.setVisible(false);
        listaSugestoesCliente.setManaged(false);
        listaSugestoesImovel.setVisible(false);
        listaSugestoesImovel.setManaged(false);


        configurarAutocompletef();

        configurarAutocompletec();

        configurarAutocompletei();

    }

    private void configurarAutocompletef() {

        listaFuncionarios = FXCollections.observableArrayList(funcService.buscarTodos());

        listaSugestoesFunc.setItems(listaFuncionarios);

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


    private void configurarAutocompletec() {

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

    private void configurarAutocompletei() {

        listaImovel = FXCollections.observableArrayList(imovelService.buscarTodos());

        listaSugestoesImovel.setItems(listaImovel);

        // Quando digitar → filtra
        campoImovel.textProperty().addListener((obs, old, novo) -> {
            if (novo.isBlank()) {
                listaSugestoesImovel.setVisible(false);
                listaSugestoesImovel.setManaged(false);
                return;
            }

            listaSugestoesImovel.setItems(
                    listaImovel.filtered(i ->
                            i.getEndereco().getLogradouro().toLowerCase().contains(novo.toLowerCase())
                    )
            );
            listaSugestoesImovel.setVisible(true);
            listaSugestoesImovel.setManaged(true);
        });

        listaSugestoesImovel.setOnMouseClicked(ev -> {
            Imovel i = listaSugestoesImovel.getSelectionModel().getSelectedItem();
            if (i != null) {
                campoImovel.setText(i.getEndereco().getLogradouro());
                listaSugestoesImovel.setVisible(false);
                listaSugestoesImovel.setManaged(false);
            }
        });

    }


    @FXML
    private void salvar() {

        if (campoCliente.getText().isEmpty() || campoFuncionario.getText().isEmpty() || campoImovel.getText().isEmpty()
        || date == null || hora.getText().isEmpty()) {
            MainApp.mostrarAlerta("Erro", "Todos os campo são obrigatórios!");
            return;
        }

        if (visitaAtual == null) visitaAtual = new Visita();
        visitaAtual.setCliente(clienteService.buscarPorNome(campoCliente.getText()));
        visitaAtual.setFuncionario(funcService.buscarPorNome(campoFuncionario.getText()));
        visitaAtual.setImovel(imovelService.buscarPorLogradouro(campoImovel.getText()));

        switch (statusBox.getValue()){
            case "AGENDADA":
                visitaAtual.setStatus(StatusVisita.AGENDADA);
                break;
            case "CANCELADA":
                visitaAtual.setStatus(StatusVisita.CANCELADA);
                break;
            case "REALIZADA":
                visitaAtual.setStatus(StatusVisita.REALIZADA);
                break;
        }


        DateTimeFormatter fmtHora = DateTimeFormatter.ofPattern("HH:mm");
        LocalTime time = LocalTime.parse(hora.getText(),fmtHora);

        visitaAtual.setHorarioVisita(LocalDateTime.of(date.getValue(),time));

        visitaService.alter(visitaAtual);
        Stage stage = (Stage) campoCliente.getScene().getWindow();
        if (visitasObservable != null) {
            visitasObservable.add(visitaAtual);
        }
        stage.close();
    }
    @FXML
    private void cancelar(){
        Stage stage = (Stage) campoCliente.getScene().getWindow();
        stage.close();
    }

}