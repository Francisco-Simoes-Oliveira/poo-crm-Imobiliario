package service;


import dao.ComodoDao;
import dao.ImovelDao;
import modelo.*;

import java.util.List;

public class ImovelService extends ServiceImplementacao<ImovelDao,Imovel,Long> {
    private ImovelDao dao = new ImovelDao();

    public ImovelService() {
        super(ImovelDao.class);
    }

    public void add(Imovel imovel){
        dao.add(imovel);
    }
    public void add(Endereco endereco, Double preco, Comodos comodos, StatusImovel statusImovel, Funcionario funcionario){
        Imovel Imovel = new Imovel(endereco,preco,comodos, statusImovel, funcionario);
        dao.add(Imovel);
    }

    public void add( String cep,String logradouro, String bairro, String cidade, String uf, String numero, String complemento, Double preco, int quarto, int banheiro, int cozinha, int sala, int lavanderia,int garagem, StatusImovel status, Funcionario funcionario) {
        Comodos comodos = new Comodos(quarto, banheiro, cozinha,sala,lavanderia,garagem);
        Endereco endereco = new Endereco(cep,logradouro,bairro,cidade,uf,numero, complemento);
        Imovel imovel = new Imovel(endereco,preco, comodos, status, funcionario);

        dao.add(imovel);
    }


    public List<Imovel> buscarTodos(){
        return dao.buscaTodos();
    }
}
