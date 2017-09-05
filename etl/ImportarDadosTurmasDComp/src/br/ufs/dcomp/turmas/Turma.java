package br.ufs.dcomp.turmas;

import java.util.List;

public final class Turma {
	
	private Integer ano;
	private Integer periodo;
	private String nomeCompleto;
	private String id;
	private List<Aluno> alunos;
	private String horario;
	private String professor;
	private String tipo;

	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public String getProfessor() {
		return professor;
	}
	public void setProfessor(String professor) {
		this.professor = professor;
	}
	public Integer getAno() {
		return ano;
	}
	public void setAno(Integer ano) {
		this.ano = ano;
	}
	public Integer getPeriodo() {
		return periodo;
	}
	public void setPeriodo(Integer periodo) {
		this.periodo = periodo;
	}
	public String getNomeCompleto() {
		return nomeCompleto;
	}
	public void setNomeCompleto(String nomeCompleto) {
		this.nomeCompleto = nomeCompleto;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public List<Aluno> getAlunos() {
		return alunos;
	}
	public void setAlunos(List<Aluno> alunos) {
		this.alunos = alunos;
	}
	
	@Override
	public boolean equals(Object obj) {
		boolean resultado = false;
		if (obj instanceof Turma) {
			Turma turmaInstance = (Turma) obj;
			resultado = turmaInstance.getId().equals(getId());
			
		}
		return resultado;
	}
	
	public String getHorario() {
		return horario;
	}
	public void setHorario(String horario) {
		this.horario = horario;
	}
	@Override
	public String toString() {
		return "Turma [ano=" + ano + ", periodo=" + periodo + ", nomeCompleto="
				+ nomeCompleto + ", id=" + id + ", horario=" + horario + ", professor=" + professor + ", tipo=" + tipo +", alunos=" + alunos
				 + "]";
	}

}