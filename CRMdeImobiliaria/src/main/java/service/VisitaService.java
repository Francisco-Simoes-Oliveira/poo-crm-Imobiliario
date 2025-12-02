package service;

import dao.VisitaDao;
import modelo.*;
import view.MainApp;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class VisitaService extends ServiceImplementacao<VisitaDao, Visita,Long>{
    public VisitaService() {
        super(VisitaDao.class);
    }

    public Visita add(Visita visita){
        if (visita.getCliente().getStatus() == StatusPessoa.DESATIVADO || visita.getImovel().getStatus() == StatusImovel.VENDIDO){
            MainApp.mostrarAlerta("ERRO","Cliente ou Imovel com o Status indevido");
            return visita;
        }
        dao.add(visita);
        return visita;
    }

    public Visita add(Cliente cliente, Funcionario funcionario, Imovel imovel, LocalDateTime horarioVisita, StatusVisita statosVisita, String observacao){
        if (cliente.getStatus() == StatusPessoa.DESATIVADO || imovel.getStatus() == StatusImovel.VENDIDO){
            MainApp.mostrarAlerta("ERRO","Cliente ou Imovel com o Status indevido");
            return null;
        }
        Visita visita = new Visita(cliente,funcionario,imovel,horarioVisita,statosVisita,observacao);
        dao.add(visita);
        return visita;
    }
}
