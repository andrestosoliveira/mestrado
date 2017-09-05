package br.ufs.dcomp.turmas;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public final class DAO 
{
	private String sqlInserirTurma = "insert into tb_tur_turma (\"tur_id\", \"tur_nome\", \"tur_ano\", \"tur_periodo\", \"tur_horario\", \"tur_professores\", \"tur_tipo\") values (?, ?, ?, ?, ?, ?, ?);";
	private String sqlInserirAluno = "insert into tb_alu_aluno (\"tur_id\", \"alu_matricula\", \"alu_nome\", \"alu_curso\", \"alu_campus\", \"alu_tipo\", \"alu_turno\", \"alu_tipo_curso\", \"alu_situacao\", \"alu_nota1\", \"alu_nota2\", \"alu_nota3\", \"alu_nota4\", \"alu_media\", \"alu_nr_faltas\") values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
	private String sqlInserirAlunoTurmaAtividade = "insert into tb_ata_aluno_turma_atividades (\"tur_id\", \"alu_matricula\", \"ala_percentual_realizado\") values (?, ?, ?);";
	
	private static DAO instancia;

	private DAO()
	{
	}
	
	public static DAO getInstancia() {
        if (instancia == null) {
            instancia = new DAO();
        }
        return instancia;
    }
	
	public void inserir(List<Turma> turmas) {
		for (Turma turma : turmas) {
			inserirTurma(turma);
		}
	}
	
	private int inserirTurma(Turma turma)
	{
		int id = 0;
		PreparedStatement pstm = null;
		Conexao conn = null;
	    try {
	        conn = Conexao.getInstancia();
	        pstm = conn.getConnection().prepareStatement(sqlInserirTurma);
	        pstm.setString(1, turma.getId());
	        pstm.setString(2, turma.getNomeCompleto());
	        pstm.setInt(3, turma.getAno());
	        pstm.setInt(4, turma.getPeriodo());
	        if (turma.getHorario()!=null) {
	        	pstm.setString(5, turma.getHorario() );
	        } else {
	        	pstm.setNull(5, java.sql.Types.VARCHAR);
			}
	        if (turma.getProfessor()!=null) {
	        	pstm.setString(6, turma.getProfessor() );
	        } else {
	        	pstm.setNull(6, java.sql.Types.VARCHAR);
			}
	        pstm.setString(7, turma.getTipo());
	        pstm.executeUpdate();
	        
	        for (Aluno aluno : turma.getAlunos()) {
	        	inserirAluno(turma.getId(), aluno, conn);
			}
	        
	        /*
	        ResultSet rs = pstm.executeQuery();
	        if(rs.next())  
	        		id = rs.getInt("idlog");
	        */ 

	    } catch (SQLException ex) {
	        ex.printStackTrace();
	    } catch (ClassNotFoundException ex) {
	    		ex.printStackTrace();
	    } finally {
	        //conn.close();
	    }	
	    return id;
	}
    
	private int inserirAluno(String idTurma, Aluno aluno, Conexao conn)
	{
		int id = 0;
		PreparedStatement pstm = null;
	    try {
	        pstm = conn.getConnection().prepareStatement(sqlInserirAluno);
	        pstm.setString(1, idTurma);
	        pstm.setLong(2, aluno.getMatricula());
	        pstm.setString(3, aluno.getNome());
	        if (aluno.getCurso() != null){
	        	pstm.setString(4, aluno.getCurso());
	        } else {
	        	pstm.setNull(4, java.sql.Types.VARCHAR);
	        }
	        if (aluno.getCampus() != null){
	        	pstm.setString(5, aluno.getCampus());
	        } else {
	        	pstm.setNull(5, java.sql.Types.VARCHAR);
	        }
	        if (aluno.getTipo() != null){
	        	pstm.setString(6, aluno.getTipo());
	        } else {
	        	pstm.setNull(6, java.sql.Types.VARCHAR);
	        }
	        if (aluno.getTurno() != null){
	        	pstm.setString(7, aluno.getTurno());
	        } else {
	        	pstm.setNull(7, java.sql.Types.VARCHAR);
	        }
	        if (aluno.getTipoCurso() != null){
	        	pstm.setString(8, aluno.getTipoCurso());
	        } else {
	        	pstm.setNull(8, java.sql.Types.VARCHAR);
	        }
	        if (aluno.getSituacao() != null){
	        	pstm.setString(9, aluno.getSituacao());
	        } else {
	        	pstm.setNull(9, java.sql.Types.VARCHAR);
	        }
	        if (aluno.getNota1() != null){
	        	pstm.setFloat(10, aluno.getNota1());
	        } else {
	        	pstm.setNull(10, java.sql.Types.REAL);
	        }
	        if (aluno.getNota2() != null){
	        	pstm.setFloat(11, aluno.getNota2());
	        } else {
	        	pstm.setNull(11, java.sql.Types.REAL);
	        }
	        if (aluno.getNota3() != null){
	        	pstm.setFloat(12, aluno.getNota3());
	        } else {
	        	pstm.setNull(12, java.sql.Types.REAL);
	        }
	        if (aluno.getNota4() != null){
	        	pstm.setFloat(13, aluno.getNota4());
	        } else {
	        	pstm.setNull(13, java.sql.Types.REAL);
	        }
	        if (aluno.getMedia() != null){
	        	pstm.setFloat(14, aluno.getMedia());
	        } else {
	        	pstm.setNull(14, java.sql.Types.REAL);
	        }	        	
	        if (aluno.getNrFaltas() != null){
	        	pstm.setInt(15, aluno.getNrFaltas());
	        } else {
	        	pstm.setNull(15, java.sql.Types.INTEGER);
	        }
	        pstm.executeUpdate();
	        
	        /*
	        ResultSet rs = pstm.executeQuery();
	        if(rs.next())  
	        		id = rs.getInt("idlog");
	        */ 

	    } catch (SQLException ex) {
	        ex.printStackTrace();
	    } catch (ClassNotFoundException ex) {
	    		ex.printStackTrace();
	    } finally {
	        //conn.close();
	    }	
	    return id;
	}
	
	public int inserirAlunoTurmaAtividades(AlunoTurmaAtividades alunoTurmaAtividades)	{
		int id = 0;
		PreparedStatement pstm = null;
		Conexao conn = null;
	    try {
	        conn = Conexao.getInstancia();
	        pstm = conn.getConnection().prepareStatement(sqlInserirAlunoTurmaAtividade);
	        pstm.setString(1, alunoTurmaAtividades.getId());
	        pstm.setLong(2, alunoTurmaAtividades.getMatricula());
	        pstm.setFloat(3, alunoTurmaAtividades.getPercentualRealizado());

	        pstm.executeUpdate();
	        
	        /*
	        ResultSet rs = pstm.executeQuery();
	        if(rs.next())  
	        		id = rs.getInt("idlog");
	        */ 

	    } catch (SQLException ex) {
	        ex.printStackTrace();
	    } catch (ClassNotFoundException ex) {
	    		ex.printStackTrace();
	    } finally {
	        //conn.close();
	    }	
	    return id;
	}
	
	/*
	public int updateNumeroLinhas( int numeroLinhas, int idarquivo )
	{
		int id = 0;
		PreparedStatement pstm = null;
		Conexao conn = null;
	    try {
	        conn = Conexao.getInstancia();
	        pstm = conn.getConnection().prepareStatement(sqlUpdateNumeroLinhas);
	        pstm.setInt(1, numeroLinhas);
	        pstm.setInt(2, idarquivo );
	        pstm.executeUpdate();
	    } catch (SQLException ex) {
	        ex.printStackTrace();
	    } catch (ClassNotFoundException ex) {
	    		ex.printStackTrace();
	    } finally {
	        conn.close();
	    }	
	    return 0;
	}
	*/
    


}
