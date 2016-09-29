/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import model.Pergunta;
import dao.IDaoManager;
import dao.JdbcDaoManager;
import dao.PerguntaDao;
import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

/**
 *
 * @author silveiraalexand
 */
public class JeopartyMayAl {

    public static void main(String[] args) {
        long tInicial,tFinal;   
        JSONParser parser = new JSONParser();
        List<Pergunta> perguntas = new ArrayList<Pergunta>();
                        
       //conexâo com bd para multi thread
        IDaoManager con1 = new JdbcDaoManager();
        IDaoManager con2 = new JdbcDaoManager();
        IDaoManager con3 = new JdbcDaoManager();
        IDaoManager con4 = new JdbcDaoManager();
        
        try{            
            // Array Json para pegar arquivo
            JSONArray array = (JSONArray) parser.parse(new FileReader("src/json/dados.json"));
           
            //popula array
            for (Object object : array)
            {
                Pergunta perg = new Pergunta();                
                JSONObject pergunta = (JSONObject) object;
                DateFormat formater = new SimpleDateFormat("yyyy-MM-dd");                
                
                perg.setCategory((String) pergunta.get("category"));                 
                perg.setAir_date((Date)formater.parse((String) pergunta.get("air_date")));                
                perg.setQuestion((String) pergunta.get("question"));                
                perg.setValue((String) pergunta.get("value"));                
                perg.setAnswer((String) pergunta.get("answer"));                
                perg.setRound((String) pergunta.get("round"));             
                perg.setShow_number( Integer.parseInt((String) pergunta.get("show_number")));
                perguntas.add(perg);
            }               

        }catch(Exception e){
            e.printStackTrace();
        }
        System.out.println(perguntas.size() + " registros");        
        
        
        try{
            con1.iniciar();
            con2.iniciar();
            con3.iniciar();
            con4.iniciar();
            
            PerguntaDao dao1 = con1.getPerguntaDAO();
            PerguntaDao dao2 = con2.getPerguntaDAO();
            PerguntaDao dao3 = con3.getPerguntaDAO();
            PerguntaDao dao4 = con4.getPerguntaDAO();
            
            JeopardyRunnable jrn1 = new JeopardyRunnable(dao1, perguntas, 0, (perguntas.size()/4));
            JeopardyRunnable jrn2 = new JeopardyRunnable(dao2, perguntas, (perguntas.size()/4), 2*(perguntas.size()/4));
            JeopardyRunnable jrn3 = new JeopardyRunnable(dao3, perguntas, 2*(perguntas.size()/4), 3*(perguntas.size()/4));
            JeopardyRunnable jrn4 = new JeopardyRunnable(dao4, perguntas, 3*(perguntas.size()/4), perguntas.size());
            
            Thread th1 = new Thread(jrn1);
            Thread th2 = new Thread(jrn2);
            Thread th3 = new Thread(jrn3);
            Thread th4 = new Thread(jrn4);
            
            tInicial = System.currentTimeMillis();
            
            th1.start();
            th2.start();
            th3.start();
            th4.start();
            
            th1.join();
            th2.join();
            th3.join();
            th4.join();
            
            tFinal = System.currentTimeMillis();
            
            System.out.println((jrn1.getCont()+jrn2.getCont()+jrn3.getCont()+jrn4.getCont())+" registros inseridos");
            System.out.println("Tempo para inserir: "+((tFinal-tInicial)/1000)+" segundos");
            
            con1.confirmarTransacao();
            con2.confirmarTransacao();
            con3.confirmarTransacao();
            con4.confirmarTransacao();
            
            con1.encerrar();
            con2.encerrar();
            con3.encerrar();
            con4.encerrar();
            
        }catch(Exception e){
            System.out.println(e.getMessage());
            con1.encerrar();
            con2.encerrar();
            con3.encerrar();
            con4.encerrar();
        } 
    }
}

        
        
        
    
