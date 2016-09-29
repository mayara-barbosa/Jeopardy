package main;

import dao.PerguntaDao;
import model.Pergunta;
import java.util.List;

/**
 *
 * @author mayara-barbosa
 */
public class JeopardyRunnable implements Runnable{
    
    private PerguntaDao dao;
    List<Pergunta> perguntas;
    private int inicio, fim, cont;

    public JeopardyRunnable(PerguntaDao dao, List<Pergunta> perguntas, int inicio, int fim) {
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
