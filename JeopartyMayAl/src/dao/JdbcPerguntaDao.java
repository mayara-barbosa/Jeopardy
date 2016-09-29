/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import model.Pergunta;

/**
 *
 * @author Usuario
 */
public class JdbcPerguntaDao implements PerguntaDao{
    private Connection conexao;

    public JdbcPerguntaDao(Connection conexao) {
        this.conexao = conexao;
    }
    
    @Override
    public void inserir(Pergunta pergunta) {
        String sql = "INSERT INTO perguntas ("
                + "category, "
                + "air_date, "
                + "question, "
                + "value, "
                + "answer, "
                + "round,"
                + "show_number) "
                + "VALUES(?,?,?,?,?,?,?)";
        PreparedStatement ps;
        try{
            ps = conexao.prepareStatement(sql);
            ps.setString(1, pergunta.getCategory());
            ps.setDate(2, new java.sql.Date(pergunta.getAir_date().getTime()));
            ps.setString(3, pergunta.getQuestion());
            ps.setString(4, pergunta.getValue());
            ps.setString(5, pergunta.getAnswer());
            ps.setString(6, pergunta.getRound());
            ps.setInt(7, pergunta.getShow_number());
            ps.executeUpdate();
        }catch(SQLException ex){
            throw new DaoException("Ocorreu um erro ao inserir no banco de dados"+
                ex.getMessage());
        }
    }

    
}


