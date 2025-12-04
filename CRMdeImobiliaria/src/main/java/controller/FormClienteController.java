package controller;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import modelo.Cliente;
import modelo.StatusPessoa;
import service.ClienteService;
import view.MainApp;

import java.awt.event.ActionEvent;

public class FormClienteController {
    private ClienteService service = new ClienteService();

    @FXML private TextField nomeField;
    @FXML private TextField cpfField;
    @FXML private TextField emailField;
    @FXML private TextField telefoneField;
    @FXML private RadioButton status;

    private Cliente clienteAtual;
    private ObservableList<Cliente> clientesObservable;

    public void setClientesObservable(ObservableList<Cliente> clientesObservable) {
        this.clientesObservable = clientesObservable;
    }
    public void setCliente(Cliente cliente) {
        this.clienteAtual = cliente;
        // Preenche os campos de texto normalmente
        nomeField.setText(cliente.getNome());
        cpfField.setText(cliente.getCpf());
        emailField.setText(cliente.getEmail());
        telefoneField.setText(cliente.getTelefone());

        // 🔹 Seleciona ou não o RadioButton conforme o status
        if (cliente.getStatus() == StatusPessoa.ATIVO) {
            status.setSelected(true);
        } else {
            status.setSelected(false);
        }

    }


    @FXML
    private void salvar() {

        if (nomeField.getText().isEmpty() || cpfField.getText().isEmpty()) {
            MainApp.mostrarAlerta("Erro", "O campo nome e CPF são obrigatórios!");
            return;
        }
        if(!nomeField.getText().isEmpty() && !cpfField.getText().isEmpty()) {
            if (Cliente.validarCpf(cpfField.getText())) {

                if (clienteAtual == null) clienteAtual = new Cliente();
                clienteAtual.setNome(nomeField.getText());
                clienteAtual.setCpf(cpfField.getText());
                clienteAtual.setEmail(emailField.getText());
                clienteAtual.setTelefone(telefoneField.getText());

                if (status.isSelected()){
                    clienteAtual.setStatus(StatusPessoa.ATIVO);
                }else {
                    clienteAtual.setStatus(StatusPessoa.DESATIVADO);
                }
                service.alter(clienteAtual);
                Stage stage = (Stage) nomeField.getScene().getWindow();
                if (clientesObservable != null) {
                    clientesObservable.add(clienteAtual);
                }
                stage.close();
            }else MainApp.mostrarAlerta("Erro", "CPF inválido!");;
        }
    }
    @FXML
    private void cancelar(){
        Stage stage = (Stage) nomeField.getScene().getWindow();
        stage.close();
    }
}

