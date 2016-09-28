/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jeopartymayal;

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

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
        // Leitor JSON
        JSONParser parser = new JSONParser();
        List<Pergunta> perguntas = new ArrayList<Pergunta>();
        
        
        
        
        //Threads ------------------------------------------
        
        //Single Thread
        
        Thread th = new Thread();
        
        
        
        //MultiThread
                
       
        IDaoManager manager1 = new JbdcDaoManager();
        IDaoManager manager2 = new JbdcDaoManager();
        IDaoManager manager3 = new JbdcDaoManager();
        IDaoManager manager4 = new JbdcDaoManager();
        
        try{
            JSONArray array = (JSONArray) parser.parse(new FileReader("src/JSON/dados.json"));

            for (Object object : array)
            {
                Pergunta pg = new Pergunta();                
                JSONObject pergunta = (JSONObject) object;
                DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");                
                
                pg.setCategory((String) pergunta.get("category"));                 
                pg.setAir_date((Date)formatter.parse((String) pergunta.get("air_date")));                
                pg.setQuestion((String) pergunta.get("question"));                
                pg.setValue((String) pergunta.get("value"));                
                pg.setAnswer((String) pergunta.get("answer"));                
                pg.setRound((String) pergunta.get("round"));             
                pg.setShow_number( Integer.parseInt((String) pergunta.get("show_number")));
                perguntas.add(pg);
            }
                

        }catch(Exception e){
            e.printStackTrace();
        }
        System.out.println(perguntas.size() + " registros lidos");        
        
        long tInicial,tFinal;
        try{
            manager1.iniciar();
            manager2.iniciar();
            manager3.iniciar();
            manager4.iniciar();
            PerguntaDao dao1 = manager1.getPerguntaDAO();
            PerguntaDao dao2 = manager2.getPerguntaDAO();
            PerguntaDao dao3 = manager3.getPerguntaDAO();
            PerguntaDao dao4 = manager4.getPerguntaDAO();
            
            InsertRunnable rn1 = new InsertRunnable(dao1, perguntas, 0, (perguntas.size()/4));
            InsertRunnable rn2 = new InsertRunnable(dao2, perguntas, (perguntas.size()/4), 2*(perguntas.size()/4));
            InsertRunnable rn3 = new InsertRunnable(dao3, perguntas, 2*(perguntas.size()/4), 3*(perguntas.size()/4));
            InsertRunnable rn4 = new InsertRunnable(dao4, perguntas, 3*(perguntas.size()/4), perguntas.size());
            
            Thread th1 = new Thread(rn1);
            Thread th2 = new Thread(rn2);
            Thread th3 = new Thread(rn3);
            Thread th4 = new Thread(rn4);
            
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
            System.out.println((rn1.getCont()+rn2.getCont()+rn3.getCont()+rn4.getCont())+" registros inseridos");
            System.out.println("Tempo para inserir: "+((tFinal-tInicial)/1000)+" segundos");
            manager1.confirmarTransacao();
            manager2.confirmarTransacao();
            manager3.confirmarTransacao();
            manager4.confirmarTransacao();
            manager1.encerrar();
            manager2.encerrar();
            manager3.encerrar();
            manager4.encerrar();
        }catch(Exception e){
            System.out.println(e.getMessage());
            manager1.encerrar();
            manager2.encerrar();
        } 
    }
}

        
        
        
    }
    
}
