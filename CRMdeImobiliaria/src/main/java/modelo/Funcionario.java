package modelo;

import javax.persistence.*;

@Entity
@Table(name = "corretor")
public class Funcionario extends Pessoa{

    @ManyToOne
    @JoinColumn(name = "idCargo")
    private Cargo cargo;

    public Funcionario() {
        super();
    }

    public Funcionario(String nome, String cpf, String email, String telefone) {
        super(nome, cpf, email, telefone);
    }

    public Funcionario(String nome, String cpf, String email, String telefone, Cargo cargo) {
        super(nome, cpf, email, telefone);
        this.cargo = cargo;
    }

    public Funcionario(String nome, String cpf, String telefone, Cargo cargo) {
        super(nome, cpf, telefone);
        this.cargo = cargo;
    }

    public Funcionario(String nome, String cpf, Cargo cargo) {
        super(nome, cpf);
        this.cargo = cargo;
    }

    public Cargo getCargo() {
        return cargo;
    }

    public void setCargo(Cargo cargo) {
        this.cargo = cargo;
    }

    @Override
    public String toString() {
        return this.getNome();
    }
}
