
 
package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author alexandre.chaves
 */

public class JdbcDaoManager implements IDaoManager{
    Connection conexao;
    PerguntaDao perguntaDao;

    public JdbcDaoManager() {
    }
    
    @Override
    public void iniciar() throws DaoException{
        try{
            Class.forName("com.mysql.jdbc.Driver");
            String url;
            String user = "root";
            String passwd = "root";
            url = "jdbc:mysql://localhost:3306/jeopardy";
            conexao = DriverManager.getConnection(url, user, passwd);
            conexao.setAutoCommit(false);
            perguntaDao = new JdbcPerguntaDao(conexao);
            
        }catch(SQLException ex){
            System.out.println(ex.getMessage());
            throw new DaoException("Ocorreu um erro ao tentar conectar com o banco de dados"+
                ex.getMessage());
            
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(JdbcDaoManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void confirmarTransacao() {
        try{
            conexao.commit();
        }catch(SQLException ex){
            throw new DaoException("Ocorreu um erro ao confirmar a transação" 
			+ ex.getMessage());
        }
    }

    @Override
    public void abortarTransacao() {
        try {
            conexao.rollback();
        } catch (SQLException ex){
            throw new DaoException("Ocorreu um erro ao abortar a transação" +
			ex.getMessage());
        }
    }

    @Override
    public PerguntaDao getPerguntaDAO() {
        return perguntaDao;
    }

     @Override
    public void encerrar() {
        try{
            if(!conexao.isClosed()){
                conexao.isClosed();
            }
        } catch (SQLException ex) {
            throw new DaoException("Ocorreu um erro ao encerrar a conexão: " 
			+ ex.getMessage());
        }
    }
    
}


