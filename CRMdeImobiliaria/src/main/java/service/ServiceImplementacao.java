package service;

import dao.DaoGenerico;
import modelo.Cliente;
import modelo.Funcionario;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

public abstract class ServiceImplementacao<DAO extends DaoGenerico<T, ID>, T, ID> implements ServiceGenerico<DAO, T, ID> {

    protected DAO dao;

    public ServiceImplementacao(Class<DAO> daoClass) {
        try {
            this.dao = daoClass.getDeclaredConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                 NoSuchMethodException e) {
            throw new RuntimeException("Erro ao instanciar DAO: " + daoClass.getSimpleName(), e);
        }
    }

    public T add(T objeto){
        dao.add(objeto);
        return objeto;
    }
    public void add(List<T> objetos){
        for (T obj : objetos){
            dao.add(obj);
        }
    }

    public void alter(T objeto){
        dao.alter(objeto);
    }


    public List<T> buscarTodos(){
        return dao.buscaTodos();
    }
    public T buscaPorId(ID id){
        return dao.buscaPorId(id);
    }
    public T buscarPorNome(String nome) {
        return dao.buscarPorNome(nome);
    }

}
