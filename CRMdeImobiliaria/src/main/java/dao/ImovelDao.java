package dao;

import modelo.Endereco;
import modelo.Imovel;

import javax.persistence.EntityManager;

public class ImovelDao extends DaoImplementacao<Imovel,Long> {
    public ImovelDao() {
        super(Imovel.class);
    }

    public Imovel buscarPorLogradouro(String logradouro){
        EntityManager em = emf.createEntityManager();
        String sql = "SELECT i FROM Imovel i WHERE LOWER(i.endereco.logradouro) = LOWER(:logradouro)";
        Imovel imovel = em.createQuery(sql, Imovel.class).setParameter("logradouro", logradouro).setMaxResults(1).getSingleResult();
        em.close();

        return imovel;
    }

}
