package modelo;

import javax.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.time.LocalDateTime;
import java.util.Date;

@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
@Table(name = "imovel")
public class Imovel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "idEndereco", referencedColumnName = "id")
    private Endereco endereco;

    private Double preco;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "idComodos", referencedColumnName = "id")
    private Comodos comodos;

    @Enumerated(EnumType.STRING)
    private StatusImovel statusImovel;

    @ManyToOne
    @JoinColumn(name = "idCorretor", referencedColumnName = "id")
    private Funcionario funcionario;

    private LocalDateTime dataCriacao;

    public Imovel() {}

    public Imovel(Endereco endereco, Double preco, Comodos comodos, StatusImovel statusImovel, Funcionario funcionario) {
        this.endereco = endereco;
        this.preco = preco;
        this.comodos = comodos;
        this.statusImovel = statusImovel;
        this.funcionario = funcionario;
        this.dataCriacao = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public Double getPreco() {
        return preco;
    }

    public void setPreco(Double preco) {
        this.preco = preco;
    }

    public Comodos getComodos() {
        return comodos;
    }

    public void setComodos(Comodos comodos) {
        this.comodos = comodos;
    }

    public StatusImovel getStatus() {
        return statusImovel;
    }

    public void setStatus(StatusImovel statusImovel) {
        this.statusImovel = statusImovel;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    public String getLogradoro(){
        if (endereco== null)return "";
        return endereco.getLogradoro();
    }

    public Funcionario getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(Funcionario funcionario) {
        this.funcionario = funcionario;
    }

}
