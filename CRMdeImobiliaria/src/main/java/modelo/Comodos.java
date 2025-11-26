package modelo;

import javax.persistence.*;

@Entity
@Table(name = "comodos")
public class Comodos{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int quarto;
    private int banheiro;
    private int cozinha;
    private int sala;
    private int lavanderia;
    private int garagem;

    public Comodos() {
    }

    public Comodos(int quarto, int banheiro, int cozinha, int sala, int lavanderia,int garagem) {
        this.quarto = quarto;
        this.banheiro = banheiro;
        this.cozinha = cozinha;
        this.sala = sala;
        this.lavanderia = lavanderia;
        this.garagem = garagem;
    }

    public Long getId() {
        return id;
    }

    public int getQuarto() {
        return quarto;
    }

    public void setQuarto(int quarto) {
        this.quarto = quarto;
    }

    public int getBanheiro() {
        return banheiro;
    }

    public void setBanheiro(int banheiro) {
        this.banheiro = banheiro;
    }

    public int getcozinha() {
        return cozinha;
    }

    public void setcozinha(int cozinha) {
        this.cozinha = cozinha;
    }

    public int getSala() {
        return sala;
    }

    public void setSala(int sala) {
        this.sala = sala;
    }

    public int getGaragem() {
        return garagem;
    }

    public void setGaragem(int garagem) {
        this.garagem = garagem;
    }
}
