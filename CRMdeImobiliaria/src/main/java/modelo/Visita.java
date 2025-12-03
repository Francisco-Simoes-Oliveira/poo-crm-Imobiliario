package modelo;

import javax.persistence.*;
import java.time.LocalDateTime;


@Entity
@Table(name = "visita")
public class Visita {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "idCliente", nullable = false)
    private Cliente cliente;

    @ManyToOne(optional = false)
    @JoinColumn(name = "idCorretor", nullable = false)
    private Funcionario funcionario;

    @ManyToOne(optional = false)
    @JoinColumn(name = "idImovel", nullable = false)
    private Imovel imovel;

    private LocalDateTime horarioVisita;

    @Enumerated(EnumType.STRING)
    private StatusVisita statos;

    @Column(length = 350)
    private String observacao;

    public Visita() {
    }

    public Visita(Cliente cliente, Funcionario funcionario, Imovel imovel, LocalDateTime horarioVisita, StatusVisita statos, String observacao) {
        this.cliente = cliente;
        this.funcionario = funcionario;
        this.imovel = imovel;
        this.horarioVisita = horarioVisita;
        this.statos = statos;
        this.observacao = observacao;
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

    public LocalDateTime getHorarioVisita() {
        return horarioVisita;
    }

    public void setHorarioVisita(LocalDateTime horarioVisita) {
        this.horarioVisita = horarioVisita;
    }

    public StatusVisita getStatosVisita() {
        return statos;
    }

    public void setStatosVisita(StatusVisita statos) {
        this.statos = statos;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }
}
