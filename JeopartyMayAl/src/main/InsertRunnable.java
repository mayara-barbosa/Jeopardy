/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import dao.PerguntaDao;
import model.Pergunta;
import java.util.List;

/**
 *
 * @author Usuario
 */
public class InsertRunnable implements Runnable{
    private PerguntaDao dao;
    List<Pergunta> perguntas;
    private int inicio, fim, cont;

    public InsertRunnable(PerguntaDao dao, List<Pergunta> perguntas, int inicio, int fim) {
        this.dao = dao;
        this.perguntas = perguntas;
        this.inicio = inicio;
        this.fim = fim;
        this.cont = 0;
    }
    
    @Override
    public void run() {
        for (int i = inicio; i < fim; i++) {
            dao.inserir(perguntas.get(i));
            cont++;
        }
    }

    public int getCont() {
        return cont;
    }
}
