/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.io.FileWriter;
import java.io.IOException;
import org.json.simple.JSONObject;


/**
 *
 * @author silveiraalexand
 */
public class CriarJson {
   

	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
		
		//Cria um Objeto JSON
		JSONObject jsonObject = new JSONObject();
		
		FileWriter writeFile = null;
		
		//Armazena dados em um Objeto JSON
		jsonObject.put("nome", "Allan");
		jsonObject.put("sobrenome", "Romanato");
		jsonObject.put("pais", "Brasil");
		jsonObject.put("estado", "SP");
		
		try{
			writeFile = new FileWriter("saida.json");
			//Escreve no arquivo conteudo do Objeto JSON
			writeFile.write(jsonObject.toJSONString());
			writeFile.close();
		}
		catch(IOException e){
			e.printStackTrace();
		}
		
		//Imprimne na Tela o Objeto JSON para vizualização
		System.out.println(jsonObject);

	}

}
    

