/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

/**
 *
 * @author Usuario
 */
import java.sql.Connection;
import model.Pergunta;

public interface PerguntaDao {
    void inserir(Pergunta pergunta);
}
