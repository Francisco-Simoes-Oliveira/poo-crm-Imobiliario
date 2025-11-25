package controller;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import modelo.Imovel;
import modelo.Endereco;
import modelo.StatusPessoa;
import org.json.JSONObject;
import service.ImovelService;
import view.MainApp;

public class FormImovelController {
    private ImovelService service = new ImovelService();

    @FXML private TextField nomeCidadeField;
    @FXML private TextField cepField;
    @FXML private TextField logradouroField;
    @FXML private TextField ufField;
    @FXML private TextField numeroField;
    @FXML private TextField complementoField;



    private Imovel imovelAtual;
    private ObservableList<Imovel> imovelsObservable;

    public void setImovelsObservable(ObservableList<Imovel> imovelsObservable) {
        this.imovelsObservable = imovelsObservable;
    }
    public void setImovel(Imovel imovel) {
        this.imovelAtual = imovel;
        // Preenche os campos de texto normalmente
        nomeCidadeField.setText(imovel.getEndereco().getCidade());
        cepField.setText(imovel.getEndereco().getCep());
        logradouroField.setText(imovel.getEndereco().getLogradouro());
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
    }
}
