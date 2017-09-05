package br.ufs.dcomp.turmas;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;


/**
 * Classe para gerenciar uma conexão com o banco de dados.
 */
public class Conexao 
{
	
	public final static String DRV_POSTGRE   = "org.postgresql.Driver";
	public final static String URL_POSTGRE_BANCO  = "jdbc:postgresql://localhost:5432/turmas_dcomp";
	public final static String USUARIO_POSTGRE = "postgres";
	public final static String SENHA_POSTGRE   = "postgres";

    private static Conexao instancia;
    private Connection conn;
    private Statement stm;
    String driver, url, usuario, senha;

    public static Conexao getInstancia() {
        if (instancia == null) {
            instancia = new Conexao();
        }
        return instancia;
    }

    public static Conexao getInstancia(String driver, String url, String usuario, String senha) {
        if (instancia == null) {
            instancia = new Conexao(driver, url, usuario, senha);
        }
        return instancia;
    }

    private Conexao() {
        this.conn = null;
        this.stm = null;
        this.driver = DRV_POSTGRE;
        this.url = URL_POSTGRE_BANCO;
        this.usuario = USUARIO_POSTGRE;
        this.senha = SENHA_POSTGRE;
    }

    private Conexao(String driver, String url, String usuario, String senha) {
        this.conn = null;
        this.stm = null;
        this.driver = driver;
        this.url = url;
        this.usuario = usuario;
        this.senha = senha;
    }

    private void criarConnection() throws ClassNotFoundException, SQLException {
        try {
            Class.forName(this.driver);
            System.out.println("Driver JDBC carregado");
            this.conn = DriverManager.getConnection(this.url, this.usuario, this.senha);
            System.out.println("Conexão com o banco de dados estabelecida.");
        } catch (ClassNotFoundException cnfe) {
            String msg = "Driver JDBC não encontrado : " + cnfe.getMessage();
            throw new ClassNotFoundException(msg);
        } catch (SQLException sqle) {
            String msg = "Erro na conexão ao Bando de Dados : " +
                    sqle.getMessage();
            throw new SQLException(msg);
        }
    }
    
    public void setAutoCommit( boolean autoCommit ) throws SQLException 
    {
    		if (this.conn != null && !this.conn.isClosed()) 
        {
            conn.setAutoCommit( autoCommit );
        }
    }
    
    public void Commit() throws SQLException 
    {
    		if (this.conn != null && !this.conn.isClosed() && !conn.getAutoCommit() ) 
        {
            conn.commit();
        }
    }
    
    public void Rollback() throws SQLException 
    {
    		if (this.conn != null && !this.conn.isClosed() && !conn.getAutoCommit() ) 
        {
            conn.rollback();
        }
    }
    

    public Connection getConnection() throws ClassNotFoundException, SQLException {
        if (this.conn == null || this.conn.isClosed()) 
        {
            criarConnection();
        }
        return conn;
    }

    public Statement getStatement() throws ClassNotFoundException, SQLException {
        if (this.stm == null) {
            this.stm = this.getConnection().createStatement();
        }
        return this.stm;
    }

    public ResultSet executarQuery(String query) throws SQLException, ClassNotFoundException {
        return this.getStatement().executeQuery(query);
    }

    public void close() {
        try {
            conn.close();
        } catch (Exception e) {
        }
    }
}
