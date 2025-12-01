package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import modelo.*;
import org.json.JSONObject;
import service.CargoService;
import service.FuncionarioService;
import service.ImovelService;
import util.JsonImporter;
import service.ClienteService;

import com.github.javafaker.Faker;


public class ConfiguracaoController {

    @FXML private Button clienteJson;

    @FXML private TextField clienteField;
    @FXML private TextField funcionarioField;
    @FXML private TextField imovelField;

    private final Faker faker = new Faker();

    public void initialize() {
        clienteField.textProperty().addListener((obs, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                clienteField.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
        funcionarioField.textProperty().addListener((obs, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                funcionarioField.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
        imovelField.textProperty().addListener((obs, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                imovelField.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
    }



    public void prencherBanco(){

        gerarClientes(Integer.parseInt(clienteField.getText()));
        gerarFuncionario(Integer.parseInt(funcionarioField.getText()));
        gerarImoveis(Integer.parseInt(imovelField.getText()));

        /*
        ClienteService serviceCliente = new ClienteService();

        ImovelService serviceImovel = new ImovelService();
        FuncionarioService serviceFunc = new FuncionarioService();
        CargoService cargoService = new CargoService();

        // Se o banco estiver vazio, popula com os dados do JSON
        if (serviceCliente.buscarTodos().isEmpty()) {
            List<Cliente> clientes = JsonImporter.carregarClientes();
            for (Cliente c : clientes) {
                serviceCliente.add(c);
            }
            System.out.println("Banco populado com dados do JSON!");
        } else System.out.println("ERRO: Banco Cliente populado");

        if (cargoService.buscarTodos().isEmpty()){
            cargoService.add(new Cargo("Gerente",6000.));
            cargoService.add(new Cargo("Corretor",3000.));
        }

        if (serviceFunc.buscarTodos().isEmpty()){
           serviceFunc.add(new Funcionario("Daniel Alves", "00000000000", "44000000000",cargoService.buscarPorNome("Corretor")));
           serviceFunc.add(new Funcionario("Toninho tornado", "00000000000", "44000000000",cargoService.buscarPorNome("Corretor")));
           serviceFunc.add(new Funcionario("Zico Corninhos", "00000000000", "44000000000",cargoService.buscarPorNome("Corretor")));
           serviceFunc.add(new Funcionario("Francisco S", "00000000000", "44000000000",cargoService.buscarPorNome("Gerente")));
        }

        if (serviceImovel.buscarTodos().isEmpty()) {
            List<Imovel> imoveis = JsonImporter.carregarImoveis();
            for (Imovel i : imoveis) {
                serviceImovel.add(i);
            }
            System.out.println("Banco de imóveis populado com dados do JSON!");
        } else {
            System.out.println("ERRO: Banco de imóveis já está populado");
        }*/
    }


    public void gerarClientes(int quantidade) {
        ClienteService serviceCliente = new ClienteService();

        for (int i = 0; i < quantidade; i++) {

            String nome = faker.name().fullName();
            String cpf = faker.number().digits(11);
            String email = faker.internet().emailAddress();
            String telefone = faker.phoneNumber().cellPhone().replaceAll("[^0-9]", "");

            Cliente c = new Cliente(nome, cpf, email, telefone);

            serviceCliente.add(c);

            if (i % 1000 == 0) {
                System.out.println("Inseridos: " + i);
            }
        }
    }

    public void gerarFuncionario(int quantidade) {
        FuncionarioService serviceFunc = new FuncionarioService();
        CargoService cargoService = new CargoService();

        for (int i = 0; i < quantidade; i++) {

            String nome = faker.name().fullName();
            String cpf = faker.number().digits(11);
            String email = faker.internet().emailAddress();
            String telefone = faker.phoneNumber().cellPhone().replaceAll("[^0-9]", "");

            Funcionario f = new Funcionario(nome, cpf, email, telefone, cargoService.buscaPorId(2L));

            serviceFunc.add(f);

            if (i % 1000 == 0) {
                System.out.println("Inseridos: " + i);
            }
        }
    }

    public void gerarImoveis(int qtd) {
        ImovelService imovelService = new ImovelService();
        FuncionarioService funcService = new FuncionarioService();

        String[] cepsValidos = {
                "01001-000", "01025-020", "01311-000",
                "02047-000", "04538-132", "05001-900",
                "20031-170", "20230-021", "22230-010",
                "22250-040", "30130-010", "30160-011",
                "30310-150", "40020-000", "40100-110",
                "40301-155", "80010-010", "80060-000",
                "60060-440", "60125-100", "69005-070",
                "69020-010", "90010-140", "90035-003"
        };
        for (int i = 0; i < qtd; i++) {

            String cep = cepsValidos[faker.number().numberBetween(0, cepsValidos.length)];
            JSONObject json = Endereco.buscaViaCep(cep);

            if (json == null || json.has("erro")) {
                System.out.println("CEP inválido: " + cep);
                i--;
                continue;
            }

            Endereco end = new Endereco(
                    cep,
                    json.getString("logradouro"),
                    json.getString("bairro"),
                    json.getString("localidade"),
                    json.getString("uf"),
                    faker.number().numberBetween(1, 9999) + "",
                    faker.lorem().sentence()
            );

            Comodos com = new Comodos(
                    faker.number().numberBetween(1, 5),
                    faker.number().numberBetween(1, 3),
                    faker.number().numberBetween(1, 2),
                    faker.number().numberBetween(1, 2),
                    faker.number().numberBetween(0, 2),
                    faker.number().numberBetween(1, 2)
            );

            StatusImovel status = StatusImovel.values()
                    [faker.number().numberBetween(0, StatusImovel.values().length)];

            Funcionario funcionario = funcService.buscaPorId(
                    faker.number().numberBetween(1L, 10L)); // exemplo

            Imovel im = new Imovel(end,
                    faker.number().randomDouble(2, 100000, 1000000),
                    com,
                    status,
                    funcionario);

            imovelService.add(im);
        }

        System.out.println("Imóveis gerados: " + qtd);
    }



}
