package controller;

import javafx.collections.ObservableList;
import modelo.Imovel;
import modelo.Visita;

public class FormVisitaController {

    private Visita visitaAtual;
    private ObservableList<Visita> visitasObservable;



    public void setVisitasObservable(ObservableList<Visita> visitasObservable) {
        this.visitasObservable = visitasObservable;
    }
    public void setVisita(Visita visita){

    }
}
