/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

/**
 *
 * @author alexandre.chaves
 */
public interface IDaoManager {
    
    void iniciar();
    void encerrar();
    void confirmarTransacao();
    void abortarTransacao();
    PerguntaDao getPerguntaDAO();
}
