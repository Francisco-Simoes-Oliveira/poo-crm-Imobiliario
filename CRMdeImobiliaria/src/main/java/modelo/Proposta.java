package modelo;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "proposta")
public class Proposta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "idCliente", nullable = false)
    private Cliente cliente;

    @ManyToOne(optional = false)
    @JoinColumn(name = "idFuncionario", nullable = false)
    private Funcionario funcionario;

    @ManyToOne(optional = false)
    @JoinColumn(name = "idImovel", nullable = false)
    private Imovel imovel;

    private Double valorProposto;

    private LocalDateTime horarioDeEnvio;

    @Enumerated(EnumType.STRING)
    private StatusProposta statusProposta;

    private LocalDate prazoResposta;

    public Proposta() {
    }

    public Proposta(Cliente cliente, Funcionario funcionario, Imovel imovel, Double valorProposto, LocalDateTime horarioDeEnvio, StatusProposta statusProposta, LocalDate prazoResposta) {
        this.cliente = cliente;
        this.funcionario = funcionario;
        this.imovel = imovel;
        this.valorProposto = valorProposto;
        this.horarioDeEnvio = horarioDeEnvio;
        this.statusProposta = statusProposta;
        this.prazoResposta = prazoResposta;
    }

    public Proposta(Cliente cliente, Funcionario funcionario, Imovel imovel, Double valorProposto , StatusProposta statusProposta, LocalDate prazoResposta) {
        this.cliente = cliente;
        this.funcionario = funcionario;
        this.imovel = imovel;
        this.valorProposto = valorProposto;
        this.horarioDeEnvio = LocalDateTime.now();
        this.statusProposta = statusProposta;
        this.prazoResposta = prazoResposta;
    }

    public Long getId() {
        return id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Funcionario getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(Funcionario funcionario) {
        this.funcionario = funcionario;
    }

    public Imovel getImovel() {
        return imovel;
    }

    public void setImovel(Imovel imovel) {
        this.imovel = imovel;
    }

    public Double getValorProposto() {
        return valorProposto;
    }

    public void setValorProposto(Double valorProposto) {
        this.valorProposto = valorProposto;
    }

    public LocalDateTime getHorarioDeEnvio() {
        return horarioDeEnvio;
    }

    public void setHorarioDeEnvio(LocalDateTime horarioDeEnvio) {
        this.horarioDeEnvio = horarioDeEnvio;
    }

    public StatusProposta getStatusProposta() {
        return statusProposta;
    }

    public void setStatusProposta(StatusProposta statusProposta) {
        this.statusProposta = statusProposta;
    }

    public LocalDate getPrazoResposta() {
        return prazoResposta;
    }

    public void setPrazoResposta(LocalDate prazoResposta) {
        this.prazoResposta = prazoResposta;
    }
}
