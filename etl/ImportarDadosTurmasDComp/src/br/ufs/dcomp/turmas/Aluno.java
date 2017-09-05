package br.ufs.dcomp.turmas;

public final class Aluno {
	
	private Long matricula;
	private String nome;
	private String curso;
	private String campus;
	private String tipo;
	private String turno;
	private String tipoCurso;
	private String situacao;
	private Float nota1;
	private Float nota2;
	private Float nota3;
	private Float nota4;
	private Float media;
	public Float getNota4() {
		return nota4;
	}
	public void setNota4(Float nota4) {
		this.nota4 = nota4;
	}
	private Integer nrFaltas;
	
	public Integer getNrFaltas() {
		return nrFaltas;
	}
	public void setNrFaltas(Integer nrFaltas) {
		this.nrFaltas = nrFaltas;
	}
	public Float getNota1() {
		return nota1;
	}
	public void setNota1(Float nota1) {
		this.nota1 = nota1;
	}
	public Float getNota2() {
		return nota2;
	}
	public void setNota2(Float nota2) {
		this.nota2 = nota2;
	}
	public Float getNota3() {
		return nota3;
	}
	public void setNota3(Float nota3) {
		this.nota3 = nota3;
	}
	public Long getMatricula() {
		return matricula;
	}
	public void setMatricula(Long matricula) {
		this.matricula = matricula;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getCurso() {
		return curso;
	}
	public void setCurso(String curso) {
		this.curso = curso;
	}
	public String getCampus() {
		return campus;
	}
	public void setCampus(String campus) {
		this.campus = campus;
	}
	public String getTurno() {
		return turno;
	}
	public void setTurno(String turno) {
		this.turno = turno;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public String getSituacao() {
		return situacao;
	}
	public void setSituacao(String situacao) {
		this.situacao = situacao;
	}
	public String getTipoCurso() {
		return tipoCurso;
	}
	public void setTipoCurso(String tipoCurso) {
		this.tipoCurso = tipoCurso;
	}
	@Override
	public boolean equals(Object obj) {
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Aluno other = (Aluno) obj;
		if (!matricula.equals(other.matricula))
			return false;
		return true;
	}

/*	public Float getMedia() {
		Float media = null;
		if (getNota1()!= null && getNota2()!=null) {
			if (getNota3()==null) {
				media = (getNota1() + getNota2())/2;
			} else if (getNota4()==null){
				media = (getNota1() + getNota2() + getNota3())/3;
			} else {
				media = (getNota1() + getNota2() + getNota3() + getNota4())/4;
			}
			BigDecimal teste = new BigDecimal(media);
			teste = teste.setScale(1, BigDecimal.ROUND_HALF_UP);
			media = Float.valueOf(teste.floatValue());
		}
		return media;
	}
	*/

	@Override
	public String toString() {
		return "Aluno [matricula=" + matricula + ", nome=" + nome + ", curso="
				+ curso + ", campus=" + campus + ", tipo=" + tipo + ", turno="
				+ turno + ", tipoCurso=" + tipoCurso + ", situacao=" + situacao
				+ ", nota1=" + nota1 + ", nota2=" + nota2 + ", nota3=" + nota3
				+ ", nota4=" + nota4 + ", media=" + media + ", nrFaltas=" + nrFaltas + "]";
	}
	public Float getMedia() {
		return media;
	}
	public void setMedia(Float media) {
		this.media = media;
	}

	
}