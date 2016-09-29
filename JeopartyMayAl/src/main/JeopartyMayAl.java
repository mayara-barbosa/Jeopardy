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
        
        //Thread th = new Thread();
        
        
       //MultiThread
                
       
        IDaoManager conexao1 = new JdbcDaoManager();
        IDaoManager conexao2 = new JdbcDaoManager();
        IDaoManager conexao3 = new JdbcDaoManager();
        IDaoManager conexao4 = new JdbcDaoManager();
        
        try{
            
            // Array Json para pegar arquivo
            JSONArray array = (JSONArray) parser.parse(new FileReader("src/JSON/dados.json"));

            
            //
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
            
            conexao1.iniciar();
            conexao2.iniciar();
            conexao3.iniciar();
            conexao4.iniciar();
            
            PerguntaDao dao1 = conexao1.getPerguntaDAO();
            PerguntaDao dao2 = conexao2.getPerguntaDAO();
            PerguntaDao dao3 = conexao3.getPerguntaDAO();
            PerguntaDao dao4 = conexao4.getPerguntaDAO();
            
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
            
            conexao1.confirmarTransacao();
            conexao2.confirmarTransacao();
            conexao3.confirmarTransacao();
            conexao4.confirmarTransacao();
            
            conexao1.encerrar();
            conexao2.encerrar();
            conexao3.encerrar();
            conexao4.encerrar();
            
        }catch(Exception e){
            System.out.println(e.getMessage());
            conexao1.encerrar();
            conexao2.encerrar();
        } 
    }
}

        
        
        
    
