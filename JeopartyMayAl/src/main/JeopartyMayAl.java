package main;

import model.Pergunta;
import dao.IDaoManager;
import dao.JdbcDaoManager;
import dao.JdbcPerguntaDao;
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
        long tempoInicial,tempoFinal;   
        JSONParser parser = new JSONParser();
        List<Pergunta> perguntas = new ArrayList<Pergunta>();
                        
       //conexâo com bd para multi thread
       
        IDaoManager con1 = new JdbcDaoManager();
        IDaoManager con2 = new JdbcDaoManager();
        IDaoManager con3 = new JdbcDaoManager();
        IDaoManager con4 = new JdbcDaoManager();
        
       
        //conexao single thread
        IDaoManager con5 = new JdbcDaoManager();
        
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
            //SINGLE THREAD
            con5.iniciar();
            PerguntaDao dao5 = con5.getPerguntaDAO();
            JeopardyRunnable jrun = new JeopardyRunnable(dao5, perguntas, 0, perguntas.size());
            Thread th5 = new Thread(jrun);
            tempoInicial = System.currentTimeMillis();
            th5.start();
            th5.join();
            tempoFinal = System.currentTimeMillis();
            System.out.println(jrun.getCont() + " registros");
            System.out.println("Tempo total SINGLE THREAD: "+((tempoFinal-tempoInicial)/1000)+" segundos");
            con5.confirmarTransacao();            
            con5.encerrar();
             
           
            con1.iniciar();
            con2.iniciar();
            con3.iniciar();
            con4.iniciar();           
            
            PerguntaDao dao1 = con1.getPerguntaDAO();
            PerguntaDao dao2 = con2.getPerguntaDAO();
            PerguntaDao dao3 = con3.getPerguntaDAO();
            PerguntaDao dao4 = con4.getPerguntaDAO();
            
            JeopardyRunnable jrun1 = new JeopardyRunnable(dao1, perguntas, 0, (perguntas.size()/4));
            JeopardyRunnable jrun2 = new JeopardyRunnable(dao2, perguntas, (perguntas.size()/4), 2*(perguntas.size()/4));
            JeopardyRunnable jrun3 = new JeopardyRunnable(dao3, perguntas, 2*(perguntas.size()/4), 3*(perguntas.size()/4));
            JeopardyRunnable jrun4 = new JeopardyRunnable(dao4, perguntas, 3*(perguntas.size()/4), perguntas.size());
            
            Thread th1 = new Thread(jrun1);
            Thread th2 = new Thread(jrun2);
            Thread th3 = new Thread(jrun3);
            Thread th4 = new Thread(jrun4);
            
            tempoInicial = System.currentTimeMillis();
            
            th1.start();
            th2.start();
            th3.start();
            th4.start();
            
            th1.join();
            th2.join();
            th3.join();
            th4.join();
            
            tempoFinal = System.currentTimeMillis();
            
            System.out.println((jrun1.getCont()+jrun2.getCont()+jrun3.getCont()+jrun4.getCont())+" registros ");
            System.out.println("Tempo total MULTI THREAD: "+((tempoFinal-tempoInicial)/1000)+" segundos");
            
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
            con5.encerrar();
        } 
    }
}

        
        
        
    
