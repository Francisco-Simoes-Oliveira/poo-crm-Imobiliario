package service;

import dao.PropostaDao;
import modelo.*;
import view.MainApp;

import java.time.LocalDate;

public class PropostaService extends ServiceImplementacao<PropostaDao, Proposta,Long>{
    public PropostaService() {
        super(PropostaDao.class);
    }

    public Proposta add(Proposta proposta){
        if (proposta.getCliente().getStatus() == StatusPessoa.DESATIVADO || proposta.getImovel().getStatusVisita() == StatusImovel.VENDIDO){
            MainApp.mostrarAlerta("ERRO","Cliente ou Imovel com o Status indevido");
            return proposta;
        }
        dao.add(proposta);
        return proposta;
    }
    public Proposta add(Cliente cliente, Funcionario funcionario, Imovel imovel, Double valorProposto , StatusProposta statusProposta, LocalDate prazoResposta){
        if (cliente.getStatus() == StatusPessoa.DESATIVADO || imovel.getStatusVisita() == StatusImovel.VENDIDO){
            MainApp.mostrarAlerta("ERRO","Cliente ou Imovel com o Status indevido");
            return null;
        }
        Proposta proposta = new Proposta(cliente,funcionario,imovel,valorProposto,statusProposta,prazoResposta);
        dao.add(proposta);
        return proposta;
    }
}
