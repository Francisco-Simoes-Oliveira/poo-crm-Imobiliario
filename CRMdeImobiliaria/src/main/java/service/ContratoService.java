package service;

import dao.ContratoDao;
import modelo.Contrato;

public class ContratoService extends ServiceImplementacao<ContratoDao, Contrato,Long>{
    public ContratoService() {
        super(ContratoDao.class);
    }
}
