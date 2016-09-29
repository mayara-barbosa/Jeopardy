/*package main;

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
import model.Pergunta;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class Program {
    
    public static void main(String[] args) throws IOException {
        
        JSONParser parser = new JSONParser();
        List<Pergunta> perguntas = new ArrayList<Pergunta>();
        //IDaoManager manager = new JbdcDaoManager();
        
        try{
            JSONArray array = (JSONArray) parser.parse(new FileReader("src/json/dados.json"));

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
        int cont = 0;
        try{
            manager.iniciar();
            PerguntaDao dao = manager.getPerguntaDAO();
            tInicial = System.currentTimeMillis();
            for(Pergunta pergunta : perguntas){
                dao.inserir(pergunta);
                cont++;
            }
            tFinal = System.currentTimeMillis();
            System.out.println(cont+" registros inseridos");
            System.out.println("Tempo para inserir: "+((tFinal-tInicial)/1000)+" segundos");
            manager.confirmarTransacao();
            manager.encerrar();
        }catch(Exception e){
            System.out.println(e.getMessage());
            manager.encerrar();
        } 
    }
}
*/